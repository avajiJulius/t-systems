package com.logiweb.avaji.entities.models.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class City{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_code")
    private Long cityCode;
    @Column(name = "city_name")
    private String cityName;
//    @OneToMany(mappedBy = "cities")
//    private List<Road> roads;


}
