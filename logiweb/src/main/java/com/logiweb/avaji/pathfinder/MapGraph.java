package com.logiweb.avaji.pathfinder;

import com.logiweb.avaji.services.api.CountryMapService;
import com.logiweb.avaji.dtos.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MapGraph {

    private Set<Vertex> vertices = new HashSet<>();
    private Deque<Vertex> visited = new ArrayDeque<>();

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

    public boolean isConnected(long vertexA, long vertexB) {
        return mapService.isConnected(vertexA,  vertexB);
    }

    public void refreshVertices() {
        vertices.addAll(visited);
        visited.clear();
    }

    public void setVisited(Vertex vertex) {
        vertices.remove(vertex);
        visited.addFirst(vertex);
        if(visited.size() > 2) {
            vertices.add(visited.pollLast());
        }
    }

}
