package com.logiweb.avaji.services.implementetions;

import com.logiweb.avaji.entities.enums.CargoStatus;
import com.logiweb.avaji.entities.enums.WaypointType;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class WaypointServiceTest {

    private List<Waypoint> waypoints;
    private List<City> cities;
    private WaypointServiceImpl waypointService;

    @Test
    public void shouldReturnMaxCapacityThatEquals130() {
        this.waypointService = new WaypointServiceImpl();
        this.cities = new ArrayList<>();
        List<Cargo> cargo = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            cargo.add(new Cargo(i, "Cargo" + i, i * 10, CargoStatus.PREPARED));
        }
        for (int i = 1; i < 7; i++) {
            cities.add(new City(i, "City"+ i));
        }
        this.waypoints = new ArrayList<>();
        waypoints.add(new Waypoint(1, cities.get(0), WaypointType.LOADING, null, cargo.get(0)));
        waypoints.add(new Waypoint(2, cities.get(0), WaypointType.LOADING, null, cargo.get(1)));
        waypoints.add(new Waypoint(3, cities.get(0), WaypointType.UNLOADING, null, cargo.get(5)));
        waypoints.add(new Waypoint(4, cities.get(1), WaypointType.LOADING, null, cargo.get(2)));
        waypoints.add(new Waypoint(5, cities.get(1), WaypointType.LOADING, null, cargo.get(3)));
        waypoints.add(new Waypoint(6, cities.get(2), WaypointType.UNLOADING, null, cargo.get(1)));
        waypoints.add(new Waypoint(7, cities.get(2), WaypointType.UNLOADING, null, cargo.get(0)));
        waypoints.add(new Waypoint(8, cities.get(2), WaypointType.LOADING, null, cargo.get(5)));
        waypoints.add(new Waypoint(10, cities.get(4), WaypointType.UNLOADING, null, cargo.get(3)));
        waypoints.add(new Waypoint(11, cities.get(4), WaypointType.UNLOADING, null, cargo.get(2)));

        Double maxCapacity = waypointService.getMaxCapacity(waypointService.getDumpPath(waypoints), waypoints);
        assertEquals(130, maxCapacity);
    }
}