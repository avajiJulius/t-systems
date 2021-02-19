package com.logiweb.avaji.service.api.mq;

public interface InformationProducerService {

    /**
     * Update information by truck info
     */
    void updateTruckInformation();

    /**
     * Update information by order info
     *
     */
    void updateOrderInformation();

    /**
     * Update information by drive info
     */
    void updateDriverInformation();

    /**
     * Send information by converting Information to json and send to ActiveMQ broker
     */
    void sendInformation();
}
