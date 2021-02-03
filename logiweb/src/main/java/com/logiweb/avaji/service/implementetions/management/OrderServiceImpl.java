package com.logiweb.avaji.service.implementetions.management;

import com.logiweb.avaji.dao.CargoDAO;
import com.logiweb.avaji.dtos.*;
import com.logiweb.avaji.dao.OrderDAO;
import com.logiweb.avaji.entity.model.*;
import com.logiweb.avaji.service.api.management.DriverService;
import com.logiweb.avaji.service.implementetions.sender.JmsSender;
import com.logiweb.avaji.service.implementetions.utils.PathParser;
import com.logiweb.avaji.service.api.management.OrderService;
import com.logiweb.avaji.service.implementetions.utils.Mapper;
import com.logiweb.avaji.service.api.path.PathDetailsService;
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
    private final PathParser parser;
    private final DriverService driverService;
    private final CargoDAO cargoDAO;
    private final JmsSender jmsSender;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, Mapper converter, PathDetailsService pathDetailsService,
                            PathParser parser, DriverService driverService, CargoDAO cargoDAO, JmsSender jmsSender) {
        this.orderDAO = orderDAO;
        this.converter = converter;
        this.pathDetailsService = pathDetailsService;
        this.parser = parser;
        this.driverService = driverService;
        this.cargoDAO = cargoDAO;
        this.jmsSender = jmsSender;
    }

    @Override
    @Transactional
    public List<OrderDTO> readOrdersPage(int pageNumber, int pageSize) {
        int indexFrom = 0;
        if(pageNumber != 1) {
            indexFrom = (pageNumber - 1) * pageSize;
        }
        List<OrderDTO> orderPage = orderDAO.findOrdersPage(indexFrom, pageSize);
        for (OrderDTO orderDTO : orderPage) {
            orderDTO.setDrivers(orderDAO.findDriversByOrderId(orderDTO.getOrderId()));

            if(orderDTO.getTruckId() != null) {
                orderDTO.setShiftSize(
                        orderDAO.findTruckByOrderId(orderDTO.getOrderId()).getShiftSize());
            }

            orderDTO.setPrettyPath(parser.toPrettyPath(orderDTO.getPrettyPath()));
        }
        return orderPage;
    }

    @Override
    public void createOrderByWaypoints(Order order, CreateWaypointsDTO dto) {
        List<Waypoint> waypoints = converter.dtoToWaypoints(dto, order);
        orderDAO.saveWaypoints(waypoints);

        List<WaypointDTO> waypointsDTO = dto.getWaypointsDto();
        setCargoWeight(waypointsDTO);

        List<Long> path = pathDetailsService.getPath(waypointsDTO).getPath();

        String stringPath = parser.parseLongListToString(path);
        order.setPath(stringPath);

        double maxCapacityInTons = pathDetailsService.getMaxCapacityInTons(path, waypointsDTO);
        order.setMaxCapacity(maxCapacityInTons);

        long id = orderDAO.saveOrder(order);
        logger.info("Create order by id: {} with max capacity {}", id, order.getMaxCapacity());

        double approximateLeadTime = pathDetailsService.getShiftHours(path);
        createOrderDetails(id, approximateLeadTime);

        jmsSender.send("order.topic", "+1 order");
    }

    private void setCargoWeight(List<WaypointDTO> waypointsDTO) {
        for (WaypointDTO dto : waypointsDTO) {
             double weight = cargoDAO.findCargoWeightById(dto.getCargoId());
            dto.setCargoWeight(weight);
        }
    }

    private void createOrderDetails(long id, double approximateLeadTime) {
        orderDAO.saveOrderDetails(id, approximateLeadTime);
        logger.info("Create order detail for order id: {} , with time in travel: {}", id, approximateLeadTime);
    }

    @Override
    public void deleteOrder(long orderId) {
        orderDAO.deleteOrder(orderId);
        logger.info("Delete order by id: {}", orderId);

        jmsSender.send("order.topic", "If order in last 10 send, else ignore");
    }


    @Override
    public List<TruckDTO> readTrucksForOrder(long orderId) {
        Order order = orderDAO.findOrderById(orderId);

        List<Long> path = parser.parseStringToLongList(order.getPath());

        double maxCapacity = order.getMaxCapacity();

        return orderDAO.findTrucksForOrder(maxCapacity, path.get(0));
    }

    @Override
    @Transactional
    public void addTruckToOrder(String truckId, long orderId) {
        Truck truck = orderDAO.findTruckEntityById(truckId);
        truck.setInUse(true);

        Order order = orderDAO.findOrderById(orderId);
        order.setDesignatedTruck(truck);

        orderDAO.updateOrder(order);
        logger.info("Add truck {} for order by id: {}", truckId, orderId);

        jmsSender.send("truck.topic", "+1 in use, -1 truck is free");
    }

    @Override
    @Transactional
    public List<DriverDTO> readDriversForOrder(long orderId) {
        long cityCode = orderDAO.findTruckByOrderId(orderId).getCurrentCity().getCityCode();
        String path = orderDAO.findOrderById(orderId).getPath();
        double shiftHours = pathDetailsService.getShiftHours(parser.parseStringToLongList(path));

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
        List<Driver> drivers = driverService.readDriversByIds(driversIds);
        for(Driver driver: drivers) {
            driver.setCurrentTruck(truck);
            driver.setOrderDetails(orderDetails);
        }
        driverService.updateDrivers(drivers);
        logger.info("Add driver to order by id: {}", orderId);
    }

    @Override
    public long getOrdersTotalNumbers() {
        return orderDAO.countOrders();
    }
}
