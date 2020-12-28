package com.logiweb.avaji.services.implementetions;

import com.logiweb.avaji.dao.CountryMapDAO;
import com.logiweb.avaji.entities.dto.CityDto;
import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.entities.models.utils.Road;
import com.logiweb.avaji.services.api.CountryMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CountryMapServiceImpl implements CountryMapService {

    private final CountryMapDAO countryMapDAO;
    private final DtoConverter converter;

    @Autowired
    public CountryMapServiceImpl(CountryMapDAO countryMapDAO, DtoConverter dtoConverter) {
        this.converter = dtoConverter;
        this.countryMapDAO = countryMapDAO;
    }


    @Override
    public List<CityDto> readAllCities() {
        List<CityDto> dtos = converter.citiesToDtos(countryMapDAO.findAllCities());

        return dtos;
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

    @Override
    public List<Road> readPathRoads(List<City> path) {
        List<Road> roads = new ArrayList<>();
        for (int i = 0; i < path.size() - 2; i++) {
            roads.add(countryMapDAO.findRoadByCities(path.get(i).getCityCode(), path.get(i+1).getCityCode()));
        }
        return roads;
    }
}
