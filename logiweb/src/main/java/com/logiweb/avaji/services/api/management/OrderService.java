package com.logiweb.avaji.services.api.management;


import com.logiweb.avaji.dtos.CreateWaypointsDTO;
import com.logiweb.avaji.dtos.DriverDTO;
import com.logiweb.avaji.dtos.OrderDTO;
import com.logiweb.avaji.dtos.TruckDTO;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.exceptions.LoadAndUnloadValidateException;

import java.util.List;

/**
 * This service response for order create, delete and read manipulations.
 *
 */

public interface OrderService {

    /**
     * The <code>readAllOrders</code> read all orders from database.
     *
     * @return list of order entities
     */
    List<OrderDTO> readAllOrders();


    /**
     * The <code>createOrderByWaypoints</code> create order entity by converting waypointsDto
     * into list of waypoint entities. Validate this waypoint by this rules: every cargo need to be
     * loaded and unloaded. After success validation save order into database.
     *
     * @param order in which waypoint list insert.
     * @param dto CreateWaypointDTO
     */
    void createOrderByWaypoints(Order order, CreateWaypointsDTO dto) throws LoadAndUnloadValidateException;

    /**
     * The <code>deleteOrder</code> delete order by id.
     *
     * @param orderId of order entity.
     */
    void deleteOrder(long orderId);

    /**
     * The <code>readTrucksForOrder</code> read waypoints for order by orderId.
     * Count <code>maxCapacity</code> value and read all free and serviceable trucks
     * with capacity greater than <code>maxCapacity</code>.
     *
     * @param orderId
     * @return all free, serviceable and suitable for capacity.
     */
    List<TruckDTO> readTrucksForOrder(long orderId);

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
     * @return
     */
    List<DriverDTO> readDriversForOrder(long orderId);


    /**
     * The <code>addDriversToOrder</code> find drivers and add them to truck of current order;
     *
     * @param driversIds
     * @param orderId
     */
    void addDriversToOrder(List<Long> driversIds, long orderId);


}
