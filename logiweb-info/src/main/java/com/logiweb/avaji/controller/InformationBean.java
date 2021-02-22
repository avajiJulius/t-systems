package com.logiweb.avaji.controller;

import com.logiweb.avaji.service.InformationService;
import com.logiweb.avaji.model.Information;
import com.logiweb.avaji.service.InitializeService;
import com.logiweb.avaji.util.InformationUpdateEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named(value = "informationBean")
@ApplicationScoped
public class InformationBean implements Serializable {

    private static final Logger logger = LogManager.getLogger(InformationBean.class);

    private static Information information;

    @Inject
    private InformationService informationService;

    @Inject
    private InitializeService initializeService;

    @Inject @Push
    private PushContext newInformation;

    @PostConstruct
    private void init() {
        initializeService.init();
        load();
        logger.info("Application Initialize");
    }

    public void load() {
        information = informationService.getInformation();
    }

    public void onUpdate(@Observes InformationUpdateEvent event) {
        logger.info("information update on bean");
        load();
        newInformation.send("updated");
    }

    public Information getInformation() {
        return information;
    }
}
