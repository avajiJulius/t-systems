package com.logiweb.avaji.crud.order.dao;

import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.Truck;
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

    public void deleteOrder(long orderId) {
        Order order = entityManager.find(Order.class, orderId);
        entityManager.remove(order);
    }

    public Order findOrderById(long orderId) {
        TypedQuery<Order> query = entityManager.createNamedQuery("Order.findOrderById", Order.class)
                .setParameter("orderId", orderId);
        return query.getSingleResult();
    }

    public Order findOrderByTruckId(String truckId) {
        TypedQuery<Order> query = entityManager.createNamedQuery("Order.findOrderByTruckId", Order.class)
                .setParameter("truckId", truckId);
        return query.getSingleResult();
    }


    public void updateOrder(Order order) {
        entityManager.merge(order);
    }


}
