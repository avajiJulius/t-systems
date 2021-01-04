package com.logiweb.avaji.orderdetails.service.implementetion;

import com.logiweb.avaji.orderdetails.service.api.OrderDetailsService;
import com.logiweb.avaji.crud.driver.dao.DriverDAO;
import com.logiweb.avaji.crud.driver.dto.DriverPublicResponseDto;
import com.logiweb.avaji.crud.order.dao.OrderDAO;
import com.logiweb.avaji.crud.truck.dao.TruckDAO;
import com.logiweb.avaji.crud.workdetails.dao.WorkDetailsDAO;
import com.logiweb.avaji.dtoconverter.DtoConverter;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.orderdetails.service.api.ShiftDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final TruckDAO truckDAO;
    private final OrderDAO orderDAO;
    private final DriverDAO driverDAO;
    private final WorkDetailsDAO workDetailsDAO;
    private final ShiftDetailsService shiftDetailsService;
    private final DtoConverter converter;

    @Autowired
    public OrderDetailsServiceImpl(TruckDAO truckDAO, OrderDAO orderDAO, DriverDAO driverDAO,
                                   WorkDetailsDAO workDetailsDAO, ShiftDetailsService shiftDetailsService, DtoConverter converter) {
        this.truckDAO = truckDAO;
        this.orderDAO = orderDAO;
        this.driverDAO = driverDAO;
        this.workDetailsDAO = workDetailsDAO;
        this.shiftDetailsService = shiftDetailsService;
        this.converter = converter;
    }

    @Override
    public List<Truck> readTrucksForOrder(long orderId) {
        shiftDetailsService.init(orderId);
        Double maxCapacity = shiftDetailsService.getMaxCapacity();

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
        shiftDetailsService.init(orderId);
        Order order = orderDAO.findOrderById(orderId);
        long cityCode = order.getDesignatedTruck().getCurrentCity().getCityCode();
        Double shiftHours = shiftDetailsService.getShiftHours();

        long untilEndOfMonth = shiftDetailsService.calculateTimeUntilEndOfMonth();
        if(shiftHours > untilEndOfMonth) {
            shiftHours = (double) untilEndOfMonth;
        }

        return converter.driversToDtos(driverDAO.findDriverForOrder(shiftHours, cityCode));
    }

    @Override
    @Transactional
    public void addDriversToOrder(List<Long> driversIds, long orderId) {
        String truckId = orderDAO.findOrderById(orderId).getDesignatedTruck().getTruckId();
        Truck truck = truckDAO.findTruckById(truckId);
        List<Driver> drivers = workDetailsDAO.findDriversByIds(driversIds);
        for(Driver driver: drivers) {
            driver.setCurrentTruck(truck);
        }
        driverDAO.updateDrivers(drivers);
    }


}
