package com.logiweb.avaji.crud.countrymap.service.implementetion;

import com.logiweb.avaji.crud.countrymap.service.api.CountryMapService;
import com.logiweb.avaji.crud.countrymap.dao.CountryMapDAO;
import com.logiweb.avaji.crud.countrymap.dto.CityDTO;
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

    @Autowired
    public CountryMapServiceImpl(CountryMapDAO countryMapDAO) {
        this.countryMapDAO = countryMapDAO;
    }


    @Override
    public List<CityDTO> readAllCities() {
        return countryMapDAO.findAllCities();
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
