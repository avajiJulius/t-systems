package com.logiweb.avaji.services.api;


import com.logiweb.avaji.dtos.CityDTO;
import com.logiweb.avaji.dtos.OrderDetailsDTO;

import java.util.Deque;
import java.util.List;

public interface OrderDetailsService {
    OrderDetailsDTO readOrderDetails(long driverId);
    void updateOrderByCargoStatus(long orderId, List<Long> cargoIds);
    void changeCity(long orderId, Deque<CityDTO> remainingPath);
}
