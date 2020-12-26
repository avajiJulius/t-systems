package com.logiweb.avaji.dao;

import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class OrderDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Order> findAllOrders() {
        Query query = entityManager.createNamedQuery("Order.findAllOrders");
        return query.getResultList();
    }


    public void saveOrder(Order order) {
        entityManager.persist(order);
        entityManager.flush();
    }

    public void deleteOrder(Integer orderId) {
        Order order = entityManager.find(Order.class, orderId);
        entityManager.remove(order);
    }

    public Order findOrderById(Integer orderId) {
        TypedQuery<Order> query = entityManager.createNamedQuery("Order.findOrderById", Order.class)
                .setParameter("orderId", orderId);
        return query.getSingleResult();
    }

    public Order findOrderByTruckId(String truckId) {
        TypedQuery<Order> query = entityManager.createNamedQuery("Order.findOrderByTruckId", Order.class)
                .setParameter("truckId", truckId);
        return query.getSingleResult();
    }


    public Truck findTruckByOrderId(Integer orderId) {
        TypedQuery<Truck> query = entityManager.createNamedQuery("Order.findTruckByOrderId", Truck.class)
                .setParameter("orderId", orderId);
        return query.getSingleResult();
    }

    public void updateOrder(Order order) {
        entityManager.merge(order);
    }


}
