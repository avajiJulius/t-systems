package com.logiweb.avaji.orderdetails.service.api;

import com.logiweb.avaji.crud.driver.dto.DriverPublicResponseDto;
import com.logiweb.avaji.entities.models.Truck;

import java.util.List;

public interface OrderDetailsService {

    /**
     * The <code>readTrucksForOrder</code> read waypoints for order by orderId.
     * Count <code>maxCapacity</code> value and read all free and serviceable trucks
     * with capacity greater than <code>maxCapacity</code>.
     *
     * @param orderId
     * @return all free, serviceable and suitable for capacity.
     */
    List<Truck> readTrucksForOrder(long orderId);

    /**
     * The <code>addTruckToOrder</code> find truck by truckId and add it to order with orderId.
     *
     * @param truckId
     * @param orderId
     */
    void addTruckToOrder(String truckId, long orderId);

    /**
     * The <code>readDriverForOrder</code> take driver if workedHour limit
     * will not be exceeded, driver status is <code>REST</code>, and the truck current city
     * is the same as driver current city.
     *
     * @param orderId
     */
    List<DriverPublicResponseDto> readDriverForOrder(long orderId);


    /**
     * The <code>addDriversToOrder</code> find drivers and add them to truck of current order;
     *
     * @param driversIds
     * @param orderId
     */
    void addDriversToOrder(List<Long> driversIds, long orderId);
}
