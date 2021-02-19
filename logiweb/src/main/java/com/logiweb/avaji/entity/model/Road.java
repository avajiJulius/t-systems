package com.logiweb.avaji.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "roads")
@NamedQuery(name = "Road.findRoadByCities",
query = "select r from Road r " +
        "where r.cityA.cityCode = :cityACode " +
        "and r.cityB.cityCode = :cityBCode")
@NamedQuery(name = "Road.findAllRoads",
        query = "select new com.logiweb.avaji.dtos.RoadDTO(" +
                "r.cityA.cityCode, r.cityB.cityCode, r.distanceInHours) from Road r")
@NamedQuery(name = "Road.findDistance",
query = "select r.distanceInHours from Road r " +
        "where r.cityA.cityCode = :cityACode " +
        "and r.cityB.cityCode = :cityBCode")
@NamedQuery(name = "Road.findIsConnected",
query = "select count(r) from Road r " +
        "where r.cityA.cityCode = :cityACode " +
        "and r.cityB.cityCode = :cityBCode")
@Data
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

}
