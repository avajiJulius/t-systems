package com.logiweb.avaji.model;

import java.util.List;

public class Order {

    private long orderId;
    private String status;
    private String truckId;
    private String path;
    private List<Driver> drivers;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status.toLowerCase();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTruckId() {
        return truckId;
    }

    public void setTruckId(String truckId) {
        this.truckId = truckId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }
}
