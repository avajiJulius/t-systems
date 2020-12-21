package com.logiweb.avaji.services.implementetions;

import com.logiweb.avaji.dao.CountryMapDAO;
import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.entities.models.utils.Road;
import com.logiweb.avaji.services.CountryMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryMapServiceImpl implements CountryMapService {

    private final CountryMapDAO countryMapDAO;

    @Autowired
    public CountryMapServiceImpl(CountryMapDAO countryMapDAO) {
        this.countryMapDAO = countryMapDAO;
    }


    @Override
    public List<City> readAllCities() {
        return countryMapDAO.findAllCities();
    }

    @Override
    public void createCity(City city) {

    }

    @Override
    public void createCities(List<City> cities) {

    }

    @Override
    public void createRoad(City cityA, City cityB, double distance) {

    }

    @Override
    public void createRoad(Road road) {

    }

    @Override
    public void createRoads(List<Road> roads) {

    }

    @Override
    public List<Road> readRoads(City city) {
        return null;
    }

    @Override
    public Road readRoad(City cityA, City cityB) {
        return null;
    }
}
