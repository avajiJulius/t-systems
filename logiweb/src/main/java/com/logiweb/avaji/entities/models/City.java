package com.logiweb.avaji.entities.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "cities")
@NamedQuery(name = "City.findAllCities",
    query = "select distinct new com.logiweb.avaji.dtos.CityDTO(" +
        "c.cityCode, c.cityName) from City c")
@NamedQuery(name = "City.findAllCitiesForVertex",
        query = "select new com.logiweb.avaji.dtos.Vertex(c.cityCode) " +
                "from City c")
@NamedQuery(name = "City.findConnectedCities",
        query = "select case when r.cityA.cityCode = :cityCode then r.cityB.cityCode " +
                "when r.cityB.cityCode = :cityCode then r.cityA.cityCode " +
                "else -1 end as cityCode from Road r where r.cityA.cityCode = :cityCode " +
                "or r.cityB.cityCode = :cityCode")
@NamedQuery(name = "City.findCitiesByCodes",
        query = "select new com.logiweb.avaji.dtos.CityDTO(" +
                "c.cityCode, c.cityName) from City c where c.cityCode in (:codes)")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class City{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_code")
    private long cityCode;
    @Column(name = "city_name")
    private String cityName;
    @ManyToMany
    @JoinTable(name = "country_map",
            joinColumns = @JoinColumn(name = "city_code"),
            inverseJoinColumns = @JoinColumn(name = "road_id"))
    private Collection<Road> roads;

    @Override
    public String toString() {
        return "City{" +
                "cityCode=" + cityCode +
                ", cityName='" + cityName + '\'' +
                '}';
    }
}
