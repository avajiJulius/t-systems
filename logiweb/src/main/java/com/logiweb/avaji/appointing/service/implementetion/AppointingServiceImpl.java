package com.logiweb.avaji.appointing.service.implementetion;

import com.logiweb.avaji.appointing.service.api.AppointingService;
import com.logiweb.avaji.crud.driver.dao.DriverDAO;
import com.logiweb.avaji.crud.driver.dto.DriverPublicResponseDto;
import com.logiweb.avaji.crud.order.dao.OrderDAO;
import com.logiweb.avaji.crud.truck.dao.TruckDAO;
import com.logiweb.avaji.crud.workdetails.dao.WorkDetailsDAO;
import com.logiweb.avaji.dtoconverter.DtoConverter;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.orderdetails.service.api.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointingServiceImpl implements AppointingService {

    private final TruckDAO truckDAO;
    private final OrderDAO orderDAO;
    private final DriverDAO driverDAO;
    private final WorkDetailsDAO workDetailsDAO;
    private final OrderDetailsService orderDetailsService;
    private final DtoConverter converter;

    @Autowired
    public AppointingServiceImpl(TruckDAO truckDAO, OrderDAO orderDAO, DriverDAO driverDAO,
                                 WorkDetailsDAO workDetailsDAO, OrderDetailsService orderDetailsService, DtoConverter converter) {
        this.truckDAO = truckDAO;
        this.orderDAO = orderDAO;
        this.driverDAO = driverDAO;
        this.workDetailsDAO = workDetailsDAO;
        this.orderDetailsService = orderDetailsService;
        this.converter = converter;
    }

    @Override
    public List<Truck> readTrucksForOrder(long orderId) {
        orderDetailsService.init(orderId);
        Double maxCapacity = orderDetailsService.getMaxCapacity();

        return truckDAO.findTrucksForOrder(maxCapacity);
    }


    @Override
    public void addTruckToOrder(String truckId, long orderId) {
        Truck truck = truckDAO.findTruckById(truckId);
        Order order = orderDAO.findOrderById(orderId);
        order.setDesignatedTruck(truck);
        orderDAO.updateOrder(order);
    }

    @Override
    public List<DriverPublicResponseDto> readDriverForOrder(long orderId) {
        orderDetailsService.init(orderId);
        Order order = orderDAO.findOrderById(orderId);
        long cityCode = order.getDesignatedTruck().getCurrentCity().getCityCode();
        Double shiftHours = orderDetailsService.getShiftHours();

        long untilEndOfMonth = orderDetailsService.calculateTimeUntilEndOfMonth();
        if(shiftHours > untilEndOfMonth) {
            shiftHours = (double) untilEndOfMonth;
        }

        return converter.driversToDtos(driverDAO.findDriverForOrder(shiftHours, cityCode));
    }

    @Override
    public void addDriversToOrder(List<Long> driversIds, long orderId) {
        String truckId = orderDAO.findOrderById(orderId).getDesignatedTruck().getTruckId();
        Truck truck = truckDAO.findTruckById(truckId);
        List<Driver> drivers = workDetailsDAO.findDriversByIds(driversIds);
        truck.setDrivers(drivers);

        truckDAO.updateTruck(truck);
    }


}
