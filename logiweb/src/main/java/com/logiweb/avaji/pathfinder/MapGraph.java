package com.logiweb.avaji.pathfinder;

import com.logiweb.avaji.services.api.CountryMapService;
import com.logiweb.avaji.dtos.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
        Optional<Vertex> vertex = vertices.stream().filter(v -> v.getCityCode() == cityCode).findFirst();
        if(vertex.isPresent()) {
            return vertex.get();
        }
        return null;
    }

    public Set<Vertex> findConnected(Vertex vertex) {
        List<Long> connected = mapService.findConnected(vertex.getCityCode());
        Set<Vertex> vertices = new HashSet<>();
        for (long code: connected) {
            if(this.getVertex(code) != null) {
                vertices.add(this.getVertex(code));
            }
        }
        return vertices;
    }

    public void refreshVertices() {
        vertices.addAll(visited);
        visited.clear();
    }

    public void setVisited(Vertex vertex) {
        vertices.remove(vertex);
        visited.add(vertex);
    }

}
