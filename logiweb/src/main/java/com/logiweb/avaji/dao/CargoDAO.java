package com.logiweb.avaji.dao;

import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class CargoDAO {

    private EntityManager entityManager;

    public CargoDAO() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistence");
        entityManager = factory.createEntityManager();
    }

    public List<Cargo> findCargoByWaypoints(List<Waypoint> waypoints) {
        Query query = entityManager.createNamedQuery("Cargo.findCargoByWaypoints")
                .setParameter("waypoints", waypoints);
        return query.getResultList();
    }

    public List<Cargo> findCargoByOrderId(Long orderId) {
        TypedQuery<Cargo> query = entityManager.createNamedQuery("Cargo.findCargoByOrderId", Cargo.class)
                .setParameter("orderId", orderId);
        return query.getResultList();
    }

}
