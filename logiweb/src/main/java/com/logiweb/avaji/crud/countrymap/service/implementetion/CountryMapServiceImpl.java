package com.logiweb.avaji.crud.countrymap.service.implementetion;

import com.logiweb.avaji.crud.countrymap.service.api.CountryMapService;
import com.logiweb.avaji.crud.countrymap.dao.CountryMapDAO;
import com.logiweb.avaji.crud.countrymap.dto.CityDTO;
import com.logiweb.avaji.entities.models.utils.Road;
import com.logiweb.avaji.orderdetails.dto.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CountryMapServiceImpl implements CountryMapService {

    private final CountryMapDAO countryMapDAO;

    @Autowired
    public CountryMapServiceImpl(CountryMapDAO countryMapDAO) {
        this.countryMapDAO = countryMapDAO;
    }


    @Override
    public List<CityDTO> readAllCities() {
        return countryMapDAO.findAllCities();
    }

    @Override
    public List<Road> readPathRoads(List<CityDTO> path) {
        List<Road> roads = new ArrayList<>();
        for (int i = 0; i < path.size() - 2; i++) {
            roads.add(countryMapDAO.findRoadByCities(path.get(i).getCityCode(), path.get(i+1).getCityCode()));
        }
        return roads;
    }

    @Override
    public List<Road> readAllRoads() {
        return countryMapDAO.findAllRoads();
    }

    @Override
    public List<Vertex> readAllVertex() {
        return countryMapDAO.findAllCitiesForVertex();
    }

    @Override
    public List<CityDTO> readCitiesByCodes(List<Long> codes) {
        return countryMapDAO.findCitiesByCodes(codes);
    }

    @Override
    public double readDistanceBetween(long cityCodeA, long cityCodeB) {
        return countryMapDAO.findDistance(cityCodeA, cityCodeB);
    }

    @Override
    public List<Long> findConnected(long cityCode) {
        return countryMapDAO.findConnectedCities(cityCode);
    }

}
