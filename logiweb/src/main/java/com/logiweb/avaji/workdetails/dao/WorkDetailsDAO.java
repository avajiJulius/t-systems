package com.logiweb.avaji.workdetails.dao;

import com.logiweb.avaji.entities.models.utils.WorkDetails;
import com.logiweb.avaji.entities.models.utils.WorkShift;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class WorkDetailsDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public WorkDetails findWorkDetailsById(Integer workDetailsId) {
        return entityManager.find(WorkDetails.class, workDetailsId);
    }

    public WorkShift findShiftByDriverId(Integer driverId) {
        TypedQuery<WorkShift> query = entityManager.createNamedQuery("WorkShift.findShiftByDriverId", WorkShift.class)
                .setParameter("driverId", driverId);
        return query.getSingleResult();
    }

    public WorkDetails findWorkDetailsByDriverId(Integer driverId) {
        TypedQuery<WorkDetails> query = entityManager.createNamedQuery("WorkDetails.findWorkDetailsByDriverId", WorkDetails.class)
                .setParameter("driverId", driverId);
        return query.getSingleResult();
    }

    public void updateWorkDetails(WorkDetails workDetails) {
        entityManager.merge(workDetails);
    }
}
