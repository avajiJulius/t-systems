package com.logiweb.avaji.orderdetails.service.api;


import org.springframework.stereotype.Service;

@Service
public interface OrderDetailsService {
    void init(Integer orderId);
    Double getMaxCapacity();
    Double getShiftHours();
    long calculateTimeUntilEndOfMonth();

}
