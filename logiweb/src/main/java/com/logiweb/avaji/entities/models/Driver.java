package com.logiweb.avaji.entities.models;

import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.entities.enums.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Locale;

@Entity
@Table(name = "drivers")
@NamedQueries({
    @NamedQuery(name = "Driver.findDrivers",
    query = "select d from Driver d"),
    @NamedQuery(name = "Driver.findDriverById",
    query = "select d from Driver d where d.driverId = :driverId"),
        @NamedQuery(name = "Driver.deleteDriver",
        query = "delete from Driver d where d.driverId = :driverId")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private String driverId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "worked_time_in_hours")
    private double workedTimeInHours;
    @Column(name = "driver_status")
    @Enumerated(value = EnumType.STRING)
    private DriverStatus driverStatus;
    @OneToOne
    @JoinColumn(name = "city_code")
    private City currentCity;
    @ManyToOne
    @JoinColumn(name = "truck_id")
    private Truck currentTruck;

}
