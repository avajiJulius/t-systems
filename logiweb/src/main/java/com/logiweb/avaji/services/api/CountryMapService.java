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
    List<Vertex> readAllVertex();

    double readDistanceBetween(long cityCodeA, long cityCodeB);

    List<Long> findConnected(long cityCode);

    boolean isConnected(long cityCodeA, long cityCodeB);
}