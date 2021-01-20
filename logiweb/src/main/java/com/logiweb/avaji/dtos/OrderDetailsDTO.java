package com.logiweb.avaji.dtos;


import com.logiweb.avaji.entities.models.Waypoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDTO {
    private long id;
    private String truckId;
    private List<DriverDTO> coDrivers = new ArrayList<>();
    private String path;
    private String remainingPathString;
    private Deque<CityDTO> remainingPath = new ArrayDeque<>();
    private List<WaypointDTO> waypoints = new ArrayList<>();
    private boolean completed;


    public OrderDetailsDTO(long id, String truckId,
                           String path, String remainingPathString) {
        this.id = id;
        this.truckId = truckId;
        this.path = path;
        this.remainingPathString = remainingPathString;
    }
}
