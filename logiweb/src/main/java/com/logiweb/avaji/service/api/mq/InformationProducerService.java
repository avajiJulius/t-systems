package com.logiweb.avaji.service.api.mq;


/**
 * This service update Information by OrderInfo(about last 10 orders), TruckInfo and DriverInfo(numbers of trucks and drivers)
 * and produce message by converting Information to json and send it to MQ Broker topic.
 *
 */
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
