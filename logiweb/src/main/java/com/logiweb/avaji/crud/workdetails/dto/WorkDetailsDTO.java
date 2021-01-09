package com.logiweb.avaji.crud.workdetails.dto;

import com.logiweb.avaji.entities.enums.DriverStatus;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkDetailsDTO {
    private long id;
    private Collection<Driver> coDriversId = new ArrayList<>();
    private String truckId;
    private long orderId;
    private Collection<Waypoint> waypointList = new ArrayList<>();
    private boolean shiftActive;
    private DriverStatus driverStatus;
}
