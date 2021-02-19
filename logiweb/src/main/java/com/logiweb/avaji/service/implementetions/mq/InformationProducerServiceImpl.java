package com.logiweb.avaji.service.implementetions.mq;

import com.logiweb.avaji.dtos.mq.InformationDTO;
import com.logiweb.avaji.service.api.mq.InformationProducerService;
import com.logiweb.avaji.service.api.mq.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class InformationProducerServiceImpl implements InformationProducerService {

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
        if (informationDTO == null) {
            informationDTO = informationService.getFullInformation();
        }
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
    }
}
