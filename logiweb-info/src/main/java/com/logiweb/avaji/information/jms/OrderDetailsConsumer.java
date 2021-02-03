package com.logiweb.avaji.information.jms;

import com.logiweb.avaji.model.OrderDetails;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(name = "driverDetailsConsumer",
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/topic/truck")
        })
public class OrderDetailsConsumer implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            OrderDetails details = message.getBody(OrderDetails.class);

            if(details != null) {
                //NOTIFY
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
