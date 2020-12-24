package com.logiweb.avaji.services.implementetions;

import com.logiweb.avaji.dao.CargoDAO;
import com.logiweb.avaji.dao.OrderDAO;
import com.logiweb.avaji.dao.WaypointDAO;
import com.logiweb.avaji.entities.dto.DtoConverter;
import com.logiweb.avaji.entities.dto.WaypointDto;
import com.logiweb.avaji.entities.enums.WaypointType;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import com.logiweb.avaji.services.OrderService;
import com.logiweb.avaji.services.WaypointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final CargoDAO cargoDAO;
    private final WaypointDAO waypointDAO;
    private final DtoConverter converter;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, CargoDAO cargoDAO, WaypointDAO waypointDAO, DtoConverter converter) {
        this.orderDAO = orderDAO;
        this.cargoDAO = cargoDAO;
        this.waypointDAO = waypointDAO;
        this.converter = converter;
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
