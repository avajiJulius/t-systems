package com.logiweb.avaji.daos;

import com.logiweb.avaji.dtos.DriverDTO;
import com.logiweb.avaji.dtos.OrderDTO;
import com.logiweb.avaji.dtos.TruckDTO;
import com.logiweb.avaji.dtos.WaypointDTO;
import com.logiweb.avaji.entities.models.*;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class OrderDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<OrderDTO> findAllOrders() {
        TypedQuery<OrderDTO> query = entityManager.createNamedQuery("Order.findAllOrders", OrderDTO.class);
        return query.getResultList();
    }

    public Order findOrderById(long orderId) {
        TypedQuery<Order> query = entityManager.createNamedQuery("Order.findOrderById", Order.class)
                .setParameter("orderId", orderId);
        return query.getSingleResult();
    }

    public long saveOrder(Order savedOrder) {
        entityManager.persist(savedOrder);
        entityManager.flush();
        return savedOrder.getId();
    }

    public void updateOrder(Order order) {
        entityManager.merge(order);
    }

    public void deleteOrder(long orderId) {
        Order order = entityManager.find(Order.class, orderId);
        entityManager.remove(order);
    }

    public List<WaypointDTO> findWaypointsOfThisOrder(long orderId){
        TypedQuery<WaypointDTO> query = entityManager.createNamedQuery("Waypoint.findWaypointsOfThisOrder", WaypointDTO.class)
                .setParameter("orderId", orderId);
        return query.getResultList();
    }

    public List<Waypoint> findWaypointsOfThisCargo(long cargoId){
        TypedQuery<Waypoint> query = entityManager.createNamedQuery("Waypoint.findWaypointsOfThisCargo", Waypoint.class)
                .setParameter("cargoId", cargoId);
        return query.getResultList();
    }

    public Truck findTruckByOrderId(long orderId) {
        TypedQuery<Truck> query = entityManager.createNamedQuery("Order.findTruckByOrderId", Truck.class)
                .setParameter("orderId", orderId);
        return query.getSingleResult();
    }

    public List<TruckDTO> findTrucksForOrder(Double maxCapacity) {
        TypedQuery<TruckDTO> query = entityManager.createNamedQuery("Truck.findTrucksForOrder", TruckDTO.class)
                .setParameter("maxCapacity", maxCapacity);
        return query.getResultList();
    }


    public List<DriverDTO> findDriverForOrder(Double shiftHours, long cityCode) {
        TypedQuery<DriverDTO> query = entityManager.createNamedQuery("Driver.findDriversForOrder", DriverDTO.class)
                .setParameter("shiftHours", shiftHours).setParameter("cityCode", cityCode);
        return query.getResultList();
    }



    public Order findOrderByTruckId(String truckId) {
        TypedQuery<Order> query = entityManager.createNamedQuery("Order.findOrderByTruckId", Order.class)
                .setParameter("truckId", truckId);
        return query.getSingleResult();
    }

    public Truck findTruckEntityById(String truckId) {
        return entityManager.find(Truck.class, truckId);
    }


    public List<Driver> findDriversByIds(List<Long> driversIds) {
        TypedQuery<Driver> query = entityManager.createNamedQuery("Driver.findDriversByIds", Driver.class)
                .setParameter("driversIds", driversIds);
        return query.getResultList();
    }

    public void saveOrderDetails(long id) {
        Order order = entityManager.find(Order.class, id);
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrder(order);
        orderDetails.setRemainingPath(order.getPath());
        entityManager.persist(orderDetails);
        entityManager.flush();
    }

    public OrderDetails findOrderDetails(long orderId) {
        return entityManager.find(OrderDetails.class, orderId);
    }

    public void saveWaypoints(List<Waypoint> waypoints) {
        for(Waypoint waypoint : waypoints) {
            entityManager.persist(waypoint);
        }
        entityManager.flush();
    }
}
