package com.logiweb.avaji.entities.models;

import com.logiweb.avaji.entities.models.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roads")
@NamedQuery(name = "Road.findRoadByCities",
query = "select r from Road r " +
        "where r.cityA.cityCode = :cityACode " +
        "and r.cityB.cityCode = :cityBCode")
@NamedQuery(name = "Road.findAllRoads",
        query = "select r from Road r")
@NamedQuery(name = "Road.findDistance",
query = "select r.distanceInHours from Road r " +
        "where r.cityA.cityCode = :cityACode " +
        "and r.cityB.cityCode = :cityBCode")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Road {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "road_id")
    private long roadId;
    @ManyToOne
    @JoinColumn(name = "city_a_code")
    private City cityA;
    @ManyToOne
    @JoinColumn(name = "city_b_code")
    private City cityB;
    @Column(name = "distance_in_hours")
    private double distanceInHours;

    @ManyToMany(mappedBy = "roads")
    private List<City> cities = new ArrayList<>();
}
