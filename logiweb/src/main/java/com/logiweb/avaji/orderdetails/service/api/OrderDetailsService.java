package com.logiweb.avaji.orderdetails.service.api;


import org.springframework.stereotype.Service;

@Service
public interface OrderDetailsService {
    void init(long orderId);
    Double getMaxCapacity();
    Double getShiftHours();
    long calculateTimeUntilEndOfMonth();
    int calculateFreeSpaceInShift(long orderId);

}
