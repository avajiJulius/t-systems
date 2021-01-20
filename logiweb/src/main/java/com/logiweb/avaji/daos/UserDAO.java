package com.logiweb.avaji.daos;

import com.logiweb.avaji.entities.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
@Transactional
public class UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public User findUserByEmail(String email) {
        TypedQuery<User> query = entityManager.createNamedQuery("User.findUserByEmail", User.class)
                .setParameter("email", email);
        return query.getSingleResult();
    }
}
