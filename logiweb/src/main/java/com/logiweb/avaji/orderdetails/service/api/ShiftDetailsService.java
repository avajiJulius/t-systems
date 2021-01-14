package com.logiweb.avaji.orderdetails.service.api;


import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShiftDetailsService {
    void init(long orderId);
    Double getMaxCapacity();
    Double getShiftHours();
    long calculateTimeUntilEndOfMonth();
    int calculateFreeSpaceInShift(long orderId);
    City getNextCity(List<Waypoint> cities, long cityCode);
}
