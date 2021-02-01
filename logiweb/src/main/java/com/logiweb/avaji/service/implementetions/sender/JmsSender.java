package com.logiweb.avaji.service.implementetions.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsSender {

    private final JmsTemplate jmsTemplate;

    @Autowired
    public JmsSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(String queue, String message) {
        jmsTemplate.convertAndSend(queue, message);
    }
}
