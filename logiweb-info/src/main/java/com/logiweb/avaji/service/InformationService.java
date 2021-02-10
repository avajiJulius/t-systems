package com.logiweb.avaji.service;

import com.logiweb.avaji.model.Information;
import com.logiweb.avaji.util.InformationUpdateEvent;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

@Stateful
@LocalBean
@ApplicationScoped
public class InformationService {

    private Information information;

    @Inject
    private BeanManager beanManager;

    public Information getInformation() {
        return information;
    }

    public void updateInformation(Information information) {
        this.information = information;
        beanManager.fireEvent(new InformationUpdateEvent());
    }
}
