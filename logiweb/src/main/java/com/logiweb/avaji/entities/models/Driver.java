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
    @NamedQuery(name = "Driver.findAllDrivers",
    query = "select d from Driver d"),
        @NamedQuery(name = "Driver.findDriversForOrder",
        query = "select d from Driver d " +
                "where (176 - d.hoursWorked) > :shiftHours " +
                "and d.currentCity.cityCode = :cityCode " +
                "and d.driverStatus like 'REST' "),
        @NamedQuery(name = "Driver.refreshWorkedHours",
        query = "update Driver d set d.hoursWorked = 0")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private Integer driverId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "hours_worked")
    private double hoursWorked;
    @Column(name = "driver_status")
    @Enumerated(value = EnumType.STRING)
    private DriverStatus driverStatus;
    @ManyToOne
    @JoinColumn(name = "city_code")
    private City currentCity;
    @ManyToOne
    @JoinColumn(name = "truck_id")
    private Truck currentTruck;

}
