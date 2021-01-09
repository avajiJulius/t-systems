package com.logiweb.avaji.crud.order.service.implementetion;

import com.logiweb.avaji.crud.driver.dao.DriverDAO;
import com.logiweb.avaji.crud.order.dao.OrderDAO;
import com.logiweb.avaji.crud.order.dto.CreateWaypointsDTO;
import com.logiweb.avaji.crud.order.dto.OrderDTO;
import com.logiweb.avaji.crud.truck.dao.TruckDAO;
import com.logiweb.avaji.crud.order.dto.WaypointDTO;
import com.logiweb.avaji.entities.enums.WaypointType;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import com.logiweb.avaji.exceptions.CityValidateException;
import com.logiweb.avaji.exceptions.LoadAndUnloadValidateException;
import com.logiweb.avaji.crud.order.service.api.OrderService;
import com.logiweb.avaji.orderdetails.service.implementetion.ShiftDetailsServiceImpl;
import com.logiweb.avaji.mapper.Mapper;
import com.logiweb.avaji.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    private final OrderDAO orderDAO;
    private final Mapper converter;
    private final Validator validator;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO,
                            Mapper converter) {
        this.orderDAO = orderDAO;
        this.converter = converter;
        this.validator = new Validator();
    }

    @Override
    @Transactional
    public List<OrderDTO> readAllOrders() {
        return orderDAO.findAllOrders();
    }


    @Override
    public void createOrderByWaypoints(Order order, CreateWaypointsDTO dto) throws LoadAndUnloadValidateException {
        List<Waypoint> waypoints = converter.dtoToWaypoints(dto, order);
        List<WaypointDTO> waypointsDto = dto.getWaypointsDto();
        order.setWaypoints(waypoints);
        if(!validator.validateOrderByWaypoints(waypointsDto)) {
            logger.error("Wrong number of load or unload points");
            throw new LoadAndUnloadValidateException("Wrong number of load or unload points");
        }
        orderDAO.saveOrder(order);
    }

    @Override
    public void deleteOrder(long orderId) {
        orderDAO.deleteOrder(orderId);
    }





}
