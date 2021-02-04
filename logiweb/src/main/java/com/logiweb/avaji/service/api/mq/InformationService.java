package com.logiweb.avaji.service.api.mq;

import com.logiweb.avaji.entity.model.mq.DriverInfo;
import com.logiweb.avaji.entity.model.mq.OrderInfo;
import com.logiweb.avaji.entity.model.mq.TruckInfo;

public interface InformationService {
    OrderInfo getOrderInformation();
    DriverInfo getDriverInformation();
    TruckInfo getTruckInformation();
}
