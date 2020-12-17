package com.logiweb.avaji.dao;

import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.Order;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

public class OrderDAO {

    private EntityManager entityManager;

    public OrderDAO() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistence");
        entityManager = factory.createEntityManager();
    }

    public List<Order> findAllOrders() {
        Query query = entityManager.createNamedQuery("Order.findAllOrders");
        return query.getResultList();
    }

    public List<Order> findOrdersByWaypointId(Long waypointId) {
        Query query = entityManager.createNamedQuery("Order.findOrdersByWaypointId");
        return query.getResultList();
    }

    @Transactional
    public void saveOrder(Order order) {
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.persist(order);
            transaction.commit();
        } finally {
            if(transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void deleteOrder(Long orderId) {
        Query query = entityManager.createNamedQuery("Order.deleteOrder");
        query.executeUpdate();
    }

}
