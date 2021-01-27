package com.logiweb.avaji.dtos;

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

    public static class Builder {

        private OrderDetailsDTO newOrderDetails;

        public Builder (OrderDetailsDTO orderDetails) {
            this.newOrderDetails = orderDetails;
        }

        public Builder withCoDrivers(List<DriverDTO> coDrivers) {
            newOrderDetails.coDrivers = coDrivers;
            return this;
        }

        public Builder withRemainingPath(Deque<CityDTO> remainingPath) {
            newOrderDetails.remainingPath = remainingPath;
            return this;
        }

        public Builder withPrettyPath(String prettyPath) {
            newOrderDetails.prettyPath = prettyPath;
            return this;
        }

        public Builder withWaypoints(List<WaypointDTO> waypoints) {
            newOrderDetails.waypoints = waypoints;
            return this;
        }

        public Builder withCompleted(boolean completed) {
            newOrderDetails.completed = completed;
            return this;
        }
    }
}
