package com.logiweb.avaji.services;


import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.utils.Waypoint;

import java.util.List;

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
     * The <code>createOrderByWaypoints</code> create order entity with list of waypoints.
     *
     * @param waypoints
     */
    void createOrderByWaypoints(List<Waypoint> waypoints);

    /**
     * The <code>deleteOrder</code> delete order by id.
     *
     * @param orderId of order entity.
     */
    void deleteOrder(Integer orderId);
}
