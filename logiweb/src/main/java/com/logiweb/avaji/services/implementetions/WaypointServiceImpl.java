package com.logiweb.avaji.services.implementetions;

import com.logiweb.avaji.entities.enums.WaypointType;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.entities.models.utils.Road;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import com.logiweb.avaji.services.WaypointService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WaypointServiceImpl implements WaypointService {


    public List<City> getDumpPath(List<Waypoint> waypoints) {
        List<City> cities = waypoints.stream().map(Waypoint::getWaypointCity).distinct().collect(Collectors.toList());
        cities.add(cities.get(0));
        return cities;
    }

    public Double getMaxCapacity(List<City> path, List<Waypoint> waypoints) {
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
            if(capacity > maxCapacity) {
                maxCapacity = capacity;
            }
            index++;
        }

        return maxCapacity;
    }


    public void getPath(List<Waypoint> waypoints) {
        getCityPath(waypoints);
    }

    public Set<City> getCityPath(List<Waypoint> waypoints) {
        Deque<City> cityPath = new ArrayDeque<>();
        List<City> cities = waypoints.stream().map(Waypoint::getWaypointCity).distinct().collect(Collectors.toList());
        for (City city: cities) {
            Stream<Waypoint> stream = waypoints.stream().filter(w -> w.getWaypointCity().getCityCode() == city.getCityCode());
            long waypointsCounterInCity = stream.count();
            long unloadingCounterInCity = stream.filter(w -> w.getWaypointType() == WaypointType.UNLOADING).count();
            if(waypointsCounterInCity == unloadingCounterInCity) {
                cityPath.addLast(city);
                break;
            }
        }
        if (cityPath.isEmpty()) {
        }
        throw new UnsupportedOperationException();
    }

}
