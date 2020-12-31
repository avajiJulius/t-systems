package com.logiweb.avaji.crud.cargo.dao;

import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Repository
@Transactional
public class CargoDAO {

    private EntityManager entityManager;

    @Autowired
    public CargoDAO(EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public List<Cargo> findAllCargo() {
        TypedQuery<Cargo> query = entityManager.createNamedQuery("Cargo.findAllCargo", Cargo.class);

        return query.getResultList();
    }

    public List<Cargo> findCargoByWaypoints(List<Waypoint> waypoints) {
        Query query = entityManager.createNamedQuery("Cargo.findCargoByWaypoints")
                .setParameter("waypoints", waypoints);
        return query.getResultList();
    }

    public List<Cargo> findCargoByOrderId(Integer orderId) {
        TypedQuery<Cargo> query = entityManager.createNamedQuery("Cargo.findCargoByOrderId", Cargo.class)
                .setParameter("orderId", orderId);
        return query.getResultList();
    }

    public Cargo findCargoById(Integer cargoId) {
        return entityManager.find(Cargo.class, cargoId);
    }

    public void updateCargo(Cargo cargo) {
        entityManager.merge(cargo);
    }
}
