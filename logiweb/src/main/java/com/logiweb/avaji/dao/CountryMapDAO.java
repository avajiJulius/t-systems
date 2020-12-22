package com.logiweb.avaji.dao;

import com.logiweb.avaji.entities.models.utils.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class CountryMapDAO {

    private EntityManager entityManager;

    @Autowired
    public CountryMapDAO(EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public List<City> findAllCities() {
        TypedQuery<City> query = entityManager.createNamedQuery("City.findAllCities", City.class);
        return query.getResultList();
    }

    public City findCityByCode(Integer cityCode) {
        return entityManager.find(City.class, cityCode);
    }
}
