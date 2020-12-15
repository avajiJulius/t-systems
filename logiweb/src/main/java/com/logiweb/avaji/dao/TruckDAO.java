package com.logiweb.avaji.dao;

import com.logiweb.avaji.entities.models.Truck;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class TruckDAO {

    private EntityManager entityManager;

    public TruckDAO() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistence");
        entityManager = factory.createEntityManager();
    }

    public List<Truck> findTrucks() {
        Query query = entityManager.createNamedQuery("Truck.findTrucks");
        return query.getResultList();
    }

    public Truck findTruckById(Long truckId) {
        Query query = entityManager.createNamedQuery("Truck.findTruckById");
        query.setParameter("truckId", truckId);
        return (Truck) query.getSingleResult();
    }

    @Transactional
    public void saveTruck(Truck truck) {
        entityManager.persist(truck);
    }

    @Transactional
    public void updateTruck(Truck updatedTruck) {
        entityManager.merge(updatedTruck);
    }


    public void deleteTruck(Long id) {
        Query query = entityManager.createNamedQuery("Truck.deleteTruck");
        query.setParameter("truckId", id);
        query.executeUpdate();
    }


}
