package com.logiweb.avaji.information.jms;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.*;

@Stateless
@LocalBean
public class InitializeSender {

    @Resource(name = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(name = "java:/jms/queue/init")
    private Destination destination;

    public void send() {
        try (Connection connection = connectionFactory.createConnection();
             Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE)) {

            MessageProducer producer = session.createProducer(destination);

            producer.send(session.createTextMessage("give me full info"));

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
