package com.logiweb.avaji.information.jms;

import com.logiweb.avaji.model.TruckDetails;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(name = "truckDetailsConsumer",
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/topic/truck")
        })
public class TruckDetailsConsumer implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            TruckDetails details = message.getBody(TruckDetails.class);
            if(details != null) {
                //NOTIFY
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
