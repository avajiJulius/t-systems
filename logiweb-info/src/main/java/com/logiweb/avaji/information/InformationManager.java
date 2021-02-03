package com.logiweb.avaji.information;

import com.logiweb.avaji.model.DriverDetails;
import com.logiweb.avaji.model.OrderDetails;
import com.logiweb.avaji.model.TruckDetails;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@SessionScoped
public class InformationManager implements Serializable {

    public OrderDetails getOrderDetails() {
        return new OrderDetails();
    }

    public DriverDetails getDriverDetails() {
        return new DriverDetails();
    }

   public TruckDetails getTruckDetails() {
        return new TruckDetails();
   }

}
