package com.logiweb.avaji.entities.models;

import com.logiweb.avaji.entities.models.utils.City;

public class Truck {
    private String truckId;
    //уточнить у куратора про размер смены водителей
    private int driverSize;
    private String capacity;
    private boolean status;
    private City currentCity;

    public Truck() {
    }

    public Truck(String truckId, int driverSize, String capacity, boolean status, City currentCity) {
        this.truckId = truckId;
        this.driverSize = driverSize;
        this.capacity = capacity;
        this.status = status;
        this.currentCity = currentCity;
    }

    public String getTruckId() {
        return truckId;
    }

    public void setTruckId(String truckId) {
        this.truckId = truckId;
    }

    public int getDriverSize() {
        return driverSize;
    }

    public void setDriverSize(int driverSize) {
        this.driverSize = driverSize;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }
}
