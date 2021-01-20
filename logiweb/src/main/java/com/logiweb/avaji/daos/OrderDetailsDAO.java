package com.logiweb.avaji.daos;

import com.logiweb.avaji.dtos.DriverDTO;
import com.logiweb.avaji.dtos.OrderDetailsDTO;
import com.logiweb.avaji.dtos.TruckDTO;
import com.logiweb.avaji.entities.models.*;
import com.logiweb.avaji.exceptions.DriverNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class OrderDetailsDAO {

    @PersistenceContext
    EntityManager entityManager;

    public OrderDetailsDTO findOrderDetails(long driverId) {
        TypedQuery<OrderDetailsDTO> query = entityManager.createNamedQuery("OrderDetails.findOrderDetails", OrderDetailsDTO.class)
                .setParameter("id", driverId);
        return query.getSingleResult();
    }

    public long findOrderIdOfDriverId(long driverId) {
        TypedQuery<Long> query = entityManager.createNamedQuery("Order.findOrderIdOfDriverId", Long.class)
                .setParameter("id", driverId);
        return query.getSingleResult();
    }

    public List<DriverDTO> findDriversByOrderId(long orderId) {
        TypedQuery<DriverDTO> query = entityManager.createNamedQuery("Driver.findDriversByOrderId", DriverDTO.class)
                .setParameter("id", orderId);
        return query.getResultList();
    }

    public void updateOrderDetails(OrderDetailsDTO updatedOrderDetails) {
        OrderDetails orderDetails = entityManager.find(OrderDetails.class, updatedOrderDetails.getId());
        orderDetails.setRemainingPath(updatedOrderDetails.getRemainingPathString());
        entityManager.merge(orderDetails);
    }
}
