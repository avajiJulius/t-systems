package com.logiweb.avaji.ejb;

import com.logiweb.avaji.model.Information;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;

@Stateless
@LocalBean
public class InformationManager {

    private Information information;

    @Inject
    @JMSConnectionFactory("java:/RemoteJmsXA")
    @JMSPasswordCredential(userName = "amq", password = "amq")
    private JMSContext context;

    @Resource(lookup = "java:global/remoteContext/initQueue")
    private Queue queue;

    public void init() {
        context.createProducer().send(queue, "START");
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }
}
