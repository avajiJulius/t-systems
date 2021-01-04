package com.logiweb.avaji.orderdetails.service.implementetion;

import com.logiweb.avaji.crud.driver.dao.DriverDAO;
import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.orderdetails.dao.OrderDetailsDAO;
import com.logiweb.avaji.entities.enums.WaypointType;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.entities.models.utils.Road;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import com.logiweb.avaji.orderdetails.service.api.ShiftDetailsService;
import com.logiweb.avaji.crud.countrymap.service.api.CountryMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ShiftDetailsServiceImpl implements ShiftDetailsService {

    private final OrderDetailsDAO orderDetailsDAO;
    private final CountryMapService mapService;
    private final DriverDAO driverDAO;

    private List<Waypoint> waypoints;
    private List<City> path;

    @Autowired
    public ShiftDetailsServiceImpl(OrderDetailsDAO orderDetailsDAO, CountryMapService mapService,
                                   DriverDAO driverDAO) {
        this.orderDetailsDAO = orderDetailsDAO;
        this.mapService = mapService;
        this.driverDAO = driverDAO;
    }

    public void init(long orderId) {
        this.waypoints = orderDetailsDAO.findWaypointsOfThisOrder(orderId);
        this.path = getDumpPath();
    }

    private List<City> getDumpPath() {
            List<City> cities = waypoints.stream().map(Waypoint::getWaypointCity)
                    .distinct().collect(Collectors.toList());
            cities.add(cities.get(0));
            return cities;
    }

    @Override
    public Double getMaxCapacity() {
        int index = 0;
        Double capacity = 0.0;

        int finalIndex = index;
        List<Waypoint> cityWaypoint = waypoints.stream()
                .filter(w -> w.getWaypointCity().getCityCode() == path.get(finalIndex).getCityCode())
                .collect(Collectors.toList());
        capacity = cityWaypoint.stream().filter(waypoint -> waypoint.getWaypointType() == WaypointType.LOADING)
                .map(Waypoint::getWaypointCargo)
                .map(Cargo::getCargoWeight).reduce(capacity, (a, b) -> a + b);

        Double maxCapacity = capacity;
        index++;
        while (index < path.size() - 2) {
            int finalIndex1 = index;
            cityWaypoint = waypoints.stream()
                    .filter(w -> w.getWaypointCity().getCityCode() == path.get(finalIndex1).getCityCode())
                    .collect(Collectors.toList());
            capacity = cityWaypoint.stream().filter(waypoint -> waypoint.getWaypointType() == WaypointType.UNLOADING)
                    .map(Waypoint::getWaypointCargo)
                    .map(Cargo::getCargoWeight).reduce(capacity, (a, b) -> a - b);

            capacity = cityWaypoint.stream().filter(waypoint -> waypoint.getWaypointType() == WaypointType.LOADING)
                    .map(Waypoint::getWaypointCargo)
                    .map(Cargo::getCargoWeight).reduce(capacity, (a, b) -> a + b);
            if (capacity > maxCapacity) {
                maxCapacity = capacity;
            }
            index++;
        }

        return maxCapacity;
    }

    @Override
    public Double getShiftHours() {
        List<Road> roads = mapService.readPathRoads(path);
        Double shiftHours = 0.0;
        for (Road road : roads) {
            shiftHours += road.getDistanceInHours();
        }

        return shiftHours;
    }


    public void getPath(List<Waypoint> waypoints) {
        getCityPath(waypoints);
    }


    //TODO: smart path created
    public Set<City> getCityPath(List<Waypoint> waypoints) {
        Deque<City> cityPath = new ArrayDeque<>();
        List<City> cities = waypoints.stream().map(Waypoint::getWaypointCity).distinct().collect(Collectors.toList());
        for (City city : cities) {
            Stream<Waypoint> stream = waypoints.stream().filter(w -> w.getWaypointCity().getCityCode() == city.getCityCode());
            long waypointsCounterInCity = stream.count();
            long unloadingCounterInCity = stream.filter(w -> w.getWaypointType() == WaypointType.UNLOADING).count();
            if (waypointsCounterInCity == unloadingCounterInCity) {
                cityPath.addLast(city);
                break;
            }
        }
        if (cityPath.isEmpty()) {
        }
        throw new UnsupportedOperationException();
    }


    @Override
    public long calculateTimeUntilEndOfMonth() {
        LocalDateTime to = LocalDateTime.now().withHour(0).with(TemporalAdjusters.firstDayOfNextMonth());
        LocalDateTime from = LocalDateTime.now();
        long until = from.until(to, ChronoUnit.HOURS);
        return until;
    }

    @Override
    public int calculateFreeSpaceInShift(long orderId) {
        Truck truck = orderDetailsDAO.findTruckByOrderId(orderId);
        int currentSize = driverDAO.findDriversByTruckId(truck.getTruckId()).size();
        int size = truck.getShiftSize();
        return (size - currentSize);
    }

}
