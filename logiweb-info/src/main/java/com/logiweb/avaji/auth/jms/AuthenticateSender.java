package com.logiweb.avaji.auth.jms;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.*;

@Stateless
@LocalBean
public class AuthenticateSender {

    @Resource(name = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(name = "java:/jms/queue/auth/request")
    private Destination destination;

    public void send() {
        try (Connection connection = connectionFactory.createConnection();
             Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE)) {

            MessageProducer producer = session.createProducer(destination);

            producer.send(session.createTextMessage("give me auth users"));

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
