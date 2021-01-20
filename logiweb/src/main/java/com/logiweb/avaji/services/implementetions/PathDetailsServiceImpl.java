package com.logiweb.avaji.services.implementetions;

import com.logiweb.avaji.daos.OrderDAO;
import com.logiweb.avaji.daos.OrderDetailsDAO;
import com.logiweb.avaji.daos.TruckDAO;
import com.logiweb.avaji.dtos.*;
import com.logiweb.avaji.daos.DriverDAO;
import com.logiweb.avaji.entities.enums.WaypointType;

import com.logiweb.avaji.entities.models.Waypoint;
import com.logiweb.avaji.services.api.OrderDetailsService;
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

    public List<Long> getPath(List<WaypointDTO> waypoints) {
        List<Path> path = new ArrayList<>();

        for(WaypointDTO waypoint: waypoints) {
            Vertex start = mapGraph.getVertex(waypoint.getLoadCityCode());
            Vertex goal = mapGraph.getVertex(waypoint.getUnloadCityCode());

            path.add(searchPath(start, goal,
                    new ArrayList<>(), new Path(new ArrayList<>(), 0)));

            mapGraph.refreshVertices();
        }

        return createShortPath(path);
    }

    private List<Long> createShortPath(List<Path> route) {
        List<Vertex> vertices = new ArrayList<>();
        for(Path path : route) {
            vertices.addAll(path.getPath());
        }

        Long[] cityCodes = new Long[vertices.size()];
        Arrays.fill(cityCodes, 0L);

        for (int i = 0; i < route.size(); i++) {
            Path path = route.get(i);
            for(Vertex vertex: path.getPath()) {
                int index = vertices.indexOf(vertex);
                cityCodes[index] = vertex.getCityCode();
            }
        }
        return Arrays.stream(cityCodes).filter(c -> c != 0L).collect(Collectors.toList());
    }

    private Path searchPath(Vertex start, Vertex goal, List<Path> open, Path path) {
        mapGraph.setVisited(start);
        path.addVertex(start);

        if(start.getCityCode() == goal.getCityCode()) {
            return path;
        }

        double minDist = Double.MAX_VALUE;
        int index = 0;
        Set<Vertex> vertices = mapGraph.findConnected(start);
        if (vertices.stream().anyMatch(v -> v.getCityCode() == goal.getCityCode())) {
            return createNewPath(path ,start, goal);
        }

        for (Vertex vertex: vertices) {
            open.add(createNewPath(path, path.getLast(), vertex));
        }
        for (int i = 0; i < open.size(); i++) {
            double dist = open.get(i).getDistance();
            if(minDist > dist) {
                minDist = dist;
                index = i;
            }
        }

        Vertex newStart = open.get(index).getLast();
        mapGraph.setVisited(newStart);
        Path newPath = open.remove(index);

        return searchPath(newStart, goal, open, newPath);
    }

    private Path createNewPath(Path path, Vertex start, Vertex goal) {
        List<Vertex> vertices = new ArrayList<>();
        vertices.addAll(path.getPath());
        vertices.add(goal);
        double distance = path.getDistance() +
                mapService.readDistanceBetween(start.getCityCode(), goal.getCityCode());
        return new Path(vertices, distance);
    }


    public CityDTO getNextCity(List<CityDTO> path, long cityCode) {
        Iterator<CityDTO> iterator = path.iterator();
        while (iterator.hasNext()) {
            if(iterator.next().getCityCode() == cityCode) {
                return iterator.next();
            }
        }
        throw new UnsupportedOperationException();
    }


    @Override
    public Double getMaxCapacity(List<CityDTO> cities, List<WaypointDTO> waypoints) {
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

        return maxCapacity;
    }

    @Override
    public Double getShiftHours(List<CityDTO> path) {
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

}
