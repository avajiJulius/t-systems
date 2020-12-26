package com.logiweb.avaji.services;

import com.logiweb.avaji.entities.dto.CityDto;
import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.entities.models.utils.Road;

import java.util.List;

/**
 *  The <code>CountryMapService</code> manipulating with City and Road entities
 *  for add City and Road entities into database and read from database finding Road or list of Roads by the Cities.
 */
public interface CountryMapService {


    List<CityDto> readAllCities();

    /**
     * The <code>createCity</code> create city entity. This entity goes to CountyMapDAO.
     *
     * @param city
     */
    void createCity(City city);

    /**
     * The <code>createCities</code> create list of city entities. Those entities go to CountyMapDAO.
     *
     * @param cities
     */
    void createCities(List<City> cities);

    /**
     * The <code>createRoad</code> create road entity. This entity goes to CountyMapDAO.
     *
     * @param cityA read cityA entity from the database. If it is not exists, create it.
     * @param cityB read cityA entity from the database. If it is not exists, create it.
     * @param distance between the cityA and cityB
     */
    void createRoad(City cityA, City cityB, double distance);
    void createRoad(Road road);

    /**
     * The <code>createRoads</code> create list of road entities. Those entities go to CountyMapDAO.
     *
     * @param roads if cityA or cityB in road entity from this list is not exist, create it.
     */
    void createRoads(List<Road> roads);

    /**
     * The <code>readRoads</code> read roads from database which connected to this city.
     *
     * @param city
     * @return list of road entities which connected to this city.
     */
    List<Road> readRoads(City city);

    /**
     * The <code>readRoad</code> read road from database which connected cityA and cityB.
     *
     * @param cityA
     * @param cityB
     * @return road which connected cityA and cityB
     */
    Road readRoad(City cityA, City cityB);

    List<Road> readPathRoads(List<City> path);

}
