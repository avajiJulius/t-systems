package com.logiweb.avaji.service.implementetions.mq;

import com.logiweb.avaji.service.api.mq.InformationProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;

@Component
public class InitializeListener implements MessageListener {

    private final InformationProducerService producerService;

    @Autowired
    public InitializeListener(InformationProducerService producerService) {
        this.producerService = producerService;
    }

    @Override
    public void onMessage(Message message) {
        producerService.sendInformation();
    }
}
