package com.logiweb.avaji.services.implementetions;

import com.logiweb.avaji.dao.CargoDAO;
import com.logiweb.avaji.dao.OrderDAO;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import com.logiweb.avaji.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import java.util.List;

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
    public List<Order> readOrdersByWaypointId(Long waypointId) {
        return null;
    }

    @Override
    public List<Cargo> readCargoByOrderId(Long orderId) {

        List<Waypoint> waypoints = orderDAO.findWaypointByOrderId(orderId);
        return cargoDAO.findCargoByWaypoints(waypoints);
    }

    @Override
    public void createOrder(Order order) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderDAO.deleteOrder(orderId);
    }
}
