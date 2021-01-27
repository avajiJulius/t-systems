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
     * The <code>readAllOrders</code> read all orders from database and return order dto.
     *
     * @return list of order dtos
     */
    List<OrderDTO> readAllOrders();


    /**
     * The <code>createOrderByWaypoints</code> create order entity by converting CreateWaypointsDTO
     * into list of WaypointDTO. Search path, max capacity for set order and creating OrderDetails entity.
     *
     * @param order for set path and waypoints into it.
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
     * The <code>readTrucksForOrder</code> read TruckDTO by capacity, city and serviceable status.
     *
     * @param orderId
     * @return suitable TruckDTO for order
     */
    List<TruckDTO> readTrucksForOrder(long orderId);

    /**
     * The <code>addTruckToOrder</code> add Truck of truckId to Order.
     *
     * @param truckId
     * @param orderId
     */
    void addTruckToOrder(String truckId, long orderId);

    /**
     * The <code>readDriverForOrder</code> take driver if workedHour limit
     * will not be exceeded, driver don't have uncompleted order, and the truck current city
     * is the same as driver current city.
     *
     * @param orderId
     * @return suitable DriverDTO
     */
    List<DriverDTO> readDriversForOrder(long orderId);


    /**
     * The <code>addDriversToOrder</code> add Drivers of driversIds to Order.
     *
     * @param driversIds
     * @param orderId
     */
    void addDriversToOrder(List<Long> driversIds, long orderId);


}
