package com.logiweb.avaji.crud.countrymap.dao;

import com.logiweb.avaji.crud.countrymap.dto.CityDTO;
import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.entities.models.utils.Road;
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

    public Road findRoadByCities(long cityACode, long cityBCode) {
        TypedQuery<Road> query = entityManager.createNamedQuery("Road.findRoadByCities", Road.class)
                .setParameter("cityACode", cityACode).setParameter("cityBCode", cityBCode);
        return query.getSingleResult();
    }
}
