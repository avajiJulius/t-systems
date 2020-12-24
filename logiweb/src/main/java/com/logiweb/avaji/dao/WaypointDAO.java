package com.logiweb.avaji.dao;

import com.logiweb.avaji.entities.models.utils.Waypoint;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class WaypointDAO {

    @PersistenceContext
    EntityManager entityManager;

    public List<Waypoint> findWaypointsOfThisOrder(Integer orderId){
        TypedQuery<Waypoint> query = entityManager.createNamedQuery("Waypoint.findWaypointsOfThisOrder", Waypoint.class)
                .setParameter("orderId", orderId);
        return query.getResultList();
    }

    public List<Waypoint> findWaypointsOfThisCargo(Integer cargoId){
        TypedQuery<Waypoint> query = entityManager.createNamedQuery("Waypoint.findWaypointsOfThisCargo", Waypoint.class)
                .setParameter("cargoId", cargoId);
        return query.getResultList();
    }

    public void saveWaypoints(List<Waypoint> waypoints) {
        for(Waypoint waypoint: waypoints) {
            entityManager.persist(waypoint);
        }
        entityManager.flush();
    }
}
