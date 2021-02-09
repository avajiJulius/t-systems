package com.logiweb.avaji.mdb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logiweb.avaji.ejb.InformationManager;
import com.logiweb.avaji.model.Information;
import org.jboss.ejb3.annotation.ResourceAdapter;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.*;


@MessageDriven(name = "InformationMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:global/remoteContext/driverTopic"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "user", propertyValue = "amq"),
        @ActivationConfigProperty(propertyName = "password", propertyValue = "amq"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
@ResourceAdapter(value="remote-artemis")
public class InformationListener implements MessageListener {

    @Inject
    private InformationManager informationManager;

    @Override
    public void onMessage(Message message) {
        try {
            String jsonDetails = ((TextMessage) message).getText();
            System.out.println(jsonDetails);
            ObjectMapper mapper = new ObjectMapper();
            Information information = mapper.readValue(jsonDetails, Information.class);

            informationManager.setInformation(information);

        } catch (JMSException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
