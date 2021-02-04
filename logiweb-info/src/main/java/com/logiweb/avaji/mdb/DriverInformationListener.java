package com.logiweb.avaji.mdb;

import com.logiweb.avaji.model.DriverDetails;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.*;

@MessageDriven(name = "driverInformationListener",
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/topic/driver")
        })
public class DriverInformationListener implements MessageListener {

    @EJB
    private ConsumerManager consumerManager;

    @Inject
    @JMSConnectionFactory("java:/ConnectionFactory")
    private JMSContext context;

    @Override
    public void onMessage(Message message) {
        try {

            consumerManager.setDriverDetails(message.getBody(DriverDetails.class));
            System.out.println("hello");

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
