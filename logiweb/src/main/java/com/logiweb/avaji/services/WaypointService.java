package com.logiweb.avaji.services;


import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.entities.models.utils.Waypoint;

import java.util.List;

/**
 * This service calculate and compute need utils values and path.
 *
 */
public interface WaypointService {

    /**
     * The <code>getDumpPath</code> create simple path from waypoints.
     *
     * @param waypoints
     * @return simple path
     */
    List<City> getDumpPath(List<Waypoint> waypoints);

    /**
     *The <code>getMaxCapacity</code> calculate maxCapacity value for path
     * of certain list of waypoints.
     *
     * @param path
     * @param waypoints
     * @return the maxCapacity value that can be obtained.
     */
    Double getMaxCapacity(List<City> path, List<Waypoint> waypoints);
}
