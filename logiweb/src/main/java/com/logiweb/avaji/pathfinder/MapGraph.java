package com.logiweb.avaji.pathfinder;

import com.logiweb.avaji.dtos.RoadDTO;
import com.logiweb.avaji.services.api.CountryMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MapGraph {

    private Set<Long> cities = new HashSet<>();
    private Deque<Long> visited = new ArrayDeque<>();
    private List<RoadDTO> roads = new ArrayList<>();

    private CountryMapService mapService;

    @Autowired
    public MapGraph(CountryMapService mapService) {
        this.mapService = mapService;
        this.cities.addAll(mapService.readAllCities().stream().map(c -> c.getCityCode()).collect(Collectors.toSet()));
        this.roads.addAll(mapService.readAllRoads());
    }

    public long getCity(long cityCode) {
       return cities.stream().filter(c -> c == cityCode).findFirst().get();
    }

    public List<Long> findConnected(long cityCode) {
        return roads.stream().filter(r -> (r.getCityACode() == cityCode))
                .map(RoadDTO::getCityBCode).distinct().collect(Collectors.toList());
    }

    public boolean isConnected(long cityA, long cityB) {
        return roads.stream().anyMatch(r -> r.getCityACode() == cityA && r.getCityBCode() == cityB);
    }

    public void refreshVertices() {
        cities.addAll(visited);
        visited.clear();
    }

    public void setVisited(long cityCode) {
        cities.remove(cityCode);
        visited.addFirst(cityCode);
        if(visited.size() > 2) {
            cities.add(visited.pollLast());
        }
    }

    public double getDistanceBetween(long start, long goal) {
        return roads.stream().filter(r -> r.getCityACode() == start)
                .filter(r -> r.getCityBCode() == goal).findFirst().get().getDistance();
    }
}
