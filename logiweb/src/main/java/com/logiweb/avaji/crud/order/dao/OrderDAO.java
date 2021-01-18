package com.logiweb.avaji.crud.order.dao;

import com.logiweb.avaji.crud.countrymap.dto.CityDTO;
import com.logiweb.avaji.crud.order.dto.OrderDTO;
import com.logiweb.avaji.entities.models.Order;
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

    public void saveOrder(Order order) {
        entityManager.persist(order);
        entityManager.flush();
    }

    public void updateOrder(Order order) {
        entityManager.merge(order);
    }

    public void deleteOrder(long orderId) {
        Order order = entityManager.find(Order.class, orderId);
        entityManager.remove(order);
    }


}
