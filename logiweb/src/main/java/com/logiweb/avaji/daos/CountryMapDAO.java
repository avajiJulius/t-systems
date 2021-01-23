package com.logiweb.avaji.daos;

import com.logiweb.avaji.dtos.CityDTO;
import com.logiweb.avaji.dtos.RoadDTO;
import com.logiweb.avaji.entities.models.City;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Repository
@Transactional
public class CountryMapDAO {

    @PersistenceContext
    private EntityManager entityManager;


    public List<CityDTO> findAllCities() {
        TypedQuery<CityDTO> query = entityManager.createNamedQuery("City.findAllCities", CityDTO.class);
        return query.getResultList();
    }

    public City findCityByCode(long cityCode) {
        return entityManager.find(City.class, cityCode);
    }

    public List<Long> findConnectedCities(long cityCode) {
        Query query = entityManager.createNamedQuery("City.findConnectedCities")
                .setParameter("cityCode", cityCode);
        return query.getResultList();
    }

    public double findDistance(long cityCodeA, long cityCodeB) {
        TypedQuery<Double> query = entityManager.createNamedQuery("Road.findDistance", Double.class)
                .setParameter("cityACode", cityCodeA).setParameter("cityBCode", cityCodeB);
        return query.getSingleResult();
    }

    public boolean findIsConnected(long cityCodeA, long cityCodeB) {
        long count = entityManager.createNamedQuery("Road.findIsConnected", Long.class)
                .setParameter("cityACode", cityCodeA).setParameter("cityBCode", cityCodeB).getSingleResult();
        return count != 0L;
    }

    public List<RoadDTO> findAllRoads() {
        TypedQuery<RoadDTO> query = entityManager.createNamedQuery("Road.findAllRoads", RoadDTO.class);
        return query.getResultList();
    }
}
