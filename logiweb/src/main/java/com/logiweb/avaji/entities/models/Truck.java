package com.logiweb.avaji.entities.models;


import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.utils.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trucks")
@NamedQuery(name = "Truck.findAllTrucks",
        query = "select new com.logiweb.avaji.crud.truck.dto.TruckDTO(" +
                "t.truckId, t.capacity, t.shiftSize, t.serviceable, " +
                "c.cityCode, c.cityName) from Truck t " +
                "join t.currentCity c")
@NamedQuery(name = "Truck.findTruckById",
        query = "select new com.logiweb.avaji.crud.truck.dto.TruckDTO(" +
                "t.truckId, t.capacity, t.shiftSize, t.serviceable, " +
                "t.currentCity.cityCode, t.currentCity.cityName) from Truck t " +
                "where t.truckId = :id")
@NamedQuery(name = "Truck.findTrucksForOrder",
        query = "select new com.logiweb.avaji.crud.truck.dto.TruckDTO(" +
                "t.truckId, t.capacity, t.shiftSize, t.serviceable, " +
                "t.currentCity.cityCode, t.currentCity.cityName) from Truck t " +
                "where t.serviceable = true " +
                "and t.capacity >= :maxCapacity " +
                "and t not in (select o.designatedTruck from Order o)")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Truck {
    @Id
    @Column(name = "truck_id")
    private String truckId;
    @Column(name= "shift_size")
    private int shiftSize;
    @Column(name = "capacity")
    private double capacity;
    @Column(name = "serviceable")
    private boolean serviceable;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "city_code")
    private City currentCity;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY,
            mappedBy = "designatedTruck")
    private Order currentOrder;
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY,
            mappedBy = "currentTruck")
    private List<Driver> drivers = new ArrayList<>();

}
