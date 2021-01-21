package com.logiweb.avaji.daos;

import com.logiweb.avaji.dtos.OrderDetailsDTO;
import com.logiweb.avaji.dtos.WaypointDTO;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.OrderDetails;
import com.logiweb.avaji.entities.models.WorkShift;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

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



    public void updateOrderDetails(OrderDetails updatedOrderDetails) {
        entityManager.merge(updatedOrderDetails);
    }

    public OrderDetails findOrderDetailsEntity(long orderId) {
        return entityManager.find(OrderDetails.class, orderId);
    }

    @Transactional
    public void updateOnCompletedOrder(long orderId) {
        entityManager.createNamedQuery("Order.updateOnCompletedOrder")
                .setParameter("id", orderId).executeUpdate();
        entityManager.createNamedQuery("Driver.updateOnCompletedOrder")
                 .setParameter("id", orderId).executeUpdate();
    }

}
