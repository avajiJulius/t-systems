package com.logiweb.avaji.service.api.profile;


import com.logiweb.avaji.dtos.OrderDetailsDTO;
import com.logiweb.avaji.exception.ShiftValidationException;

import java.util.List;

/**
 * Order Details service manipulate with OrderDetails entity in database.
 * This service read OrderDetails by driverId, change cargo status of order which related to current OrderDetails
 * and change city by and calculate new remaining path.
 *
 */
public interface OrderDetailsService {

    /**
     * Read OrderDetailsDTO by driverId
     *
     * @param driverId
     * @return OrderDetailsDTO
     */
    OrderDetailsDTO readOrderDetails(long driverId);

    /**
     * Update Cargos by cargoIds of Order which related to current OrderDetails.
     * Before updating DriverStatus to LOAD_UNLOAD_WORK and activate shift active status if
     * it be inactive. If all Cargo status will be DELIVERED, change Order status to complete.
     *
     * @param driverId by finding OrderDetails
     * @param cargoIds which will be changed
     * @throws ShiftValidationException if DriverStatus and shift active status have conflict
     */
    void updateOrderByCargoStatus(long driverId, List<Long> cargoIds);

    /**
     * Change city to next by calculate remaining path and calculate worked hours for drivers;
     *
     * @param orderId
     * @param driverId
     *
     */
    void changeCity(long driverId, long orderId);
}
