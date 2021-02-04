package com.logiweb.avaji.entity.model.mq;

import com.logiweb.avaji.dtos.OrderDTO;
import com.logiweb.avaji.service.api.mq.InformationService;
import lombok.Data;

import java.util.Deque;

@Data
public class TableInformation {
    private OrderInfo orderInfo;
    private DriverInfo driverInfo;
    private TruckInfo truckInfo;

    private static TableInformation tableInformation;

    private TableInformation(InformationService informationService) {
        orderInfo = informationService.getOrderInformation();
        driverInfo = informationService.getDriverInformation();
        truckInfo = informationService.getTruckInformation();
    }

    public static TableInformation init(InformationService informationService) {
        if (tableInformation == null) {
            tableInformation = new TableInformation(informationService);
        }
        return tableInformation;
    }

    public void setLastModifiedOrder(OrderDTO order) {
        Deque<OrderDTO> lastOrders = orderInfo.getLastOrders();
        if(lastOrders.size() >= 10) {
            lastOrders.addFirst(order);
            lastOrders.removeLast();
        } else {
            lastOrders.addFirst(order);
        }
        orderInfo.setLastOrders(lastOrders);
    }
}
