package com.logiweb.avaji.model;

import java.io.Serializable;

public class Information implements Serializable {

    private OrderInfo orderInfo;
    private TruckInfo truckInfo;
    private DriverInfo driverInfo;

    public Information() {
    }

    public Information(OrderInfo orderInfo, TruckInfo truckInfo, DriverInfo driverInfo) {
        this.orderInfo = orderInfo;
        this.truckInfo = truckInfo;
        this.driverInfo = driverInfo;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public TruckInfo getTruckInfo() {
        return truckInfo;
    }

    public void setTruckInfo(TruckInfo truckInfo) {
        this.truckInfo = truckInfo;
    }

    public DriverInfo getDriverInfo() {
        return driverInfo;
    }

    public void setDriverInfo(DriverInfo driverInfo) {
        this.driverInfo = driverInfo;
    }

    @Override
    public String toString() {
        return "Information{" +
                "orderInfo=" + orderInfo +
                ", truckInfo=" + truckInfo +
                ", driverInfo=" + driverInfo +
                '}';
    }
}
