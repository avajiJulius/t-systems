package com.logiweb.avaji.service;

import com.logiweb.avaji.model.Information;
import com.logiweb.avaji.util.InformationUpdateEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

@Stateful
@LocalBean
@ApplicationScoped
public class InformationService {

    private static final Logger logger = LogManager.getLogger(InformationService.class);

    private static Information information;

    @Inject
    private BeanManager beanManager;

    public Information getInformation() {
        return information;
    }

    public void updateInformation(Information information) {
        this.information = information;
        logger.info("Information is updated");
        beanManager.fireEvent(new InformationUpdateEvent());
    }
}
