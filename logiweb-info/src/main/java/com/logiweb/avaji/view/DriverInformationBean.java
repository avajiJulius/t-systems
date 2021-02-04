package com.logiweb.avaji.view;

import com.logiweb.avaji.ejb.InformationManager;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named(value = "driverInfo")
@ViewScoped
public class DriverInformationBean implements Serializable {

    @EJB
    private InformationManager informationManager;

    public int getTotalNumber() {
        return informationManager.getDriverDetails().getTotalNumber();
    }

    public int getAvailableNumber() {
        return informationManager.getDriverDetails().getAvailableNumber();
    }

    public int getUnavailableNumber() {
        return informationManager.getDriverDetails().getUnavailableNumber();
    }

}
