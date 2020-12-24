package com.logiweb.avaji.services;


import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.entities.models.utils.Waypoint;

import java.util.List;

public interface WaypointService {
    List<City> getDumpPath(List<Waypoint> waypoints);
    Double getMaxCapacity(List<City> path, List<Waypoint> waypoints);
}
