package com.logiweb.avaji.crud.cargo.dao;

import com.logiweb.avaji.entities.models.Cargo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Repository
@Transactional
public class CargoDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Cargo> findAllCargo() {
        TypedQuery<Cargo> query = entityManager.createNamedQuery("Cargo.findAllCargo", Cargo.class);

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
}
