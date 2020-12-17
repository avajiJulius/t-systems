package com.logiweb.avaji.services;


import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.Order;

import java.util.List;

public interface OrderService {

    /**
     * The <code>readAllOrders</code> read all orders from database.
     *
     * @return list of order entities
     */
    List<Order> readAllOrders();

    List<Order> readOrdersByWaypointId(Long waypointId);
    public List<Cargo> readCargoByOrderId(Long orderId);

    /**
     * The <code>createOrder</code> create order entity whit valid cargo.
     *
     * @param order
     */
    void createOrder(Order order);

    /**
     * The <code>deleteOrder</code> delete order by id.
     *
     * @param orderId of order entity.
     */
    void deleteOrder(Long orderId);
}
