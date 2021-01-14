package com.logiweb.avaji.crud.countrymap.service.api;

import com.logiweb.avaji.crud.countrymap.dto.CityDTO;
import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.entities.models.utils.Road;

import java.util.List;

/**
 *  The <code>CountryMapService</code> manipulating with City and Road entities
 *  for add City and Road entities into database and read from database finding Road or list of Roads by the Cities.
 */
public interface CountryMapService {

    List<CityDTO> readAllCities();
    List<Road> readPathRoads(List<City> path);

}
