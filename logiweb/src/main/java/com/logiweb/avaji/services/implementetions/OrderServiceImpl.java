package com.logiweb.avaji.services.implementetions;

import com.logiweb.avaji.dao.*;
import com.logiweb.avaji.entities.dto.DriverPublicResponseDto;
import com.logiweb.avaji.entities.dto.WaypointDto;
import com.logiweb.avaji.entities.enums.WaypointType;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import com.logiweb.avaji.exceptions.CityValidateException;
import com.logiweb.avaji.exceptions.LoadAndUnloadValidateException;
import com.logiweb.avaji.services.api.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    private final OrderDAO orderDAO;
    private final TruckDAO truckDAO;
    private final DriverDAO driverDAO;
    private final DtoConverter converter;
    private final ComputingService computingService;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, TruckDAO truckDAO, DriverDAO driverDAO,
                            DtoConverter converter, ComputingService computingService) {
        this.orderDAO = orderDAO;
        this.truckDAO = truckDAO;
        this.driverDAO = driverDAO;
        this.converter = converter;
        this.computingService = computingService;
    }

    @Override
    public List<Order> readAllOrders() {
        return orderDAO.findAllOrders();
    }


    @Override
    public void createOrder(Order order) {

    }

    @Override
    public void createOrderByWaypoints(Order order, List<WaypointDto> waypointsDto) {
        List<Waypoint> waypoints = converter.dtosToWaypoints(waypointsDto, order);
        order.setWaypoints(waypoints);
        if(validateOrderByWaypoints(waypoints)) {
            orderDAO.saveOrder(order);
        }
    }

    @Override
    public void deleteOrder(Integer orderId) {
        orderDAO.deleteOrder(orderId);
    }

    @Override
    public void addTruckToOrder(String truckId, Integer orderId) {
        Truck truck = truckDAO.findTruckById(truckId);
        Order order = orderDAO.findOrderById(orderId);
        order.setDesignatedTruck(truck);
        orderDAO.updateOrder(order);
    }

    @Override
    public List<DriverPublicResponseDto> readDriverForOrder(Integer orderId) {
        Order order = orderDAO.findOrderById(orderId);
        Integer cityCode = order.getDesignatedTruck().getCurrentCity().getCityCode();
        Double shiftHours = computingService.new OrderCalculation(orderId).getShiftHours();

        long untilEndOfMonth = calculateTimeUntilEndOfMonth();
        if(shiftHours > untilEndOfMonth) {
            shiftHours = (double) untilEndOfMonth;
        }

        return converter.driversToDtos(driverDAO.findDriverForOrder(shiftHours, cityCode));
    }

    private long calculateTimeUntilEndOfMonth() {
        LocalDateTime to = LocalDateTime.now().withHour(0).with(TemporalAdjusters.firstDayOfNextMonth());
        LocalDateTime from = LocalDateTime.now();
        long until = from.until(to, ChronoUnit.HOURS);
        return until;
    }

    private boolean validateOrderByWaypoints(List<Waypoint> waypoints) {
        if (cityValidate(waypoints) && loadAndUnloadValidate(waypoints)) {
            return true;
        }
        return false;
    }

    private boolean loadAndUnloadValidate(List<Waypoint> waypoints) {
        List<Cargo> load = waypoints.stream()
                .filter(w -> w.getWaypointType() == WaypointType.LOADING)
                .map(Waypoint::getWaypointCargo).distinct()
                .sorted(Comparator.comparingInt(Cargo::getCargoId))
                .collect(Collectors.toList());
        List<Cargo> unload = waypoints.stream()
                .filter(w -> w.getWaypointType() == WaypointType.UNLOADING)
                .map(Waypoint::getWaypointCargo).distinct()
                .sorted(Comparator.comparingInt(Cargo::getCargoId))
                .collect(Collectors.toList());
        if(load.size() != unload.size()) {
            throw new LoadAndUnloadValidateException();
        }
        if(!load.equals(unload)) {
            throw new LoadAndUnloadValidateException();
        }
        return true;
    }
    private boolean cityValidate(List<Waypoint> waypoints) {
        List<City> load = waypoints.stream()
                .filter(w -> w.getWaypointType() == WaypointType.LOADING)
                .map(Waypoint::getWaypointCity).distinct()
                .sorted(Comparator.comparingInt(City::getCityCode))
                .collect(Collectors.toList());
        List<City> unload = waypoints.stream()
                .filter(w -> w.getWaypointType() == WaypointType.UNLOADING)
                .map(Waypoint::getWaypointCity).distinct()
                .sorted(Comparator.comparingInt(City::getCityCode))
                .collect(Collectors.toList());
        if(load.size() != unload.size()) {
            throw new CityValidateException();
        }
        if(!load.equals(unload)) {
            throw new CityValidateException();
        }
        return true;
    }
}
