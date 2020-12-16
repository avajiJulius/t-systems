package com.logiweb.avaji.services;

import com.logiweb.avaji.entities.models.utils.City;

public interface CountryMapService {

    void readAllCity();
    City readCityByName(String name);
}
