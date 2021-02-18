package com.logiweb.avaji.service;

import com.logiweb.avaji.controller.InformationBean;
import com.logiweb.avaji.exception.MessageProcessingException;
import com.logiweb.avaji.model.DriverInfo;
import com.logiweb.avaji.model.Information;
import com.logiweb.avaji.model.OrderInfo;
import com.logiweb.avaji.model.TruckInfo;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.enterprise.inject.spi.BeanManager;
import javax.jms.JMSException;
import javax.jms.TextMessage;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InformationServiceTest {

    private InformationService service;
    private InformationListener listener;

    @BeforeEach
    void setUp() {
        this.service = new InformationService();
        this.service.beanManager = mock(BeanManager.class);
        this.listener = new InformationListener();
        this.listener.informationService = service;
    }

    @Test
    void givenMessage_whenExpectedResultIsSame_thenReturnedInformationIsEquals() throws JMSException {
        TextMessage message = new ActiveMQTextMessage();
        message.setText("{\"orderInfo\":{\"lastOrders\":[]}," +
                "\"truckInfo\":{\"totalNumber\":6,\"availableNumber\":4,\"inUseNumber\":0,\"faultyNumber\":2}," +
                "\"driverInfo\":{\"totalNumber\":10,\"availableNumber\":10,\"unavailableNumber\":0}}");
        listener.onMessage(message);

        Information result = service.getInformation();
        Information expected = new Information(new OrderInfo(),
                new TruckInfo(6,4,0,2),
                new DriverInfo(10, 10, 0));

        assertEquals(expected.toString(), result.toString());
    }


    @Test
    void givenMessage_whenExpectedResultIsDifferent_thenReturnedInformationIsNotEquals() throws JMSException {
        TextMessage message = new ActiveMQTextMessage();
        message.setText("{\"orderInfo\":{\"lastOrders\":[]}," +
                "\"truckInfo\":{\"totalNumber\":6,\"availableNumber\":4,\"inUseNumber\":0,\"faultyNumber\":2}," +
                "\"driverInfo\":{\"totalNumber\":10,\"availableNumber\":10,\"unavailableNumber\":0}}");
        listener.onMessage(message);

        Information result = service.getInformation();
        Information expected = new Information(new OrderInfo(),
                new TruckInfo(61,4,0,2),
                new DriverInfo(10, 10, 0));

        assertNotEquals(expected.toString(), result.toString());
    }

    @Test
    void whenMessageIsNotCorrect_thenThrowMessageProcessingException() throws JMSException {
        TextMessage message = new ActiveMQTextMessage();
        message.setText("{\"orderInfa\":{\"lastOrders\":[]}," +
                "\"truckInfo\":{\"totalNumber\":6,\"availableNumber\":4,\"inUseNumber\":0,\"faultyNumber\":2}," +
                "\"driverInfo\":{\"totalNumber\":10,\"availableNumber\":10,\"unavailableNumber\":0}}");


        assertThrows(MessageProcessingException.class, () -> listener.onMessage(message));
    }
}