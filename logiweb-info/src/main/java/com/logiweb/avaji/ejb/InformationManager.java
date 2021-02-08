package com.logiweb.avaji.ejb;

import com.logiweb.avaji.mdb.ConsumerManager;
import com.logiweb.avaji.model.DriverDetails;
import com.logiweb.avaji.model.OrderDetails;
import com.logiweb.avaji.model.TruckDetails;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.*;

@Stateless
@LocalBean
public class InformationManager {

    @EJB
    private ConsumerManager consumerManager;

    @Resource(lookup = "java:/MQConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "java:jboss/activemq/queue/StartQueue")
    private Queue queue;

    @PostConstruct
    private void init() {
        try (Connection connection = connectionFactory.createConnection();
             Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {

            connection.start();

            System.out.println("SEND START");
            MessageProducer producer = session.createProducer(queue);

            producer.send(session.createTextMessage("START"));

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public DriverDetails getDriverDetails() {
        return consumerManager.getDriverDetails();
    }

    public TruckDetails getTruckDetails() {
        return consumerManager.getTruckDetails();
    }

    public OrderDetails getOrderDetails() {
        return consumerManager.getOrderDetails();
    }
}
