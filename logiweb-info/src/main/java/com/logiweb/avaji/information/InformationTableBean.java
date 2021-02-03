package com.logiweb.avaji.information;

import com.logiweb.avaji.model.DriverDetails;
import com.logiweb.avaji.model.OrderDetails;
import com.logiweb.avaji.model.TruckDetails;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named(value = "informationTableBean")
@ApplicationScoped
public class InformationTableBean {

    private OrderDetails orderDetails;
    private DriverDetails driverDetails;
    private TruckDetails truckDetails;

    @EJB
    private InformationManager informationManager;

    @PostConstruct
    private void initialize() {
        orderDetails = informationManager.getOrderDetails();
        driverDetails = informationManager.getDriverDetails();
        truckDetails = informationManager.getTruckDetails();
    }
}
