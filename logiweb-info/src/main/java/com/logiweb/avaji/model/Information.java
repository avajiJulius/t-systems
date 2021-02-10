package com.logiweb.avaji.model;

public class Information {

    private OrderInfo orderInfo;
    private TruckInfo truckInfo;
    private DriverInfo driverInfo;


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
}
