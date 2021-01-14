package com.logiweb.avaji.crud.truck.dao;

import com.logiweb.avaji.crud.truck.dto.TruckDTO;
import com.logiweb.avaji.entities.models.Truck;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Repository
@Transactional
public class TruckDAO {

    @PersistenceContext
    private EntityManager entityManager;


    public List<TruckDTO> findTrucks() {
        Query query = entityManager.createNamedQuery("Truck.findAllTrucks", TruckDTO.class);
        return query.getResultList();
    }

    public TruckDTO findTruckById(String id) {
        TypedQuery<TruckDTO> query = entityManager.createNamedQuery("Truck.findTruckById", TruckDTO.class)
                .setParameter("id", id);
        return query.getSingleResult();
    }


    public void saveTruck(Truck truck) {
        entityManager.persist(truck);
        entityManager.flush();
    }



    public void updateTruck(Truck truck) {
        entityManager.merge(truck);
    }

    public void deleteTruck(String id) {
        Truck truck = entityManager.find(Truck.class, id);
        entityManager.remove(truck);
    }


}
