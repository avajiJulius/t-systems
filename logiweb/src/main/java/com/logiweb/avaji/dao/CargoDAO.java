package com.logiweb.avaji.dao;

import com.logiweb.avaji.entities.models.Cargo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CargoDAO {

    private EntityManager entityManager;

    public CargoDAO() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistence");
        entityManager = factory.createEntityManager();
    }

    public List<Cargo> findCargoByOrderId(Long orderId) {
        Query query = entityManager.createNamedQuery("Cargo.findCargoByOrderId")
                .setParameter("orderId", orderId);
        return query.getResultList();
    }

}
