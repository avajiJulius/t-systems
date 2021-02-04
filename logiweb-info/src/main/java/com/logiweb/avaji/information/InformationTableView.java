package com.logiweb.avaji.information;

import com.logiweb.avaji.model.Details;
import com.logiweb.avaji.model.DriverDetails;
import com.logiweb.avaji.model.OrderDetails;
import com.logiweb.avaji.model.TruckDetails;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named(value = "infoView")
@ApplicationScoped
public class InformationTableView {

    @Named(value = "orderDetails")
    @ViewScoped
    private OrderDetails orderDetails;
    @Named(value = "driverDetails")
    @ViewScoped
    private DriverDetails driverDetails;
    @Named(value = "truckDetails")
    @ViewScoped
    private TruckDetails truckDetails;


    public void setOrderDetails(Details orderDetails) {
        this.orderDetails = (OrderDetails) orderDetails;
    }

    public void setDriverDetails(Details driverDetails) {
        this.driverDetails = (DriverDetails) driverDetails;
    }

    public void setTruckDetails(Details truckDetails) {
        this.truckDetails = (TruckDetails) truckDetails;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public DriverDetails getDriverDetails() {
        return driverDetails;
    }

    public TruckDetails getTruckDetails() {
        return truckDetails;
    }

}
