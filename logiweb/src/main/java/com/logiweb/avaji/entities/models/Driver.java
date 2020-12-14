package com.logiweb.avaji.entities.models;

import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.entities.enums.DriverStatus;

public class Driver {

    private Long driverID;
    private String firstName;
    private String lastName;
    private int workedTimeInHours;
    private DriverStatus driverStatus;
    private City currentCity;
    private Truck currentTruck;

    public Driver() {
    }

    public Driver(Long driverID, String firstName, String lastName,
                  int workedTimeInHours, DriverStatus driverStatus, City currentCity, Truck currentTruck) {
        this.driverID = driverID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.workedTimeInHours = workedTimeInHours;
        this.driverStatus = driverStatus;
        this.currentCity = currentCity;
        this.currentTruck = currentTruck;
    }

    public Long getDriverID() {
        return driverID;
    }

    public void setDriverID(Long driverID) {
        this.driverID = driverID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getWorkedTimeInHours() {
        return workedTimeInHours;
    }

    public void setWorkedTimeInHours(int workedTimeInHours) {
        this.workedTimeInHours = workedTimeInHours;
    }

    public DriverStatus getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(DriverStatus driverStatus) {
        this.driverStatus = driverStatus;
    }

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }

    public Truck getCurrentTruck() {
        return currentTruck;
    }

    public void setCurrentTruck(Truck currentTruck) {
        this.currentTruck = currentTruck;
    }
}
