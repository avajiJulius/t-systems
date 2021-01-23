package com.logiweb.avaji.services.api;


import com.logiweb.avaji.dtos.OrderDetailsDTO;
import com.logiweb.avaji.exceptions.ShiftValidationException;

import java.util.List;

public interface OrderDetailsService {
    OrderDetailsDTO readOrderDetails(long driverId);
    void updateOrderByCargoStatus(long driverId, List<Long> cargoIds) throws ShiftValidationException;
    void changeCity(long orderId, long driverId);
}
