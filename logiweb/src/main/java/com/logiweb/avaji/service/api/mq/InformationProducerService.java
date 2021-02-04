package com.logiweb.avaji.service.api.mq;

public interface InformationProducerService {

    void sendFullInformation();
    void sendTruckInformation(String queue);
    void sendOrderInformation(String queue);
    void sendDriverInformation(String queue);
}
