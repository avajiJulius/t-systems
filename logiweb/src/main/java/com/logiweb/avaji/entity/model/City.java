package com.logiweb.avaji.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cities")
@NamedQuery(name = "City.findAllCities",
        query = "select distinct new com.logiweb.avaji.dtos.CityDTO(" +
                "c.cityCode, c.cityName) from City c")
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

    @Override
    public String toString() {
        return "City{" +
                "cityCode=" + cityCode +
                ", cityName='" + cityName + '\'' +
                '}';
    }

    public City(String cityName) {
        this.cityName = cityName;
    }
}
