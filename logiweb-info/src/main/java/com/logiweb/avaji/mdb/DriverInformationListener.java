package com.logiweb.avaji.mdb;

import com.logiweb.avaji.model.DriverDetails;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.*;

@MessageDriven(name = "driverInformationListener", mappedName = "DriverTopic",
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "DriverTopic"),
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
                @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
        })
public class DriverInformationListener implements MessageListener {

    @EJB
    private ConsumerManager consumerManager;

    @Override
    public void onMessage(Message message) {
        try {

            System.out.println(((TextMessage) message).getText());
            consumerManager.setDriverDetails(message.getBody(DriverDetails.class));

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
