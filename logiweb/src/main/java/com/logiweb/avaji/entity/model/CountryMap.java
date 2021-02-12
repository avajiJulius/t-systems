package com.logiweb.avaji.entity.model;

import com.logiweb.avaji.dtos.RoadDTO;
import com.logiweb.avaji.service.api.map.CountryMapService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CountryMap {

    private static final Logger logger = LogManager.getLogger(CountryMap.class);

    private Set<Long> cities = new HashSet<>();
    private List<RoadDTO> roads = new ArrayList<>();
    private final CountryMapService countryMapService;

    @Autowired
    public CountryMap(CountryMapService mapService) {
        this.countryMapService = mapService;
        this.cities.addAll(mapService.readAllCities().stream().map(c -> c.getCityCode()).collect(Collectors.toSet()));
        this.roads.addAll(mapService.readAllRoads());
        logger.info("Create country map");
    }

    public List<Long> findConnected(long cityCode) {
        return roads.stream().filter(r -> (r.getCityACode() == cityCode))
                .map(RoadDTO::getCityBCode).distinct().collect(Collectors.toList());
    }

    public double getDistanceBetween(long start, long goal) {
        return roads.stream().filter(r -> r.getCityACode() == start)
                .filter(r -> r.getCityBCode() == goal).findFirst().get().getDistance();
    }

    public String getCityNameByCode(long code) {
        return countryMapService.readCityNameByCode(code);
    }
}
