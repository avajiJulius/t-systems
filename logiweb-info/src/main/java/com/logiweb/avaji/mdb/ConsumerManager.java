package com.logiweb.avaji.mdb;

import com.logiweb.avaji.model.DriverDetails;
import com.logiweb.avaji.model.OrderDetails;
import com.logiweb.avaji.model.TruckDetails;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

@Stateful
@LocalBean
public class ConsumerManager {

    private DriverDetails driverDetails;
    private OrderDetails orderDetails;
    private TruckDetails truckDetails;

    public DriverDetails getDriverDetails() {
        return driverDetails;
    }

    public void setDriverDetails(DriverDetails driverDetails) {
        this.driverDetails = driverDetails;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public TruckDetails getTruckDetails() {
        return truckDetails;
    }

    public void setTruckDetails(TruckDetails truckDetails) {
        this.truckDetails = truckDetails;
    }
}
