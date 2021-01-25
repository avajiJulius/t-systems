package com.logiweb.avaji.services.implementetions;

import com.logiweb.avaji.daos.TruckDAO;
import com.logiweb.avaji.dtos.*;

import com.logiweb.avaji.services.api.CountryMapService;
import com.logiweb.avaji.services.api.PathDetailsService;
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

    private final TruckDAO truckDAO;
    private final MapGraph mapGraph;

    @Autowired
    public PathDetailsServiceImpl(CountryMapService countryMapService,
                                  TruckDAO truckDAO) {
        this.mapGraph = new MapGraph(countryMapService);
        this.truckDAO = truckDAO;
    }

    @Override
    public double getMaxCapacityInTons(List<Long> citiesCodes, List<WaypointDTO> waypoints) {
        List<WaypointDTO> loadAvailable = new ArrayList<>();
        loadAvailable.addAll(waypoints);
        List<WaypointDTO> unloadAvailable = new ArrayList<>();
        double maxCapacity = 0;
        double capacity = 0;
        for (long code: citiesCodes) {
            double loadCapacity = 0.0;
            double unloadCapacity = 0.0;

            List<WaypointDTO> load = loadAvailable.stream()
                    .filter(waypoint -> waypoint.getLoadCityCode() == code).collect(Collectors.toList());
            if(load.size() > 0) {
                loadCapacity += load.stream()
                        .map(waypoint -> waypoint.getCargoWeight()).reduce((o1, o2) -> o1 + o2).get();
                loadAvailable.removeAll(load);
                unloadAvailable.addAll(load);
            }

            capacity += loadCapacity;

            if(capacity > maxCapacity) {
                maxCapacity = capacity;
            }

            List<WaypointDTO> unload = unloadAvailable.stream()
                    .filter(waypoint -> waypoint.getUnloadCityCode() == code).collect(Collectors.toList());
            if(unload.size() > 0) {
                    unloadCapacity += unload.stream()
                            .map(waypoint -> waypoint.getCargoWeight()).reduce((o1, o2) -> o1 + o2).get();
                    unloadAvailable.removeAll(unload);
            }

            capacity -= unloadCapacity;
        }

        return convertKilogramsToTons(maxCapacity);
    }


    private double convertKilogramsToTons(double maxCapacity) {
        return (maxCapacity / 1000);
    }

    @Override
    public double getShiftHours(List<Long> path) {
        double shiftHours = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            int index = i;
            shiftHours += mapGraph.getDistanceBetween(path.get(index), path.get(index+1));
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
