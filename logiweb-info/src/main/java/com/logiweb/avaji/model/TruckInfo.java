package com.logiweb.avaji.model;


public class TruckInfo implements Details {
    private int totalNumber;
    private int availableNumber;
    private int inUseNumber;
    private int faultyNumber;

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

    public int getInUseNumber() {
        return inUseNumber;
    }

    public void setInUseNumber(int inUseNumber) {
        this.inUseNumber = inUseNumber;
    }

    public int getFaultyNumber() {
        return faultyNumber;
    }

    public void setFaultyNumber(int faultyNumber) {
        this.faultyNumber = faultyNumber;
    }
}
