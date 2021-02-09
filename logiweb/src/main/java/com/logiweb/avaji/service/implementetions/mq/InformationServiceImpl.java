package com.logiweb.avaji.service.implementetions.mq;

import com.logiweb.avaji.dao.InformationDAO;
import com.logiweb.avaji.dtos.mq.DriverInfo;
import com.logiweb.avaji.dtos.mq.InformationDTO;
import com.logiweb.avaji.dtos.mq.TruckInfo;
import com.logiweb.avaji.dtos.mq.OrderInfo;
import com.logiweb.avaji.service.api.mq.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InformationServiceImpl implements InformationService {

    private final InformationDAO informationDAO;

    @Autowired
    public InformationServiceImpl(InformationDAO informationDAO) {
        this.informationDAO = informationDAO;
    }


    @Override
    public InformationDTO getFullInformation() {
        OrderInfo orderInformation = informationDAO.getOrderInformation();
        TruckInfo truckInformation = informationDAO.getTruckInformation();
        DriverInfo driverInformation = informationDAO.getDriverInformation();

        return new InformationDTO(orderInformation, truckInformation, driverInformation);
    }


    @Override
    public OrderInfo getOrderInformation() {
        return informationDAO.getOrderInformation();
    }

    @Override
    public DriverInfo getDriverInformation() {
        return informationDAO.getDriverInformation();
    }

    @Override
    public TruckInfo getTruckInformation() {
        return informationDAO.getTruckInformation();
    }
}
