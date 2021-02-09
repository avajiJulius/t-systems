package com.logiweb.avaji.service.api.mq;

import com.logiweb.avaji.dtos.mq.DriverInfo;
import com.logiweb.avaji.dtos.mq.InformationDTO;
import com.logiweb.avaji.dtos.mq.OrderInfo;
import com.logiweb.avaji.dtos.mq.TruckInfo;

public interface InformationService {
    InformationDTO getFullInformation();
    OrderInfo getOrderInformation();
    DriverInfo getDriverInformation();
    TruckInfo getTruckInformation();
}
