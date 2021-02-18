package com.logiweb.avaji.service.implementetions.management;

import com.logiweb.avaji.dao.CargoDAO;
import com.logiweb.avaji.dtos.*;
import com.logiweb.avaji.dao.OrderDAO;
import com.logiweb.avaji.entity.model.*;
import com.logiweb.avaji.service.api.management.DriverService;
import com.logiweb.avaji.service.api.mq.InformationProducerService;
import com.logiweb.avaji.service.implementetions.utils.DateTimeService;
import com.logiweb.avaji.service.implementetions.utils.PathParser;
import com.logiweb.avaji.service.api.management.OrderService;
import com.logiweb.avaji.service.implementetions.utils.Mapper;
import com.logiweb.avaji.service.api.path.PathDetailsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    private final InformationProducerService producerService;
    private final DateTimeService timeService;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, Mapper converter, PathDetailsService pathDetailsService,
                            PathParser parser, DriverService driverService, CargoDAO cargoDAO,
                            InformationProducerService informationProducerService, DateTimeService timeService) {
        this.orderDAO = orderDAO;
        this.converter = converter;
        this.pathDetailsService = pathDetailsService;
        this.parser = parser;
        this.driverService = driverService;
        this.cargoDAO = cargoDAO;
        this.producerService = informationProducerService;
        this.timeService = timeService;
    }

    @Override
    @Transactional
    public List<OrderDTO> readOrdersPage(int pageNumber, int pageSize) {
        int indexFrom = 0;
        if(pageNumber != 1) {
            indexFrom = (pageNumber - 1) * pageSize;
        }
        List<OrderDTO> orderPage = orderDAO.findOrdersPage(indexFrom, pageSize);

        fillOrders(orderPage);

        return orderPage;
    }


    @Override
    public void createOrderByWaypoints(Order order, CreateWaypointsDTO dto) {
        List<Waypoint> waypoints = converter.dtoToWaypoints(dto, order);
        orderDAO.saveWaypoints(waypoints);

        List<WaypointDTO> waypointsDTO = dto.getWaypointsDto();
        setCargoWeight(waypointsDTO);

        List<Long> path = pathDetailsService.getPath(waypointsDTO).getPath();

        Map<String, Object> pathDetails = calculatePathDetails(path, waypointsDTO);
        fillOrder(order, pathDetails);

        long id = orderDAO.saveOrder(order);
        logger.info("Create order by id: {} with max capacity {}", id, order.getMaxCapacity());

        createOrderDetails(id, path);

        producerService.updateOrderInformation();
    }


    @Override
    public void setCargoWeight(List<WaypointDTO> waypointsDTO) {
        for (WaypointDTO dto : waypointsDTO) {
             double weight = cargoDAO.findCargoWeightById(dto.getCargoId());
            dto.setCargoWeight(weight);
        }
    }


    @Override
    public void deleteOrder(long orderId) {
        orderDAO.deleteOrder(orderId);
        logger.info("Delete order by id: {}", orderId);

        producerService.updateOrderInformation();
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
        order.setLastEditDate(LocalDateTime.now());

        orderDAO.updateOrder(order);
        logger.info("Add truck {} for order by id: {}", truckId, orderId);

        producerService.updateOrderInformation();
        producerService.updateTruckInformation();
    }

    @Override
    @Transactional
    public List<DriverDTO> readDriversForOrder(long orderId) {
        long cityCode = orderDAO.findTruckByOrderId(orderId).getCurrentCity().getCityCode();
        String path = orderDAO.findOrderById(orderId).getPath();

        double shiftHours = calculateMaxShiftHours(path);

        return orderDAO.findDriverForOrder(shiftHours, cityCode);
    }



    @Override
    @Transactional
    public void addDriversToOrder(List<Long> driversIds, long orderId) {
        List<Driver> drivers = driverService.readDriversByIds(driversIds);
        fillDrivers(drivers, orderId);

        driverService.updateDrivers(drivers);
        logger.info("Add driver to order by id: {}", orderId);

        producerService.updateOrderInformation();
    }


    @Override
    public long getOrdersTotalNumbers() {
        return orderDAO.countOrders();
    }

    private void fillOrders(List<OrderDTO> orderPage) {
        for (OrderDTO orderDTO : orderPage) {
            if(orderDTO.getTruckId() != null) {
                orderDTO.setDrivers(orderDAO.findDriversByOrderId(orderDTO.getOrderId()));
                orderDTO.setShiftSize(
                        orderDAO.findTruckByOrderId(orderDTO.getOrderId()).getShiftSize());
            }
            orderDTO.setPrettyPath(parser.toPrettyPath(orderDTO.getPrettyPath()));
        }
    }

    private void fillOrder(Order order, Map<String, Object> pathDetails) {
        order.setPath((String) pathDetails.get("path"));
        order.setMaxCapacity((Double) pathDetails.get("capacity"));
        order.setLastEditDate(LocalDateTime.now());
    }

    private Map<String, Object> calculatePathDetails(List<Long> path, List<WaypointDTO> waypointsDTO) {
        Map<String, Object> pathDetails = new HashMap<>();

        pathDetails.put("path", parser.parseLongListToString(path));
        pathDetails.put("capacity", pathDetailsService.getMaxCapacityInTons(path, waypointsDTO));

        return pathDetails;
    }

    private void createOrderDetails(long id, List<Long> path) {
        double approximateLeadTime = pathDetailsService.getShiftHours(path);
        orderDAO.saveOrderDetails(id, approximateLeadTime);
        logger.info("Create order detail for order id: {} , with time in travel: {}", id, approximateLeadTime);
    }

    private double calculateMaxShiftHours(String path) {
        double shiftHours = pathDetailsService.getShiftHours(parser.parseStringToLongList(path));

        long untilEndOfMonth = timeService.calculateTimeUntilEndOfMonth();
        if(shiftHours > untilEndOfMonth) {
            shiftHours = (double) untilEndOfMonth;
        }
        return shiftHours;
    }

    private void fillDrivers(List<Driver> drivers, long orderId) {
        String truckId = orderDAO.findOrderById(orderId).getDesignatedTruck().getTruckId();
        OrderDetails orderDetails = orderDAO.findOrderDetails(orderId);
        Truck truck = orderDAO.findTruckEntityById(truckId);
        for(Driver driver: drivers) {
            driver.setCurrentTruck(truck);
            driver.setOrderDetails(orderDetails);
        }
    }

}
