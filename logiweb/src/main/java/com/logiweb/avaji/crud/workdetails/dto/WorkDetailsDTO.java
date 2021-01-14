package com.logiweb.avaji.crud.workdetails.dto;

import com.logiweb.avaji.entities.enums.DriverStatus;
import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.collection.internal.PersistentBag;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkDetailsDTO {
    private long id;
    private String firstName;
    private String lastName;
    private long cityCode;
    private String cityName;
    private long nextCityCode;
    private String nextCityName;
    private double hoursWorked;
    private String truckId;
    private long orderId;
    private boolean orderCompleted;
    private boolean shiftActive;
    private DriverStatus driverStatus;
    private List<Long> driversIds = new ArrayList<>();
    private List<Waypoint> waypointsList = new ArrayList<>();

    public WorkDetailsDTO(long id, String firstName, String lastName,
                          long cityCode, String cityName,
                          double hoursWorked, String truckId, long orderId,
                          boolean shiftActive, DriverStatus driverStatus) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cityCode = cityCode;
        this.cityName = cityName;
        this.hoursWorked = hoursWorked;
        this.truckId = truckId;
        this.orderId = orderId;
        this.shiftActive = shiftActive;
        this.driverStatus = driverStatus;
    }
}
