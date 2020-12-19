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
    private List<Waypoint> waypoints = new ArrayList<>();

    public void addWaypoint(Waypoint waypoint) {
        this.waypoints.add(waypoint);
    }
}
