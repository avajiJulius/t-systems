package com.logiweb.avaji.orderdetails.service.api;


import com.logiweb.avaji.crud.countrymap.dto.CityDTO;
import com.logiweb.avaji.crud.order.dto.WaypointDTO;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShiftDetailsService {
    void init(long orderId);
    Double getMaxCapacity(List<CityDTO> cities, List<Waypoint> waypoints);
    Double getShiftHours(List<CityDTO> path);
    long calculateTimeUntilEndOfMonth();
    int calculateFreeSpaceInShift(long orderId);
    CityDTO getNextCity(List<CityDTO> path, long cityCode);
    List<CityDTO> getPath(List<WaypointDTO> waypoints);
}
