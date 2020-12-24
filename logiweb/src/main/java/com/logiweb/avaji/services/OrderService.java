package com.logiweb.avaji.services;


import com.logiweb.avaji.entities.dto.WaypointDto;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.utils.Waypoint;

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
    List<Order> readAllOrders();


    /**
     * The <code>createOrder</code> create order entity whit valid cargo.
     *
     * @param order
     */
    void createOrder(Order order);


    /**
     * The <code>createOrderByWaypoints</code> create order entity by converting waypointsDto
     * into list of waypoint entities. Validate this waypoint by this rules: every cargo need to be
     * loaded and unloaded. After success validation save order into database.
     *
     * @param order in which waypoint list insert.
     * @param waypointsDto convert into waypoint list and go across validate.
     */
    void createOrderByWaypoints(Order order,List<WaypointDto> waypointsDto);

    /**
     * The <code>deleteOrder</code> delete order by id.
     *
     * @param orderId of order entity.
     */
    void deleteOrder(Integer orderId);
}
