package com.logiweb.avaji.entities.models.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "roads")
@NamedQueries(value = {
        @NamedQuery(name = "Road.findRoadByCities",
        query = "select r from Road r " +
                "where r.cityA.cityCode = :cityACode " +
                "and r.cityB.cityCode = :cityBCode")
})
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Road {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "road_id")
    private Integer roadId;
    @ManyToOne
    @JoinColumn(name = "city_a_code")
    private City cityA;
    @ManyToOne
    @JoinColumn(name = "city_b_code")
    private City cityB;
    @Column(name = "distance_in_hours")
    private double distanceInHours;

}
