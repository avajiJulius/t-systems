package com.logiweb.avaji.model;

public class DriverDetails {

    private int totalNumber;
    private int availableNumber;
    private int unavailableNumber;

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
}
