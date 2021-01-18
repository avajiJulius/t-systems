package com.logiweb.avaji.orderdetails.service.implementetion;

import com.logiweb.avaji.crud.countrymap.dto.CityDTO;
import com.logiweb.avaji.crud.driver.dao.DriverDAO;
import com.logiweb.avaji.crud.order.dto.WaypointDTO;
import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.orderdetails.dao.OrderDetailsDAO;
import com.logiweb.avaji.entities.enums.WaypointType;

import com.logiweb.avaji.entities.models.utils.Waypoint;
import com.logiweb.avaji.orderdetails.dto.Path;
import com.logiweb.avaji.orderdetails.dto.Vertex;
import com.logiweb.avaji.orderdetails.service.api.ShiftDetailsService;
import com.logiweb.avaji.crud.countrymap.service.api.CountryMapService;
import com.logiweb.avaji.pathfinder.MapGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShiftDetailsServiceImpl implements ShiftDetailsService {

    private final OrderDetailsDAO orderDetailsDAO;
    private final CountryMapService mapService;
    private final DriverDAO driverDAO;
    private final MapGraph mapGraph;

    private List<Waypoint> waypoints;
    private List<CityDTO> path;

    @Autowired
    public ShiftDetailsServiceImpl(OrderDetailsDAO orderDetailsDAO, CountryMapService mapService,
                                   DriverDAO driverDAO, MapGraph mapGraph) {
        this.orderDetailsDAO = orderDetailsDAO;
        this.mapService = mapService;
        this.driverDAO = driverDAO;
        this.mapGraph = mapGraph;
    }

    public void init(long orderId) {
        this.waypoints = orderDetailsDAO.findWaypointsOfThisOrder(orderId);
    }

    public List<CityDTO> getPath(List<WaypointDTO> waypoints) {
        List<Path> path = new ArrayList<>();

        for(WaypointDTO waypoint: waypoints) {
            Vertex start = mapGraph.getVertex(waypoint.getLoadCityCode());
            Vertex goal = mapGraph.getVertex(waypoint.getUnloadCityCode());

            path.add(searchPath(start, goal,
                    new ArrayList<>(), new Path().createNew(start, 0)));

            mapGraph.refreshVertices();
        }

        return createShortPath(path);
    }

    private List<CityDTO> createShortPath(List<Path> route) {
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
        List<Long> codes = Arrays.stream(cityCodes).filter(c -> c != 0L).collect(Collectors.toList());
        return mapService.readCitiesByCodes(codes);
    }

    private Path searchPath(Vertex start, Vertex goal, List<Path> open, Path path) {
        mapGraph.setVisited(start);
        if(start.getCityCode() == goal.getCityCode()) {
            return path;
        }

        double minDist = Double.MAX_VALUE;
        int index = 0;
        Set<Vertex> vertices = mapGraph.findConnected(start);
        if (vertices.stream().anyMatch(v -> v.getCityCode() == goal.getCityCode()))
            return path.createNew(goal, mapService.readDistanceBetween(start.getCityCode(), goal.getCityCode()));
        if(path.isEmpty()) {
            for (Vertex vertex : vertices) {
                double dist = mapService.readDistanceBetween(start.getCityCode(), vertex.getCityCode());
                open.add(new Path(start, vertex, dist));
            }
        } else {
            for (Vertex vertex: vertices) {
                double dist = mapService.readDistanceBetween(path.getLast().getCityCode(), vertex.getCityCode());
                open.add(path.createNew(vertex, dist));
            }
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
    public Double getMaxCapacity(List<CityDTO> cities, List<Waypoint> waypoints) {
        double maxCapacity = 0;
        for (CityDTO city: cities) {
            double loadCapacity = 0.0;
            double unloadCapacity = 0.0;
            List<Waypoint> waypointList = waypoints.stream()
                    .filter(waypoint -> waypoint.getWaypointCity().getCityCode() == city.getCityCode()).collect(Collectors.toList());
            List<Waypoint> load = waypointList.stream()
                    .filter(waypoint -> waypoint.getWaypointType() == WaypointType.LOADING).collect(Collectors.toList());
            if(load.size() > 0) {
                loadCapacity = load.stream().map(waypoint -> waypoint.getWaypointCargo().getCargoWeight()).reduce((o1, o2) -> o1 + o2).get();
            }
            List<Waypoint> unload = waypointList.stream()
                    .filter(waypoint -> waypoint.getWaypointType() == WaypointType.UNLOADING).collect(Collectors.toList());
            if(unload.size() > 0) {
                    unloadCapacity = unload.stream()
                            .map(waypoint -> waypoint.getWaypointCargo().getCargoWeight()).reduce((o1, o2) -> o1 + o2).get();
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
            shiftHours += mapService.readDistanceBetween(path.get(i).getCityCode(), path.get(i+1).getCityCode());
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
        Truck truck = orderDetailsDAO.findTruckByOrderId(orderId);
        int currentSize = driverDAO.findDriversByTruckId(truck.getTruckId()).size();
        int size = truck.getShiftSize();
        return (size - currentSize);
    }

}
