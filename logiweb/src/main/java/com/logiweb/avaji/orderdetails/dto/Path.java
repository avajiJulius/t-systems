package com.logiweb.avaji.orderdetails.dto;

import com.logiweb.avaji.crud.countrymap.service.api.CountryMapService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Path {

    @Autowired
    private CountryMapService countryMapService;

    private List<Vertex> path = new ArrayList<>();
    private double distance;

    public Path(Vertex first, Vertex second, double distance) {
        this.path.add(first);
        this.path.add(second);
        this.distance = distance;
    }

    public Path(double distance) {
        this.distance = distance;
    }

    public Path(Path path, Vertex next, double distance) {
        path.getPath().add(next);
        this.path = path.getPath();
        this.distance = distance;
    }

    public boolean isEmpty() {
        return path.isEmpty();
    }

    public Path createNew(Vertex next, double distance) {
        path.add(next);
        return new Path(this, next, distance);
    }

    public Vertex getLast() {
        return path.get(path.size() - 1);
    }

}
