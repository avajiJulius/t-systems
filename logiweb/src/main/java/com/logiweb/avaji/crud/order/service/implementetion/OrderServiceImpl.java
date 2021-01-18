package com.logiweb.avaji.crud.order.service.implementetion;

import com.logiweb.avaji.crud.countrymap.dto.CityDTO;
import com.logiweb.avaji.crud.countrymap.service.api.CountryMapService;
import com.logiweb.avaji.crud.order.dao.OrderDAO;
import com.logiweb.avaji.crud.order.dto.CreateWaypointsDTO;
import com.logiweb.avaji.crud.order.dto.OrderDTO;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import com.logiweb.avaji.exceptions.LoadAndUnloadValidateException;
import com.logiweb.avaji.crud.order.service.api.OrderService;
import com.logiweb.avaji.mapper.Mapper;
import com.logiweb.avaji.orderdetails.service.api.ShiftDetailsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    private final OrderDAO orderDAO;
    private final Mapper converter;
    private final ShiftDetailsService shiftDetailsService;


    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, Mapper converter, ShiftDetailsService shiftDetailsService) {
        this.orderDAO = orderDAO;
        this.converter = converter;
        this.shiftDetailsService = shiftDetailsService;
    }

    @Override
    @Transactional
    public List<OrderDTO> readAllOrders() {
        return orderDAO.findAllOrders();
    }


    @Override
    public void createOrderByWaypoints(Order order, CreateWaypointsDTO dto) {
        List<Waypoint> waypoints = converter.dtoToWaypoints(dto, order);
        order.setWaypoints(waypoints);
        List<CityDTO> path = shiftDetailsService.getPath(dto.getWaypointsDto());
        StringBuffer sb = new StringBuffer();
        for (CityDTO city: path) {
            sb.append(city.getCityCode());
            sb.append("-");
        }
        String stringPath = sb.toString();
        order.setPath(stringPath);
        orderDAO.saveOrder(order);
    }

    @Override
    public void deleteOrder(long orderId) {
        orderDAO.deleteOrder(orderId);
    }





}
