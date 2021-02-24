package com.logiweb.avaji.dtos;

import com.logiweb.avaji.entity.model.Cargo;
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
    private String prettyPath;
    private String remainingPathString;
    private Deque<CityDTO> remainingPath = new ArrayDeque<>();
    private double maxCapacity;
    private double remainingWorkingHours;
    private CityDTO nextCity;
    private List<WaypointDTO> waypoints = new ArrayList<>();
    private boolean completed;
    private List<Cargo> loadCargo = new ArrayList<>();
    private List<Cargo> unloadCargo = new ArrayList<>();


    public OrderDetailsDTO(long id, String truckId,
                           String path, String remainingPathString,
                           double maxCapacity, double remainingWorkingHours) {
        this.id = id;
        this.truckId = truckId;
        this.path = path;
        this.remainingPathString = remainingPathString;
        this.maxCapacity = maxCapacity;
        this.remainingWorkingHours = remainingWorkingHours;
    }
}
