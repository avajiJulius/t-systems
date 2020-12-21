package com.logiweb.avaji.entities.models;


import com.logiweb.avaji.entities.models.utils.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "trucks")
@NamedQueries({
        @NamedQuery(name = "Truck.findTrucks",
                query = "select t from Truck t"),
        @NamedQuery(name = "Truck.findTruckById",
                query = "select t from Truck t where t.truckId = :truckId"),
        @NamedQuery(name = "Truck.deleteTruck",
                query = "delete from Truck t where t.truckId = :truckId"),
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "truck_id")
    private String truckId;
    @Column(name= "work_shift_size")
    private double workShiftSize;
    @Column(name = "capacity")
    private double capacity;
    @Column(name = "serviceable")
    private boolean serviceable;
    @OneToOne
    @JoinColumn(name = "city_code")
    private City currentCity;

    @OneToMany(mappedBy = "currentTruck")
    private List<Driver> drivers;


}
