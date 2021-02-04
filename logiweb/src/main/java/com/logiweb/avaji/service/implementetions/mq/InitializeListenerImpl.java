package com.logiweb.avaji.service.implementetions.mq;

import com.logiweb.avaji.service.api.mq.InformationProducerService;
import com.logiweb.avaji.service.api.mq.InitializeListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;

public class InitializeListenerImpl implements InitializeListener {

    private InformationProducerService producerService;

    @Autowired
    public InitializeListenerImpl(InformationProducerService producerService) {
        this.producerService = producerService;
    }

    @Override
    @JmsListener(destination = "123")
    public void responseOnInit() {
        producerService.sendFullInformation();
    }
}
