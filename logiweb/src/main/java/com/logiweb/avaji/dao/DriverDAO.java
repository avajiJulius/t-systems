package com.logiweb.avaji.dao;

import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.Truck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class DriverDAO {

    private EntityManager entityManager;

    @Autowired
    public DriverDAO(EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public List<Driver> findDrivers() {
        Query query = entityManager.createNamedQuery("Driver.findDrivers");
        return query.getResultList();
    }

    public Driver findDriverById(Integer driverId) {
        Query query = entityManager.createNamedQuery("Driver.findDriverById");
        query.setParameter("driverId", driverId);
        return (Driver) query.getSingleResult();
    }

    @Transactional
    public void saveDriver(Driver driver) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(driver);
            transaction.commit();
        } finally {
            if(transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    @Transactional
    public void updateDriver(Driver updatedDriver) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(updatedDriver);
            transaction.commit();
        } finally {
            if(transaction.isActive()) {
                transaction.rollback();
            }
        }
    }


    public void deleteDriver(Integer driverId) {
        Query query = entityManager.createNamedQuery("Driver.deleteDriver");
        query.setParameter("driverId", driverId);
        query.executeUpdate();
    }


}
