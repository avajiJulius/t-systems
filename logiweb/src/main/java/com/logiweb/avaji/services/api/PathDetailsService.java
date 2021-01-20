package com.logiweb.avaji.services.api;


import com.logiweb.avaji.dtos.CityDTO;
import com.logiweb.avaji.dtos.WaypointDTO;
import com.logiweb.avaji.entities.models.Waypoint;
import org.springframework.stereotype.Service;

import java.util.Deque;
import java.util.List;

@Service
public interface PathDetailsService {
    Double getMaxCapacity(List<CityDTO> cities, List<WaypointDTO> waypoints);
    Double getShiftHours(List<CityDTO> path);
    long calculateTimeUntilEndOfMonth();
    int calculateFreeSpaceInShift(long orderId);
    CityDTO getNextCity(List<CityDTO> path, long cityCode);
    List<Long> getPath(List<WaypointDTO> waypoints);
}
