package com.logiweb.avaji.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logiweb.avaji.exception.MessageProcessingException;
import com.logiweb.avaji.model.Information;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(InformationListener.class);

    @Inject
    private InformationService informationService;

    @Override
    public void onMessage(Message message) {
        try {
            logger.info("Receive information message ");
            String jsonDetails = ((TextMessage) message).getText();

            System.out.println(jsonDetails);

            ObjectMapper mapper = new ObjectMapper();
            Information information = mapper.readValue(jsonDetails, Information.class);

            informationService.updateInformation(information);
        } catch (JMSException e) {
            logger.error("JMS exception");
            throw new MessageProcessingException("Cannot process message because jms exception");
        } catch (JsonProcessingException e) {
            logger.error("Cannot process message");
            throw new MessageProcessingException("Cannot process message");
        }
    }

}
