package com.logiweb.avaji.dao;

import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderDAO {

    private EntityManager entityManager;

    @Autowired
    public OrderDAO(EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public List<Order> findAllOrders() {
        Query query = entityManager.createNamedQuery("Order.findAllOrders");
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

    public void deleteOrder(Integer orderId) {
        Query query = entityManager.createNamedQuery("Order.deleteOrder");
        query.executeUpdate();
    }

}
