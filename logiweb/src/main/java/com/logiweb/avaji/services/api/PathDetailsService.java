package com.logiweb.avaji.services.api;


import com.logiweb.avaji.dtos.CityDTO;
import com.logiweb.avaji.dtos.Path;
import com.logiweb.avaji.dtos.WaypointDTO;
import com.logiweb.avaji.entities.models.Waypoint;
import org.springframework.stereotype.Service;

import java.util.Deque;
import java.util.List;

@Service
public interface PathDetailsService {
    double getMaxCapacityInTons(List<Long> citiesCodes, List<WaypointDTO> waypoints);
    double getShiftHours(List<Long> path);
    long calculateTimeUntilEndOfMonth();
    int calculateFreeSpaceInShift(long orderId);
    Path getPath(List<WaypointDTO> waypoints);
}
