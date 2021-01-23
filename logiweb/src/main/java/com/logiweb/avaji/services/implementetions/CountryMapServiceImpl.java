package com.logiweb.avaji.services.implementetions;

import com.logiweb.avaji.dtos.RoadDTO;
import com.logiweb.avaji.services.api.CountryMapService;
import com.logiweb.avaji.daos.CountryMapDAO;
import com.logiweb.avaji.dtos.CityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public double readDistanceBetween(long cityCodeA, long cityCodeB) {
        return countryMapDAO.findDistance(cityCodeA, cityCodeB);
    }

    @Override
    public List<Long> findConnected(long cityCode) {
        return countryMapDAO.findConnectedCities(cityCode);
    }

    @Override
    public boolean isConnected(long cityCodeA, long cityCodeB) {
        return countryMapDAO.findIsConnected(cityCodeA, cityCodeB);
    }

    @Override
    public List<RoadDTO> readAllRoads() {
        return countryMapDAO.findAllRoads();
    }

}
