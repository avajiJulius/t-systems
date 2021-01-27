package com.logiweb.avaji.services.implementetions.profile;


import com.logiweb.avaji.daos.CargoDAO;
import com.logiweb.avaji.daos.OrderDAO;
import com.logiweb.avaji.daos.OrderDetailsDAO;
import com.logiweb.avaji.dtos.*;
import com.logiweb.avaji.entities.enums.CargoStatus;
import com.logiweb.avaji.entities.enums.DriverStatus;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.OrderDetails;
import com.logiweb.avaji.exceptions.ShiftValidationException;
import com.logiweb.avaji.services.api.path.PathDetailsService;
import com.logiweb.avaji.services.implementetions.utils.PathParser;
import com.logiweb.avaji.services.api.profile.OrderDetailsService;
import com.logiweb.avaji.services.api.profile.ShiftDetailsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private static final Logger logger = LogManager.getLogger(OrderDetailsServiceImpl.class);

    private final OrderDetailsDAO orderDetailsDAO;
    private final OrderDAO orderDAO;
    private final CargoDAO cargoDAO;
    private final PathParser parser;
    private final ShiftDetailsService shiftDetailsService;
    private final PathDetailsService pathDetailsService;

    @Autowired
    public OrderDetailsServiceImpl(OrderDetailsDAO orderDetailsDAO, OrderDAO orderDAO, CargoDAO cargoDAO,
                                   PathParser parser, ShiftDetailsService shiftDetailsService,
                                   PathDetailsService pathDetailsService) {
        this.orderDetailsDAO = orderDetailsDAO;
        this.orderDAO = orderDAO;
        this.cargoDAO = cargoDAO;
        this.parser = parser;
        this.shiftDetailsService = shiftDetailsService;
        this.pathDetailsService = pathDetailsService;
    }

    @Override
    public OrderDetailsDTO readOrderDetails(long driverId) {
        OrderDetailsDTO orderDetails = orderDetailsDAO.findOrderDetails(driverId);

        if(orderDetails == null) {
            return null;
        }

        setCoDrivers(orderDetails, driverId);
        setRemainingPath(orderDetails);

        orderDetails.setCompleted(orderIsComplete(orderDetails.getId()));
        orderDetails.setPrettyPath(parser.toPrettyPath(orderDetails.getPath()));
        orderDetails.setWaypoints(orderDAO.findWaypointsOfThisOrder(orderDetails.getId()));

        return orderDetails;
    }



    @Override
    @Transactional
    public void updateOrderByCargoStatus(long driverId, List<Long> cargoIds) throws ShiftValidationException {
        shiftDetailsService.changeShiftDetails(driverId, DriverStatus.LOAD_UNLOAD_WORK);

        updateCargo(cargoIds);

        long orderId = orderDetailsDAO.findOrderIdOfDriverId(driverId);

        if(orderIsComplete(orderId)) {
            orderDetailsDAO.updateOnCompletedOrder(orderId);
            logger.info("Order by id {} is completed", orderId);
        }
    }

    @Override
    @Transactional
    public void changeCity(long orderId) {
        OrderDetails orderDetails = orderDetailsDAO.findOrderDetailsEntity(orderId);

        List<Long> path = parser.parseStringToLongList(orderDetails.getRemainingPath());

        Long cityCode = path.get(1);
        path.remove(0);

        double newRemainingHours = pathDetailsService.getShiftHours(path);
        double workedHours = orderDetails.getRemainingWorkingTime() - newRemainingHours;
        shiftDetailsService.updateWorkedHours(orderId, workedHours);

        orderDetails.setRemainingPath(parser.parseLongListToString(path));
        orderDetails.setRemainingWorkingTime(newRemainingHours);

        orderDetailsDAO.updateOrderDetails(orderDetails, cityCode);
    }


    private void setCoDrivers(OrderDetailsDTO orderDetails, long driverId) {
        List<DriverDTO> coDrivers = orderDAO.findDriversByOrderId(orderDetails.getId())
                .stream().filter(d -> d.getId() != driverId).collect(Collectors.toList());
        orderDetails.setCoDrivers(coDrivers);
    }

    private void setRemainingPath(OrderDetailsDTO orderDetails) {
        Deque<CityDTO> remainingPath = new ArrayDeque<>();

        List<CityDTO> pathList = parser.pathStringToCityDTOList(orderDetails.getRemainingPathString());
        remainingPath.addAll(pathList);

        orderDetails.setRemainingPath(remainingPath);

        setNextCity(orderDetails, pathList);
    }

    private void setNextCity(OrderDetailsDTO orderDetails, List<CityDTO> pathList) {
        if (pathList.size() > 1) {
            orderDetails.setNextCity(pathList.get(1));
        } else {
            orderDetails.setNextCity(new CityDTO(-1L, "No city left"));
        }
    }

    private void updateCargo(List<Long> cargoIds) {
        for(long id : cargoIds) {
            Cargo cargo = cargoDAO.findCargoById(id);

            int index = cargo.getCargoStatus().ordinal() + 1;
            cargo.setCargoStatus(CargoStatus.values()[index]);

            cargoDAO.updateCargo(cargo);
            logger.info("Cargo by id {} is {}", cargo.getCargoId(), cargo.getCargoStatus());
        }
    }

    public boolean orderIsComplete(long orderId) {
        return cargoDAO.findCargoByOrderId(orderId)
                .stream().allMatch(cargo -> cargo.getCargoStatus() == CargoStatus.DELIVERED);
    }


}
