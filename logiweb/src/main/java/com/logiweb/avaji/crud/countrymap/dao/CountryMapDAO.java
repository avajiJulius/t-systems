package com.logiweb.avaji.crud.countrymap.dao;

import com.logiweb.avaji.crud.countrymap.dto.CityDTO;
import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.entities.models.utils.Road;
import com.logiweb.avaji.orderdetails.dto.Vertex;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class CountryMapDAO {

    @PersistenceContext
    private EntityManager entityManager;


    public List<Vertex> findAllCitiesForVertex() {
        TypedQuery<Vertex> query = entityManager.createNamedQuery("City.findAllCitiesForVertex", Vertex.class);
        return query.getResultList();
    }


    public List<CityDTO> findAllCities() {
        TypedQuery<CityDTO> query = entityManager.createNamedQuery("City.findAllCities", CityDTO.class);
        return query.getResultList();
    }


    public City findCityByCode(long cityCode) {
        return entityManager.find(City.class, cityCode);
    }

    public Road findRoadByCities(long cityACode, long cityBCode) {
        TypedQuery<Road> query = entityManager.createNamedQuery("Road.findRoadByCities", Road.class)
                .setParameter("cityACode", cityACode).setParameter("cityBCode", cityBCode);
        return query.getSingleResult();
    }

    public List<Road> findAllRoads() {
        TypedQuery<Road> query = entityManager.createNamedQuery("Road.findAllRoads", Road.class);
        return query.getResultList();
    }

    public List<Long> findConnectedCities(long cityCode) {
        Query query = entityManager.createNamedQuery("City.findConnectedCities")
                .setParameter("cityCode", cityCode);
        return query.getResultList();
    }

    public List<CityDTO> findCitiesByCodes(List<Long> codes) {
        TypedQuery<CityDTO> query = entityManager.createNamedQuery("City.findCitiesByCodes", CityDTO.class)
                .setParameter("codes", codes);
        return query.getResultList();
    }

    public double findDistance(long cityCodeA, long cityCodeB) {
        TypedQuery<Double> query = entityManager.createNamedQuery("Road.findDistance", Double.class)
                .setParameter("cityACode", cityCodeA).setParameter("cityBCode", cityCodeB);
        return query.getSingleResult();
    }
}
