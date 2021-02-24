package com.logiweb.avaji.model;

import java.io.Serializable;

public class DriverInfo implements Serializable {

    private int totalNumber;
    private int availableNumber;
    private int unavailableNumber;

    public DriverInfo() {
    }

    public DriverInfo(int totalNumber, int availableNumber, int unavailableNumber) {
        this.totalNumber = totalNumber;
        this.availableNumber = availableNumber;
        this.unavailableNumber = unavailableNumber;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public int getAvailableNumber() {
        return availableNumber;
    }

    public void setAvailableNumber(int availableNumber) {
        this.availableNumber = availableNumber;
    }

    public int getUnavailableNumber() {
        return unavailableNumber;
    }

    public void setUnavailableNumber(int unavailableNumber) {
        this.unavailableNumber = unavailableNumber;
    }

    @Override
    public String toString() {
        return "DriverInfo{" +
                "totalNumber=" + totalNumber +
                ", availableNumber=" + availableNumber +
                ", unavailableNumber=" + unavailableNumber +
                '}';
    }
}
