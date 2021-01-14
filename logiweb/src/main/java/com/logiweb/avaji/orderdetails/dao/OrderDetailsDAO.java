package com.logiweb.avaji.orderdetails.dao;

import com.logiweb.avaji.crud.driver.dto.DriverDTO;
import com.logiweb.avaji.crud.truck.dto.TruckDTO;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.entities.models.utils.Waypoint;
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

    public List<Waypoint> findWaypointsOfThisOrder(long orderId){
        TypedQuery<Waypoint> query = entityManager.createNamedQuery("Waypoint.findWaypointsOfThisOrder", Waypoint.class)
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

    public Truck findTruckEntityById(String truckId) {
         return entityManager.find(Truck.class, truckId);
    }
}
