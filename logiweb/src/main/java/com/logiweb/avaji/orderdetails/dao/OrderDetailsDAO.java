package com.logiweb.avaji.orderdetails.dao;

import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    public int findDriversByTruckIdAndCount(String truckId) {
        Query query = entityManager.createNamedQuery("Driver.findDriversByTruckIdAndCount")
                .setParameter("truckId", truckId);
        return (int) query.getSingleResult();
    }
}
