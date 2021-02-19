package com.logiweb.avaji.service.api.mq;

import com.logiweb.avaji.dtos.mq.DriverInfo;
import com.logiweb.avaji.dtos.mq.InformationDTO;
import com.logiweb.avaji.dtos.mq.OrderInfo;
import com.logiweb.avaji.dtos.mq.TruckInfo;

public interface InformationService {

    /**
     * Create full Information entity by calling <code>getOrderInformation</code>,
     * <code>getTruckInformation</code>, <code>getDriverInformation</code> methods
     *
     * @return Information dto
     */
    InformationDTO getFullInformation();

    /**
     * Read Information about last 10 orders from database
     *
     * @return orderInfo
     */
    OrderInfo getOrderInformation();

    /**
     * Read Information about total, available, unavailable numbers of drivers.
     *
     * @return driverInfo
     */
    DriverInfo getDriverInformation();

    /**
     * Read Information about total, available, inUse, faulty numbers of trucks
     *
     * @return truckInfo
     */
    TruckInfo getTruckInformation();
}
