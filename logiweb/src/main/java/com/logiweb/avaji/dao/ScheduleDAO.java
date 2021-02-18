package com.logiweb.avaji.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
@Transactional
public class ScheduleDAO {

    @PersistenceContext
    private EntityManager entityManager;


    public int refreshWorkedHours() {
        Query query = entityManager.createNamedQuery("Driver.refreshWorkedHours");
        return query.executeUpdate();
    }
}
