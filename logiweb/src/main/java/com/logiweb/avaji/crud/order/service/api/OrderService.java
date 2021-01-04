package com.logiweb.avaji.crud.order.service.api;


import com.logiweb.avaji.crud.order.dto.OrderDto;
import com.logiweb.avaji.crud.order.dto.WaypointDto;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.exceptions.CityValidateException;
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
    List<OrderDto> readAllOrders();


    /**
     * The <code>createOrderByWaypoints</code> create order entity by converting waypointsDto
     * into list of waypoint entities. Validate this waypoint by this rules: every cargo need to be
     * loaded and unloaded. After success validation save order into database.
     *
     * @param order in which waypoint list insert.
     * @param waypointsDto convert into waypoint list and go across validate.
     */
    void createOrderByWaypoints(Order order,List<WaypointDto> waypointsDto) throws CityValidateException, LoadAndUnloadValidateException;

    /**
     * The <code>deleteOrder</code> delete order by id.
     *
     * @param orderId of order entity.
     */
    void deleteOrder(long orderId);


}
