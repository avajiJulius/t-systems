package com.logiweb.avaji.crud.workdetails.dto;

import com.logiweb.avaji.entities.enums.DriverStatus;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkDetailsDTO {
    private long id;
    private String truckId;
    private long orderId;
    private boolean shiftActive;
    private DriverStatus driverStatus;
    private List<Long> driversIds = new ArrayList<>();
    private List<Waypoint> waypointsList = new ArrayList<>();

    public WorkDetailsDTO(long id, String truckId, long orderId,
                          boolean shiftActive, DriverStatus driverStatus) {
        this.id = id;
        this.truckId = truckId;
        this.orderId = orderId;
        this.shiftActive = shiftActive;
        this.driverStatus = driverStatus;
    }
}
