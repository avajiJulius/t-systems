package com.logiweb.avaji.service.implementetions.mq;

import com.logiweb.avaji.dtos.mq.InformationDTO;
import com.logiweb.avaji.service.api.mq.InformationProducerService;
import com.logiweb.avaji.service.api.mq.InformationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class InformationProducerServiceImpl implements InformationProducerService {

    private static final Logger logger = LogManager.getLogger(InformationProducerServiceImpl.class);

    private static InformationDTO informationDTO;

    private final JmsTemplate jmsTemplate;
    private final InformationService informationService;

    @Autowired
    public InformationProducerServiceImpl(JmsTemplate jmsTemplate, InformationService informationService) {
        this.jmsTemplate = jmsTemplate;
        this.informationService = informationService;
    }

    @PostConstruct
    private void init() {
        logger.info("Information producer init");

        if (informationDTO == null) {
            informationDTO = informationService.getFullInformation();
        }

        sendInformation();
    }

    @Override
    public void updateTruckInformation() {
        informationDTO.setTruckInfo(informationService.getTruckInformation());
    }

    @Override
    public void updateOrderInformation() {
        informationDTO.setOrderInfo(informationService.getOrderInformation());
    }

    @Override
    public void updateDriverInformation() {
        informationDTO.setDriverInfo(informationService.getDriverInformation());
    }

    @Override
    public void sendInformation() {
        jmsTemplate.convertAndSend("driverTopic", informationDTO);
        logger.info("Information message send");
    }
}
