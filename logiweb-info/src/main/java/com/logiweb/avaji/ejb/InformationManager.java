package com.logiweb.avaji.ejb;

import com.logiweb.avaji.mdb.ConsumerManager;
import com.logiweb.avaji.model.DriverDetails;
import com.logiweb.avaji.model.OrderDetails;
import com.logiweb.avaji.model.TruckDetails;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class InformationManager {

    @EJB
    private ConsumerManager consumerManager;

    public DriverDetails getDriverDetails() {
        return consumerManager.getDriverDetails();
    }

    public TruckDetails getTruckDetails() {
        return consumerManager.getTruckDetails();
    }

    public OrderDetails getOrderDetails() {
        return consumerManager.getOrderDetails();
    }
}
