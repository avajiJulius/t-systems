package com.logiweb.avaji.orderdetails.service.implementetion;

import com.logiweb.avaji.crud.truck.dto.TruckDTO;
import com.logiweb.avaji.orderdetails.dao.OrderDetailsDAO;
import com.logiweb.avaji.orderdetails.service.api.OrderDetailsService;
import com.logiweb.avaji.crud.driver.dao.DriverDAO;
import com.logiweb.avaji.crud.driver.dto.DriverPublicResponseDto;
import com.logiweb.avaji.crud.order.dao.OrderDAO;
import com.logiweb.avaji.crud.truck.dao.TruckDAO;
import com.logiweb.avaji.crud.workdetails.dao.WorkDetailsDAO;
import com.logiweb.avaji.mapper.Mapper;
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

    private final OrderDetailsDAO orderDetailsDAO;
    private final OrderDAO orderDAO;
    private final DriverDAO driverDAO;
    private final WorkDetailsDAO workDetailsDAO;
    private final ShiftDetailsService shiftDetailsService;
    private final Mapper mapper;

    @Autowired
    public OrderDetailsServiceImpl(OrderDetailsDAO orderDetailsDAO, TruckDAO truckDAO,
                                   OrderDAO orderDAO, DriverDAO driverDAO, WorkDetailsDAO workDetailsDAO, ShiftDetailsService shiftDetailsService, Mapper mapper) {
        this.orderDetailsDAO = orderDetailsDAO;
        this.orderDAO = orderDAO;
        this.driverDAO = driverDAO;
        this.workDetailsDAO = workDetailsDAO;
        this.shiftDetailsService = shiftDetailsService;
        this.mapper = mapper;
    }

    @Override
    public List<TruckDTO> readTrucksForOrder(long orderId) {
        shiftDetailsService.init(orderId);
        Double maxCapacity = shiftDetailsService.getMaxCapacity();

        return orderDetailsDAO.findTrucksForOrder(maxCapacity);
    }


    @Override
    public void addTruckToOrder(String truckId, long orderId) {
        Truck truck = orderDetailsDAO.findTruckEntityById(truckId);
        Order order = orderDetailsDAO.findOrderById(orderId);
        order.setDesignatedTruck(truck);
        orderDAO.updateOrder(order);
    }

    @Override
    public List<DriverPublicResponseDto> readDriverForOrder(long orderId) {
        shiftDetailsService.init(orderId);
        Order order = orderDetailsDAO.findOrderById(orderId);
        long cityCode = order.getDesignatedTruck().getCurrentCity().getCityCode();
        Double shiftHours = shiftDetailsService.getShiftHours();

        long untilEndOfMonth = shiftDetailsService.calculateTimeUntilEndOfMonth();
        if(shiftHours > untilEndOfMonth) {
            shiftHours = (double) untilEndOfMonth;
        }

        return mapper.driversToDtos(driverDAO.findDriverForOrder(shiftHours, cityCode));
    }

    @Override
    @Transactional
    public void addDriversToOrder(List<Long> driversIds, long orderId) {
        String truckId = orderDetailsDAO.findOrderById(orderId).getDesignatedTruck().getTruckId();
        Truck truck = orderDetailsDAO.findTruckEntityById(truckId);
        List<Driver> drivers = workDetailsDAO.findDriversByIds(driversIds);
        for(Driver driver: drivers) {
            driver.setCurrentTruck(truck);
        }
        driverDAO.updateDrivers(drivers);
    }


}
