package com.logiweb.avaji.model;


import java.io.Serializable;

public class TruckInfo implements Serializable {
    private int totalNumber;
    private int availableNumber;
    private int inUseNumber;
    private int faultyNumber;

    public TruckInfo() {
    }

    public TruckInfo(int totalNumber, int availableNumber, int inUseNumber, int faultyNumber) {
        this.totalNumber = totalNumber;
        this.availableNumber = availableNumber;
        this.inUseNumber = inUseNumber;
        this.faultyNumber = faultyNumber;
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

    @Override
    public String toString() {
        return "TruckInfo{" +
                "totalNumber=" + totalNumber +
                ", availableNumber=" + availableNumber +
                ", inUseNumber=" + inUseNumber +
                ", faultyNumber=" + faultyNumber +
                '}';
    }
}
