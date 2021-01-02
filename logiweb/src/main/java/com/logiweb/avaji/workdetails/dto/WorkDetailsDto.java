package com.logiweb.avaji.workdetails.dto;

import com.logiweb.avaji.entities.models.utils.Waypoint;
import com.logiweb.avaji.entities.models.utils.WorkShift;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkDetailsDto {
    private long driverId;
    private List<Long> coDriversId = new ArrayList<>();
    private String truckId;
    private long orderId;
    private List<Waypoint> waypointList = new ArrayList<>();
    private boolean shiftActive;
    private String driverStatus;
    private boolean orderStatus;
}
