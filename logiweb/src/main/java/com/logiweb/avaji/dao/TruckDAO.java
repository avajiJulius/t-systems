package com.logiweb.avaji.dao;

import com.logiweb.avaji.entities.models.Truck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionManager;

import javax.persistence.*;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.util.List;

@Repository
public class TruckDAO {

    private EntityManager entityManager;

    @Autowired
    public TruckDAO(EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public List<Truck> findTrucks() {
        Query query = entityManager.createNamedQuery("Truck.findTrucks");
        return query.getResultList();
    }

    public Truck findTruckById(String truckId) {
        Query query = entityManager.createNamedQuery("Truck.findTruckById");
        query.setParameter("truckId", truckId);
        return (Truck) query.getSingleResult();
    }

    @Transactional
    public void saveTruck(Truck truck) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(truck);
            transaction.commit();
        } finally {
            if(transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    @Transactional
    public void updateTruck(Truck updatedTruck) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(updatedTruck);
            transaction.commit();
        } finally {
            if(transaction.isActive()) {
                transaction.rollback();
            }
        }
    }


    public void deleteTruck(String id) {
        Query query = entityManager.createNamedQuery("Truck.deleteTruck");
        query.setParameter("truckId", id);
        query.executeUpdate();
    }


}
