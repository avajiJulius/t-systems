package com.logiweb.avaji.services.api;

import com.logiweb.avaji.dtos.CityDTO;
import com.logiweb.avaji.entities.models.Road;
import com.logiweb.avaji.dtos.Vertex;

import java.util.List;

/**
 *  The <code>CountryMapService</code> manipulating with City and Road entities
 *  for add City and Road entities into database and read from database finding Road or list of Roads by the Cities.
 */
public interface CountryMapService {

    List<CityDTO> readAllCities();
    List<Road> readPathRoads(List<CityDTO> path);
    List<Road> readAllRoads();
    List<Vertex> readAllVertex();
    List<CityDTO> readCitiesByCodes(List<Long> codes);

    double readDistanceBetween(long cityCodeA, long cityCodeB);

    List<Long> findConnected(long cityCode);
}
