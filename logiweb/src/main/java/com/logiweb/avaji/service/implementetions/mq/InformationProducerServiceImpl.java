package com.logiweb.avaji.service.implementetions.mq;

import com.logiweb.avaji.entity.model.mq.OrderInfo;
import com.logiweb.avaji.entity.model.mq.TruckInfo;
import com.logiweb.avaji.service.api.mq.InformationProducerService;
import com.logiweb.avaji.service.api.mq.InformationService;
import com.logiweb.avaji.service.implementetions.management.DriverServiceImpl;
import org.apache.activemq.broker.BrokerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class InformationProducerServiceImpl implements InformationProducerService {

    private static final Logger logger = LogManager.getLogger(InformationProducerServiceImpl.class);

    private final JmsTemplate jmsTemplate;
    private final InformationService informationService;

    @Autowired
    public InformationProducerServiceImpl(JmsTemplate jmsTemplate, InformationService informationService) {
        this.jmsTemplate = jmsTemplate;
        this.informationService = informationService;
    }


    @Override
    public void sendFullInformation() {
        sendOrderInformation();
        sendTruckInformation();
        sendDriverInformation();
    }

    @Override
    public void sendTruckInformation() {
        TruckInfo truckInfo = informationService.getTruckInformation();
        jmsTemplate.convertAndSend("truck.topic", truckInfo);
        logger.info("Send {}", truckInfo.toString());
    }

    @Override
    public void sendOrderInformation() {
        OrderInfo orderInfo = informationService.getOrderInformation();
        jmsTemplate.convertAndSend("order.topic", orderInfo);
        logger.info("Send {}", orderInfo.toString());
    }

    @Override
    public void sendDriverInformation() {
        OrderInfo orderInfo = informationService.getOrderInformation();
        jmsTemplate.convertAndSend("driver.topic", orderInfo);
        logger.info("Send {}", orderInfo.toString());
    }
}
