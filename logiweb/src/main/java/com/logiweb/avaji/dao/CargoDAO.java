package com.logiweb.avaji.dao;

import com.logiweb.avaji.entity.model.Cargo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Repository
@Transactional
public class CargoDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Cargo> findAllFreeCargo() {
        TypedQuery<Cargo> query = entityManager.createNamedQuery("Cargo.findAllFreeCargo", Cargo.class);

        return query.getResultList();
    }

    public List<Cargo> findCargoByOrderId(long orderId) {
        TypedQuery<Cargo> query = entityManager.createNamedQuery("Cargo.findCargoByOrderId", Cargo.class)
                .setParameter("orderId", orderId);
        return query.getResultList();
    }

    public Cargo findCargoById(long cargoId) {
        return entityManager.find(Cargo.class, cargoId);
    }

    public void updateCargo(Cargo cargo) {
        entityManager.merge(cargo);
    }

    public double findCargoWeightById(long cargoId) {
        TypedQuery<Double> query = entityManager.createNamedQuery("Cargo.findCargoWeightById", Double.class)
                .setParameter("id", cargoId);
        return query.getSingleResult();
    }

}
