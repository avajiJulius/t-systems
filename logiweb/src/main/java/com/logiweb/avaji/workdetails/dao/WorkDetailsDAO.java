package com.logiweb.avaji.workdetails.dao;

import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.utils.WorkDetails;
import com.logiweb.avaji.entities.models.utils.WorkShift;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class WorkDetailsDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public WorkDetails findWorkDetailsById(long workDetailsId) {
        return entityManager.find(WorkDetails.class, workDetailsId);
    }

    public WorkShift findShiftByDriverId(long driverId) {
        TypedQuery<WorkShift> query = entityManager.createNamedQuery("WorkShift.findShiftByDriverId", WorkShift.class)
                .setParameter("driverId", driverId);
        return query.getSingleResult();
    }

    public WorkDetails findWorkDetailsByDriverId(long driverId) {
        TypedQuery<WorkDetails> query = entityManager.createNamedQuery("WorkDetails.findWorkDetailsByDriverId", WorkDetails.class)
                .setParameter("driverId", driverId);
        return query.getSingleResult();
    }

    public List<Driver> findDriversByIds(List<Long> driversIds) {
        TypedQuery<Driver> query = entityManager.createNamedQuery("Driver.findDriversByIds", Driver.class)
                .setParameter("driversIds", driversIds);
        return query.getResultList();
    }


    public void updateWorkDetails(WorkDetails workDetails) {
        entityManager.merge(workDetails);
    }
}
