package com.logiweb.avaji.crud.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateWaypointsDTO {
    private List<WaypointDTO> waypointsDto = new ArrayList<>();

    public void addWaypointDto(WaypointDTO waypoint) {
        this.waypointsDto.add(waypoint);
    }
}
