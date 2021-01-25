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


    public List<RoadDTO> findAllRoads() {
        TypedQuery<RoadDTO> query = entityManager.createNamedQuery("Road.findAllRoads", RoadDTO.class);
        return query.getResultList();
    }
}
