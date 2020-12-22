package com.logiweb.avaji.dao;

import com.logiweb.avaji.entities.models.Truck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Repository
@Transactional
public class TruckDAO {

    @PersistenceContext
    private EntityManager entityManager;


    public List<Truck> findTrucks() {
        Query query = entityManager.createNamedQuery("Truck.findTrucks");
        return query.getResultList();
    }

    public Truck findTruckById(String truckId) {
         return entityManager.find(Truck.class, truckId);
    }

    public void saveTruck(Truck truck) {
        entityManager.persist(truck);
        entityManager.flush();
    }

    public void deleteTruck(String id) {
        Truck truck = entityManager.find(Truck.class, id);
        entityManager.remove(truck);
    }



}
