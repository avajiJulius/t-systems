package com.logiweb.avaji.crud.workdetails.dao;

import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.utils.WorkDetails;
import com.logiweb.avaji.entities.models.utils.WorkShift;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class WorkDetailsDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public WorkDetails findWorkDetailsById(long workDetailsId) {
        return entityManager.find(WorkDetails.class, workDetailsId);
    }

    public WorkShift findShiftById(long id) {
        return entityManager.find(WorkShift.class, id);
    }

    public WorkDetails findWorkDetailsByDriverId(long id) {
        return entityManager.find(WorkDetails.class, id);
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
