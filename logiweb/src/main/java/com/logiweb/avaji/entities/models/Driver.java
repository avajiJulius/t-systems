package com.logiweb.avaji.entities.models;

import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.entities.enums.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "drivers")
@NamedQuery(name = "Driver.findAllDrivers",
    query = "select new com.logiweb.avaji.crud.driver.dto.DriverDTO(d.id, d.firstName, d.lastName, " +
            "d.hoursWorked, d.driverStatus , d.currentTruck.truckId, d.currentCity.cityCode, d.currentCity.cityName) " +
            "from Driver d")
@NamedQuery(name = "Driver.findDriversForOrder",
        query = "select d from Driver d " +
                "where (176 - d.hoursWorked) > :shiftHours " +
                "and d.currentCity.cityCode = :cityCode " +
                "and d.currentTruck is null")
@NamedQuery(name = "Driver.refreshWorkedHours",
        query = "update Driver d set d.hoursWorked = 0")
@NamedQuery(name = "Driver.findDriversByTruckId",
        query = "select d from Driver d where d.currentTruck.truckId = :truckId")
@NamedQuery(name = "Driver.findDriversByTruckIdAndCount",
                query = "select count(d) from Driver d " +
                        "where d.currentTruck in (select t from Truck t where t.truckId = :truckId)")
@NamedQuery(name = "Driver.findDriversByIds",
        query = "select d from Driver d where d.id in :driversIds")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver extends User{

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "hours_worked")
    private double hoursWorked;
    @Column(name = "driver_status")
    @Enumerated(value = EnumType.STRING)
    private DriverStatus driverStatus;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "city_code")
    private City currentCity;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "truck_id")
    private Truck currentTruck;

}
