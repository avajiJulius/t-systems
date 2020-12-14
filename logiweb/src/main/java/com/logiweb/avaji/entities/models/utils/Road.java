package com.logiweb.avaji.entities.models.utils;


public class Road {
    private final City from;
    private final City to;
    private final double distance;

    public Road(City from, City to, int distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public City getFrom() {
        return from;
    }

    public City getTo() {
        return to;
    }

    public double getDistance() {
        return distance;
    }
}
