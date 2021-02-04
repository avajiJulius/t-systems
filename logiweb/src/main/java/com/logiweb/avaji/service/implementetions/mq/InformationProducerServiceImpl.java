package com.logiweb.avaji.service.implementetions.mq;

import com.logiweb.avaji.entity.model.mq.TableInformation;
import com.logiweb.avaji.service.api.mq.InformationProducerService;
import com.logiweb.avaji.service.api.mq.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class InformationProducerServiceImpl implements InformationProducerService {

    private final JmsTemplate jmsTemplate;
    private final TableInformation information;

    @Autowired
    public InformationProducerServiceImpl(JmsTemplate jmsTemplate, InformationService informationService) {
        this.jmsTemplate = jmsTemplate;
        this.information = TableInformation.init(informationService);
    }

    @Override
    public void sendFullInformation() {
        sendOrderInformation();
        sendTruckInformation();
        sendDriverInformation();
    }

    @Override
    public void sendTruckInformation(String queue) {
        jmsTemplate.convertAndSend(queue, information.getTruckInfo());
    }

    @Override
    public void sendOrderInformation(String queue) {
        jmsTemplate.convertAndSend(queue, information.getOrderInfo());
    }

    @Override
    public void sendDriverInformation(String queue) {
        jmsTemplate.convertAndSend(queue, information.getDriverInfo());
    }
}
