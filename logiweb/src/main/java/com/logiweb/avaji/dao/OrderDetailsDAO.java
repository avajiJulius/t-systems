package com.logiweb.avaji.dao;

import com.logiweb.avaji.dtos.OrderDetailsDTO;
import com.logiweb.avaji.entity.model.OrderDetails;
import com.logiweb.avaji.entity.model.Truck;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;

@Repository
@Transactional
public class OrderDetailsDAO {

    private static final Logger logger = LogManager.getLogger(OrderDetailsDAO.class);

    @PersistenceContext
    EntityManager entityManager;

    public OrderDetailsDTO findOrderDetails(long driverId) {
        OrderDetailsDTO orderDetails = null;

        TypedQuery<OrderDetailsDTO> query = entityManager.createNamedQuery("OrderDetails.findOrderDetails", OrderDetailsDTO.class)
                .setParameter("id", driverId);

        try {
            orderDetails = query.getSingleResult();
        } catch (NoResultException e) {
            logger.info("No order details for driver with id {}", driverId);
        }

        return orderDetails;
    }

    public long findOrderIdOfDriverId(long driverId) {
        TypedQuery<Long> query = entityManager.createNamedQuery("Order.findOrderIdOfDriverId", Long.class)
                .setParameter("id", driverId);

        return query.getSingleResult();
    }

    public void updateOrderDetails(OrderDetails updatedOrderDetails, long cityCode) {
        entityManager.createNamedQuery("Driver.updateDriverOnCityChange")
                .setParameter("cityCode", cityCode).setParameter("id", updatedOrderDetails.getId()).executeUpdate();

        entityManager.createNamedQuery("Truck.updateTruckOnCityChange")
                .setParameter("cityCode", cityCode).setParameter("id", updatedOrderDetails.getId()).executeUpdate();

        entityManager.merge(updatedOrderDetails);
    }

    public OrderDetails findOrderDetailsEntity(long orderId) {
        return entityManager.find(OrderDetails.class, orderId);
    }

    public void freeTruck(String truckId) {
        Truck truck = entityManager.find(Truck.class, truckId);
        truck.setInUse(false);

        entityManager.merge(truck);
    }
}
