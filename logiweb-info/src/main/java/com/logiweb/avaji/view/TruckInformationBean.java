package com.logiweb.avaji.view;

import com.logiweb.avaji.ejb.InformationManager;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named(value = "truckInfo")
@ViewScoped
public class TruckInformationBean implements Serializable {
    private int totalNumber;
    private int availableNumber;
    private int inUseNumber;
    private int faultyNumber;

    @EJB
    private InformationManager informationManager;

    public int getTotalNumber() {
        return informationManager.getTruckDetails().getTotalNumber();
    }

    public int getAvailableNumber() {
        return informationManager.getTruckDetails().getAvailableNumber();
    }

    public int getInUseNumber() {
        return informationManager.getTruckDetails().getInUseNumber();
    }

    public int getFaultyNumber() {
        return informationManager.getTruckDetails().getFaultyNumber();
    }

}
