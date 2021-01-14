package com.logiweb.avaji.crud.order.service.api;


import com.logiweb.avaji.crud.order.dto.CreateWaypointsDTO;
import com.logiweb.avaji.crud.order.dto.OrderDTO;
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
    List<OrderDTO> readAllOrders();


    /**
     * The <code>createOrderByWaypoints</code> create order entity by converting waypointsDto
     * into list of waypoint entities. Validate this waypoint by this rules: every cargo need to be
     * loaded and unloaded. After success validation save order into database.
     *
     * @param order in which waypoint list insert.
     * @param dto CreateWaypointDTO
     */
    void createOrderByWaypoints(Order order, CreateWaypointsDTO dto) throws CityValidateException, LoadAndUnloadValidateException;

    /**
     * The <code>deleteOrder</code> delete order by id.
     *
     * @param orderId of order entity.
     */
    void deleteOrder(long orderId);


}
