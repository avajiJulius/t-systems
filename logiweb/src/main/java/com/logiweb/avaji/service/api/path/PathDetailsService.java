package com.logiweb.avaji.service.api.path;

import com.logiweb.avaji.entity.model.Path;
import com.logiweb.avaji.dtos.WaypointDTO;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;

/**
 * This service calculate the shortest path for orders waypoints by modified A* algorithm,
 * calculate max capacity that will be reached along the way, calculate time until end of month
 * and approximate shift hours by remaining path.
 *
 */

@Service
public interface PathDetailsService {

    /**
     * Calculate max capacity in tons by path and waypoints dto
     *
     * @param citiesCodes path
     * @param waypoints of order
     * @return max capacity that will be reached along the way.
     */
    double getMaxCapacityInTons(List<Long> citiesCodes, List<WaypointDTO> waypoints);

    /**
     *
     * Calculate approximate shift hours by path
     *
     * @param path
     * @return approximate shift hours
     */
    double getShiftHours(List<Long> path);

    /**
     * Calculate shortest path by modified A* algorithm
     *
     *
     * @param waypoints
     * @return shortest Path
     */
    Path getPath(List<WaypointDTO> waypoints);
}
