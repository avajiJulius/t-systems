package com.logiweb.avaji.auth.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.Singleton;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.List;

@MessageDriven(name = "authenticateConsumer",
activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/auth/response")
})
@Singleton
public class AuthenticateConsumer implements MessageListener {

    private List<AuthenticatedUser> users;

    @Override
    public void onMessage(Message message) {

    }

    public List<AuthenticatedUser> getUsers() {
        return users;
    }
}
