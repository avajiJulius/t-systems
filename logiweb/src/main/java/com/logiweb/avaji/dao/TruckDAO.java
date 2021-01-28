package com.logiweb.avaji.dao;

import com.logiweb.avaji.dtos.TruckDTO;
import com.logiweb.avaji.entity.model.Truck;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Repository
@Transactional
public class TruckDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<TruckDTO> findTrucksPage(int indexFrom, int pageSize) {
        TypedQuery<TruckDTO> query = entityManager.createNamedQuery("Truck.findAllTrucks", TruckDTO.class)
                .setFirstResult(indexFrom).setMaxResults(pageSize);
        return query.getResultList();
    }

    public TruckDTO findTruckById(String id) {
        TypedQuery<TruckDTO> query = entityManager.createNamedQuery("Truck.findTruckById", TruckDTO.class)
                .setParameter("id", id);
        return query.getSingleResult();
    }


    public boolean saveTruck(Truck truck) {
        entityManager.persist(truck);
        entityManager.flush();
        return entityManager.contains(truck);
    }

    public TruckDTO findTruckByOrderId(long orderId) {
        TypedQuery<TruckDTO> query = entityManager.createNamedQuery("Truck.findTruckByOrderId", TruckDTO.class)
                .setParameter("id", orderId);
        return query.getSingleResult();
    }

    public void updateTruck(Truck truck) {
        entityManager.merge(truck);
    }

    public boolean deleteTruck(String id) {
        Truck truck = entityManager.find(Truck.class, id);
        entityManager.remove(truck);
        return !entityManager.contains(truck);
    }


    public long countDriversOfTruck(String truckId) {
        TypedQuery<Long> query = entityManager.createNamedQuery("Driver.countDriversOfTruck", Long.class)
                .setParameter("id", truckId);
        return query.getSingleResult();
    }



    public long countTrucks() {
        return entityManager.createNamedQuery("Truck.countTrucks", Long.class).getSingleResult();
    }
}
