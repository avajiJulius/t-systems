package com.logiweb.avaji.service.api.mq;

public interface InformationProducerService {
    void updateTruckInformation();
    void updateOrderInformation();
    void updateDriverInformation();
    void sendInformation();
}
