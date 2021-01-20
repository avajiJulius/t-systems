package com.logiweb.avaji.services.implementetions;

import com.logiweb.avaji.daos.DriverDAO;
import com.logiweb.avaji.dtos.*;
import com.logiweb.avaji.daos.OrderDAO;
import com.logiweb.avaji.entities.models.*;
import com.logiweb.avaji.parser.PathStringParser;
import com.logiweb.avaji.services.api.OrderService;
import com.logiweb.avaji.mapper.Mapper;
import com.logiweb.avaji.services.api.PathDetailsService;
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
    private final PathDetailsService pathDetailsService;
    private final PathStringParser parser;
    private final DriverDAO driverDAO;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, Mapper converter, PathDetailsService pathDetailsService,
                            PathStringParser parser, DriverDAO driverDAO) {
        this.orderDAO = orderDAO;
        this.converter = converter;
        this.pathDetailsService = pathDetailsService;
        this.parser = parser;
        this.driverDAO = driverDAO;
    }

    @Override
    @Transactional
    public List<OrderDTO> readAllOrders() {
        return orderDAO.findAllOrders();
    }


    @Override
    public void createOrderByWaypoints(Order order, CreateWaypointsDTO dto) {
        List<Waypoint> waypoints = converter.dtoToWaypoints(dto, order);
        orderDAO.saveWaypoints(waypoints);

        List<Long> path = pathDetailsService.getPath(dto.getWaypointsDto());
        String stringPath = parser.parsePathListToString(path);
        order.setPath(stringPath);

        long id = orderDAO.saveOrder(order);
        //TODO Listner
        createOrderDetails(id);
    }

    private void createOrderDetails(long id) {
        orderDAO.saveOrderDetails(id);
    }

    @Override
    public void deleteOrder(long orderId) {
        orderDAO.deleteOrder(orderId);
    }


    @Override
    public List<TruckDTO> readTrucksForOrder(long orderId) {
        Order order = orderDAO.findOrderById(orderId);


        Double maxCapacity = pathDetailsService.getMaxCapacity(
                parser.pathStringToCityDTOList(order.getPath()),
                orderDAO.findWaypointsOfThisOrder(orderId));

        return orderDAO.findTrucksForOrder(maxCapacity);
    }

    @Override
    public void addTruckToOrder(String truckId, long orderId) {
        Truck truck = orderDAO.findTruckEntityById(truckId);
        Order order = orderDAO.findOrderById(orderId);
        order.setDesignatedTruck(truck);
        orderDAO.updateOrder(order);
    }

    @Override
    public List<DriverDTO> readDriversForOrder(long orderId) {
        long cityCode = orderDAO.findTruckByOrderId(orderId).getCurrentCity().getCityCode();
        String path = orderDAO.findOrderById(orderId).getPath();
        Double shiftHours = pathDetailsService.getShiftHours(parser.pathStringToCityDTOList(path));

        long untilEndOfMonth = pathDetailsService.calculateTimeUntilEndOfMonth();
        if(shiftHours > untilEndOfMonth) {
            shiftHours = (double) untilEndOfMonth;
        }

        return orderDAO.findDriverForOrder(shiftHours, cityCode);
    }

    @Override
    @Transactional
    public void addDriversToOrder(List<Long> driversIds, long orderId) {
        String truckId = orderDAO.findOrderById(orderId).getDesignatedTruck().getTruckId();
        OrderDetails orderDetails = orderDAO.findOrderDetails(orderId);
        Truck truck = orderDAO.findTruckEntityById(truckId);
        List<Driver> drivers = orderDAO.findDriversByIds(driversIds);
        for(Driver driver: drivers) {
            driver.setCurrentTruck(truck);
            driver.setOrderDetails(orderDetails);
        }
        driverDAO.updateDrivers(drivers);
    }
}
