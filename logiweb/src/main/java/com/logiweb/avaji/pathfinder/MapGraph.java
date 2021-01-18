package com.logiweb.avaji.pathfinder;

import com.logiweb.avaji.crud.countrymap.service.api.CountryMapService;
import com.logiweb.avaji.orderdetails.dto.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MapGraph {

    private Set<Vertex> vertices = new HashSet<>();
    private Set<Vertex> visited = new HashSet<>();

    private CountryMapService mapService;

    @Autowired
    public MapGraph(CountryMapService mapService) {
        this.mapService = mapService;
        this.vertices.addAll(mapService.readAllVertex());
    }

    public Vertex getVertex(long cityCode) {
        return vertices.stream().filter(v -> v.getCityCode() == cityCode).findFirst().get();
    }

    public Set<Vertex> findConnected(Vertex vertex) {
        List<Long> connected = mapService.findConnected(vertex.getCityCode());
        Set<Vertex> vertices = new HashSet<>();
        for (long code: connected) {
            vertices.add(this.getVertex(code));
        }
        return vertices;
    }

    public void refreshVertices() {
        vertices.addAll(visited);
    }

    public void setVisited(Vertex vertex) {
        vertices.remove(vertex);
        visited.add(vertex);
    }

}
