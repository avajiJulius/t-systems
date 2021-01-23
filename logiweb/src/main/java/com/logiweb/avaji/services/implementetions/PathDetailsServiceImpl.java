package com.logiweb.avaji.services.implementetions;

import com.logiweb.avaji.daos.TruckDAO;
import com.logiweb.avaji.dtos.*;
import com.logiweb.avaji.entities.enums.WaypointType;

import com.logiweb.avaji.services.api.PathDetailsService;
import com.logiweb.avaji.services.api.CountryMapService;
import com.logiweb.avaji.pathfinder.MapGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PathDetailsServiceImpl implements PathDetailsService {

    private final CountryMapService mapService;
    private final MapGraph mapGraph;
    private final TruckDAO truckDAO;

    @Autowired
    public PathDetailsServiceImpl(CountryMapService mapService, MapGraph mapGraph,
                                  TruckDAO truckDAO) {
        this.mapService = mapService;
        this.mapGraph = mapGraph;
        this.truckDAO = truckDAO;
    }

    @Override
    public double getMaxCapacityInTons(List<CityDTO> cities, List<WaypointDTO> waypoints) {
        double maxCapacity = 0;
        for (CityDTO city: cities) {
            double loadCapacity = 0.0;
            double unloadCapacity = 0.0;
            List<WaypointDTO> waypointList = waypoints.stream()
                    .filter(waypoint -> waypoint.getCityCode() == city.getCityCode()).collect(Collectors.toList());
            List<WaypointDTO> load = waypointList.stream()
                    .filter(waypoint -> waypoint.getType() == WaypointType.LOADING).collect(Collectors.toList());
            if(load.size() > 0) {
                loadCapacity = load.stream().map(waypoint -> waypoint.getCargoWeight()).reduce((o1, o2) -> o1 + o2).get();
            }
            List<WaypointDTO> unload = waypointList.stream()
                    .filter(waypoint -> waypoint.getType() == WaypointType.UNLOADING).collect(Collectors.toList());
            if(unload.size() > 0) {
                    unloadCapacity = unload.stream()
                            .map(waypoint -> waypoint.getCargoWeight()).reduce((o1, o2) -> o1 + o2).get();
            }
            double capacity = (loadCapacity - unloadCapacity);
            if(capacity > maxCapacity) {
                maxCapacity = capacity;
            }
        }

        return convertKilogramsToTons(maxCapacity);
    }

    private double convertKilogramsToTons(double maxCapacity) {
        return (maxCapacity / 1000);
    }

    @Override
    public double getShiftHours(List<CityDTO> path) {
        double shiftHours = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            int index = i;
            shiftHours += mapService.readDistanceBetween(path.get(index).getCityCode(), path.get(index+1).getCityCode());
        }
        return shiftHours;
    }

    @Override
    public long calculateTimeUntilEndOfMonth() {
        LocalDateTime to = LocalDateTime.now().withHour(0).with(TemporalAdjusters.firstDayOfNextMonth());
        LocalDateTime from = LocalDateTime.now();
        return from.until(to, ChronoUnit.HOURS);
    }

    @Override
    public int calculateFreeSpaceInShift(long orderId) {
        TruckDTO truck = truckDAO.findTruckByOrderId(orderId);
        int currentSize = (int) truckDAO.countDriversOfTruck(truck.getTruckId());
        int size = truck.getShiftSize();
        return (size - currentSize);
    }


    @Override
    public Path getPath(List<WaypointDTO> waypoints) {
        List<Path> available = new ArrayList<>();
        for(WaypointDTO waypoint : waypoints) {
            if (waypoint.getLoadCityCode() == waypoint.getUnloadCityCode()) {
                available.add(new Path(new ArrayList<>(),
                        waypoint.getLoadCityCode(), Double.MAX_VALUE));
            } else {
                available.add(new Path(new ArrayList<>(),
                        waypoint.getLoadCityCode(), waypoint.getUnloadCityCode(), Double.MAX_VALUE));
            }
        }
        long maxCounter = 0L;
        int index = 0;

        for (int i = 0; i < available.size(); i++) {
            long loadCode = available.get(i).getFirst();
            long count = available.stream().filter(p -> p.getFirst() == loadCode).count();
            if(count > maxCounter) {
                maxCounter = count;
                index = i;
            }
        }


        long newStart = available.get(index).getFirst();
        available.stream().filter(a -> a.getFirst() == newStart).forEach(Path::removeFirst);

        return getNewPath(new Path(new ArrayList<>(), newStart, 0), available, new ArrayList<>());

    }

    private Path getNewPath(Path path, List<Path> available, List<Path> open) {
        if (isEmpty(available)) {
            return path;
        }

        long maxCounter = 0L;
        int index = 0;

        List<Long> connected = mapGraph.findConnected(path.getLast());
        for (int i = 0; i < connected.size(); i++) {
            long loadCode = connected.get(i);
            long count = available.stream().filter(a -> a.getFirst() == loadCode).count();
            if(count > maxCounter) {
                maxCounter = count;
                index = i;
            }
        }

        if(maxCounter == 0L) {
            double minDist = Double.MAX_VALUE;
            index = 0;

            for (long codes: connected) {
                open.add(createNewPath(path, path.getLast(), codes));
            }
            for (int i = 0; i < open.size(); i++) {
                double dist = open.get(i).getDistance();
                if(minDist > dist) {
                    minDist = dist;
                    index = i;
                }
            }

            Path newPath = open.remove(index);

            return getNewPath(newPath, available, open);
        }

        long newStart = connected.get(index);
        available.stream().filter(a -> a.getFirst() == newStart).forEach(Path::removeFirst);
        double distance = mapGraph.getDistanceBetween(path.getLast(), newStart);
        path.add(newStart, distance);

        return getNewPath(path, available, new ArrayList<>());

    }

    private boolean isEmpty(List<Path> available) {
        for(Path path : available) {
            if(!path.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private Path createNewPath(Path path, long start, long goal) {
        List<Long> newPath = new ArrayList<>();
        newPath.addAll(path.getPath());
        double distance = path.getDistance() +
                mapGraph.getDistanceBetween(start, goal);
        return new Path.Builder()
                .withPath(newPath)
                .add(goal)
                .withDistance(distance)
                .build();
    }
}
