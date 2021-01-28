package com.logiweb.avaji.dao;

import com.logiweb.avaji.dtos.*;
import com.logiweb.avaji.entitie.model.*;
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


    public Truck findTruckByOrderId(long orderId) {
        TypedQuery<Truck> query = entityManager.createNamedQuery("Order.findTruckByOrderId", Truck.class)
                .setParameter("orderId", orderId);
        return query.getSingleResult();
    }

    public List<TruckDTO> findTrucksForOrder(Double maxCapacity, long startCityCode) {
        TypedQuery<TruckDTO> query = entityManager.createNamedQuery("Truck.findTrucksForOrder", TruckDTO.class)
                .setParameter("maxCapacity", maxCapacity).setParameter("startCode", startCityCode);
        return query.getResultList();
    }


    public List<DriverDTO> findDriverForOrder(Double shiftHours, long cityCode) {
        TypedQuery<DriverDTO> query = entityManager.createNamedQuery("Driver.findDriversForOrder", DriverDTO.class)
                .setParameter("shiftHours", shiftHours).setParameter("cityCode", cityCode);
        return query.getResultList();
    }

    public List<DriverDTO> findDriversByOrderId(long orderId) {
        TypedQuery<DriverDTO> query = entityManager.createNamedQuery("Driver.findDriversByOrderId", DriverDTO.class)
                .setParameter("id", orderId);
        return query.getResultList();
    }

    public Truck findTruckEntityById(String truckId) {
        return entityManager.find(Truck.class, truckId);
    }



    public void saveOrderDetails(long id, double approximateLeadTime) {
        Order order = entityManager.find(Order.class, id);
        OrderDetails orderDetails = new OrderDetails.Builder()
                .withOrder(order)
                .withRemainingPath(order.getPath())
                .withRemainingWorkingTime(approximateLeadTime).build();

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
