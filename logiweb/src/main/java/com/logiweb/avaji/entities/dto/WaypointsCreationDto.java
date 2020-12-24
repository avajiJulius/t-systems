package com.logiweb.avaji.entities.dto;

import com.logiweb.avaji.entities.models.utils.Waypoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaypointsCreationDto {
    private List<WaypointDto> waypointsDto = new ArrayList<>();

    public void addWaypointDto(WaypointDto waypoint) {
        this.waypointsDto.add(waypoint);
    }
}
