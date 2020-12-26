package com.logiweb.avaji.services.implementetions;

import com.logiweb.avaji.dao.*;
import com.logiweb.avaji.entities.dto.DriverPublicResponseDto;
import com.logiweb.avaji.entities.dto.DtoConverter;
import com.logiweb.avaji.entities.dto.WaypointDto;
import com.logiweb.avaji.entities.enums.WaypointType;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import com.logiweb.avaji.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

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
        return converter.driversToDtos(driverDAO.findDriverForOrder(shiftHours, cityCode));
    }

    private boolean validateOrderByWaypoints(List<Waypoint> waypoints) {
        List<Waypoint> loadWaypoints = waypoints.stream()
                .filter(w -> w.getWaypointType() == WaypointType.LOADING)
                .collect(Collectors.toList());
        List<Waypoint> unloadWaypoints = waypoints.stream()
                .filter(w -> w.getWaypointType() == WaypointType.UNLOADING)
                .collect(Collectors.toList());
        if(loadWaypoints.size() != unloadWaypoints.size()) {
            return false;
        }
        int count = 0;
        for (Waypoint load: loadWaypoints) {
            for (Waypoint unload: unloadWaypoints) {
                if(load.getWaypointCargo().getCargoId() == unload.getWaypointCargo().getCargoId()
                        & load.getWaypointCity().getCityCode() != unload.getWaypointCity().getCityCode()) {
                    count++;
                }
            }
        }
        if(count == loadWaypoints.size()) {
            return true;
        }
        return false;
    }
}
