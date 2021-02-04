package com.logiweb.avaji.service.api.mq;

public interface InformationProducerService {

    void sendFullInformation();
    void sendTruckInformation();
    void sendOrderInformation();
    void sendDriverInformation();
}
