package com.logiweb.avaji.entities.models;

import com.logiweb.avaji.entities.enums.CargoStatus;

public class Cargo {
    private Long cargoID;
    private String cargoTitle;
    private double cargoWeight;
    private CargoStatus cargoStatus;

    public Cargo() {
    }

    public Long getCargoID() {
        return cargoID;
    }

    public void setCargoID(Long cargoID) {
        this.cargoID = cargoID;
    }

    public String getCargoTitle() {
        return cargoTitle;
    }

    public void setCargoTitle(String cargoTitle) {
        this.cargoTitle = cargoTitle;
    }

    public double getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public CargoStatus getCargoStatus() {
        return cargoStatus;
    }

    public void setCargoStatus(CargoStatus cargoStatus) {
        this.cargoStatus = cargoStatus;
    }
}
