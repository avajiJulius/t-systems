package com.logiweb.avaji.services.api.map;

import com.logiweb.avaji.dtos.CityDTO;
import com.logiweb.avaji.dtos.RoadDTO;

import java.util.List;

/**
 *  The <code>CountryMapService</code> manipulating with City and Road entities
 *  for add City and Road entities into database and read from database finding Road or list of Roads by the Cities.
 */
public interface CountryMapService {

    /**
     * Read all cities dtos from database
     *
     * @return all cities
     */
    List<CityDTO> readAllCities();

    /**
     * Read all roads dtos from database
     *
     * @return all roads
     */
    List<RoadDTO> readAllRoads();
}
