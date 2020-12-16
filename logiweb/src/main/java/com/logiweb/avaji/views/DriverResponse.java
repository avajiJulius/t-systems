package com.logiweb.avaji.views;

import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.utils.Waypoint;

import java.util.Set;

public class DriverResponse {
    private String driverId;
    private Set<String> coDriversIds;
    private Long truckId;
    private Long orderId;
    private Set<Waypoint> waypoints;

    public DriverResponse() {
    }

    public DriverResponse(String driverId, Set<String> coDriversIds,
                          Long truckId, Long orderId, Set<Waypoint> waypoints) {
        this.driverId = driverId;
        this.coDriversIds = coDriversIds;
        this.truckId = truckId;
        this.orderId = orderId;
        this.waypoints = waypoints;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public Set<String> getCoDriversIds() {
        return coDriversIds;
    }

    public void setCoDriversIds(Set<String> coDriversIds) {
        this.coDriversIds = coDriversIds;
    }

    public Long getTruckId() {
        return truckId;
    }

    public void setTruckId(Long truckId) {
        this.truckId = truckId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Set<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(Set<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }
}
