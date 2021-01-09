package com.logiweb.avaji.crud.countrymap.service.implementetion;

import com.logiweb.avaji.crud.countrymap.service.api.CountryMapService;
import com.logiweb.avaji.crud.countrymap.dao.CountryMapDAO;
import com.logiweb.avaji.crud.countrymap.dto.CityDto;
import com.logiweb.avaji.mapper.Mapper;
import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.entities.models.utils.Road;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CountryMapServiceImpl implements CountryMapService {

    private final CountryMapDAO countryMapDAO;
    private final Mapper converter;

    @Autowired
    public CountryMapServiceImpl(CountryMapDAO countryMapDAO, Mapper mapper) {
        this.converter = mapper;
        this.countryMapDAO = countryMapDAO;
    }


    @Override
    public List<CityDto> readAllCities() {
        return converter.citiesToDtos(countryMapDAO.findAllCities());
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
