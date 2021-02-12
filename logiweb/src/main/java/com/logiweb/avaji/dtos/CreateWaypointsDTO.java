package com.logiweb.avaji.dtos;

import com.logiweb.avaji.validation.annotation.WaypointsValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateWaypointsDTO {
    @WaypointsValid
    private List<WaypointDTO> waypointsDto = new ArrayList<>();

    public void addWaypointDto(WaypointDTO waypoint) {
        this.waypointsDto.add(waypoint);
    }

    public void removeLast() {
        this.waypointsDto.remove(waypointsDto.size() - 1);
    }
}
