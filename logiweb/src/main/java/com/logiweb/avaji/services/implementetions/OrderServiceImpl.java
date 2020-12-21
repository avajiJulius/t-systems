package com.logiweb.avaji.services.implementetions;

import com.logiweb.avaji.dao.CargoDAO;
import com.logiweb.avaji.dao.OrderDAO;
import com.logiweb.avaji.entities.enums.WaypointType;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import com.logiweb.avaji.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final CargoDAO cargoDAO;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, CargoDAO cargoDAO) {
        this.orderDAO = orderDAO;
        this.cargoDAO = cargoDAO;
    }


    @Override
    public List<Order> readAllOrders() {
        return orderDAO.findAllOrders();
    }

    @Override
    public void createOrder(Order order) {

    }

    @Override
    public void createOrderByWaypoints(List<Waypoint> waypoints) {
        Order order = new Order();
        List<Cargo> load = waypoints.stream()
                .filter(w -> w.getWaypointType() == WaypointType.LOADING)
                .map(Waypoint::getWaypointCargo)
                .collect(Collectors.toList());
        List<Cargo> unload = waypoints.stream()
                .filter(w -> w.getWaypointType() == WaypointType.UNLOADING)
                .map(Waypoint::getWaypointCargo)
                .collect(Collectors.toList());
        if(load.size() != unload.size()) {
            return;
        }
        for(Cargo loadCargo: load) {
            for(Cargo unloadCargo: unload) {
                if(loadCargo.getCargoId() == unloadCargo.getCargoId()) {
                    load.remove(loadCargo);
                    unload.remove(unloadCargo);
                    continue;
                }
            }
        }
        if(load.isEmpty() && unload.isEmpty()) {
            order.setWaypoints(waypoints);
            order.setCompleted(false);
            orderDAO.saveOrder(order);
        }
    }

    @Override
    public void deleteOrder(Integer orderId) {
        orderDAO.deleteOrder(orderId);
    }
}
