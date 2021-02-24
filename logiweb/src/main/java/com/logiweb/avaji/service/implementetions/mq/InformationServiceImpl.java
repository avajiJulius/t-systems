package com.logiweb.avaji.service.implementetions.mq;

import com.logiweb.avaji.dao.InformationDAO;
import com.logiweb.avaji.dtos.mq.*;
import com.logiweb.avaji.service.api.mq.InformationService;
import com.logiweb.avaji.service.implementetions.utils.PathParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InformationServiceImpl implements InformationService {

    private final InformationDAO informationDAO;
    private final PathParser pathParser;

    @Autowired
    public InformationServiceImpl(InformationDAO informationDAO, PathParser pathParser) {
        this.informationDAO = informationDAO;
        this.pathParser = pathParser;
    }


    @Override
    @Transactional
    public InformationDTO getFullInformation() {
        return new InformationDTO(getOrderInformation(), getTruckInformation(), getDriverInformation());
    }


    @Override
    public OrderInfo getOrderInformation() {
        List<InfoOrderDTO> lastOrders = informationDAO.getOrderInformation().getLastOrders();

        for (InfoOrderDTO order : lastOrders) {
            String path = pathParser.toPrettyPath(order.getPath());

            order.setDrivers(informationDAO.findDriversOfOrder(order.getOrderId()));
            order.setPath(path);
        }

        return new OrderInfo(lastOrders);

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
