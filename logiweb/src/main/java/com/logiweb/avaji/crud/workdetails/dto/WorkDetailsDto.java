package com.logiweb.avaji.crud.workdetails.dto;

import com.logiweb.avaji.entities.models.utils.Waypoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkDetailsDto {
    private long id;
    private List<Long> coDriversId = new ArrayList<>();
    private String truckId;
    private long orderId;
    private List<Waypoint> waypointList = new ArrayList<>();
    private boolean shiftActive;
    private String driverStatus;
    private boolean orderStatus;
}
