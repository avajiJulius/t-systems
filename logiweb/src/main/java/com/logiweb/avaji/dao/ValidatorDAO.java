package com.logiweb.avaji.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class ValidatorDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public long checkEmailUnique(String email) {
        TypedQuery<Long> query = entityManager.createNamedQuery("User.countUserByEmail", Long.class)
                .setParameter("email", email);

        return query.getSingleResult();
    }

    public long checkIdUnique(String id) {
        TypedQuery<Long> query = entityManager.createNamedQuery("Truck.countTruckById", Long.class)
                .setParameter("id", id);

        return query.getSingleResult();
    }
}
