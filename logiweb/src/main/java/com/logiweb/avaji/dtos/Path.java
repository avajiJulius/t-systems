package com.logiweb.avaji.dtos;

import lombok.Data;

import java.util.List;

@Data
public class Path {

    private List<Vertex> path;
    private double distance;

    public Path(List<Vertex> path, double distance) {
        this.path = path;
        this.distance = distance;
    }

    public Path() {
    }

    public boolean isEmpty() {
        return path.isEmpty();
    }


    public Vertex getLast() {
        return path.get(path.size() - 1);
    }

    public void addVertex(Vertex vertex) {
        this.path.add(vertex);
    }

    public void removeLast() {
        path.remove(path.size() - 1);
    }
}
