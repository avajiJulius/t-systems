package com.logiweb.avaji.dao;

import com.logiweb.avaji.entities.models.utils.WorkShift;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Repository
@Transactional
public class WorkShiftDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public void createShift(WorkShift workShift) {
        entityManager.persist(workShift);
        entityManager.flush();
    }

    public WorkShift findShiftByDriverId(Integer driverId) {
        TypedQuery<WorkShift> query = entityManager.createNamedQuery("WorkShift.findShiftByDriverId", WorkShift.class)
                .setParameter("driverId", driverId);
        return query.getSingleResult();
    }
}
