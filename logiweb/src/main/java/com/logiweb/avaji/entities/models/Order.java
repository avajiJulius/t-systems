package com.logiweb.avaji.entities.models;

import java.util.List;

public class Order {
    private Long orderID;
    private boolean completed;
    private Truck designatedTruck;
    //понять list or set
    private List<Driver> designatedDrivers;

    public Order() {
    }

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Truck getDesignatedTruck() {
        return designatedTruck;
    }

    public void setDesignatedTruck(Truck designatedTruck) {
        this.designatedTruck = designatedTruck;
    }

    public List<Driver> getDesignatedDrivers() {
        return designatedDrivers;
    }

    public void setDesignatedDrivers(List<Driver> designatedDrivers) {
        this.designatedDrivers = designatedDrivers;
    }
}
