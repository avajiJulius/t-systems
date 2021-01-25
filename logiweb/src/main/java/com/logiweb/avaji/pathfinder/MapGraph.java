package com.logiweb.avaji.pathfinder;

import com.logiweb.avaji.dtos.RoadDTO;
import com.logiweb.avaji.services.api.CountryMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

public class MapGraph {

    private Set<Long> cities = new HashSet<>();
    private List<RoadDTO> roads = new ArrayList<>();

    @Autowired
    public MapGraph(CountryMapService mapService) {
        this.cities.addAll(mapService.readAllCities().stream().map(c -> c.getCityCode()).collect(Collectors.toSet()));
        this.roads.addAll(mapService.readAllRoads());
    }

    public List<Long> findConnected(long cityCode) {
        return roads.stream().filter(r -> (r.getCityACode() == cityCode))
                .map(RoadDTO::getCityBCode).distinct().collect(Collectors.toList());
    }

    public double getDistanceBetween(long start, long goal) {
        return roads.stream().filter(r -> r.getCityACode() == start)
                .filter(r -> r.getCityBCode() == goal).findFirst().get().getDistance();
    }
}
