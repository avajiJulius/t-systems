package com.logiweb.avaji.entities.models.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cities")
@NamedQueries(value = {
        @NamedQuery(name = "City.findAllCities",
        query = "select c from City c")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class City{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_code")
    private Integer cityCode;
    @Column(name = "city_name")
    private String cityName;

}
