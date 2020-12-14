package com.logiweb.avaji.entities.models.utils;

import com.logiweb.avaji.entities.enums.WaypointType;
import com.logiweb.avaji.entities.models.Cargo;

public class Waypoint {
    private City pointCity;
    private Cargo pointCargo;
    private WaypointType waypointType;

    public Waypoint(City pointCity, Cargo pointCargo, WaypointType waypointType) {
        this.pointCity = pointCity;
        this.pointCargo = pointCargo;
        this.waypointType = waypointType;
    }

    public City getPointCity() {
        return pointCity;
    }

    public void setPointCity(City pointCity) {
        this.pointCity = pointCity;
    }

    public Cargo getPointCargo() {
        return pointCargo;
    }

    public void setPointCargo(Cargo pointCargo) {
        this.pointCargo = pointCargo;
    }

    public WaypointType getWaypointType() {
        return waypointType;
    }

    public void setWaypointType(WaypointType waypointType) {
        this.waypointType = waypointType;
    }
}
