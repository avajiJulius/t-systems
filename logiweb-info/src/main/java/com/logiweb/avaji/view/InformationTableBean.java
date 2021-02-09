package com.logiweb.avaji.view;

import com.logiweb.avaji.ejb.InformationManager;
import com.logiweb.avaji.model.Information;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named(value = "tableInfo")
@ViewScoped
public class InformationTableBean implements Serializable {

    private Information information;

    @EJB
    private InformationManager informationManager;

    @PostConstruct
    private void init() {
        informationManager.init();
    }

    public Information getInformation() {
        return informationManager.getInformation();
    }
}
