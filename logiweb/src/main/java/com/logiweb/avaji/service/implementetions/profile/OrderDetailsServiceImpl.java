package com.logiweb.avaji.service.implementetions.profile;


import com.logiweb.avaji.dao.CargoDAO;
import com.logiweb.avaji.dao.OrderDAO;
import com.logiweb.avaji.dao.OrderDetailsDAO;
import com.logiweb.avaji.dtos.*;
import com.logiweb.avaji.entity.enums.CargoStatus;
import com.logiweb.avaji.entity.enums.DriverStatus;
import com.logiweb.avaji.entity.enums.OrderStatus;
import com.logiweb.avaji.entity.enums.WaypointType;
import com.logiweb.avaji.entity.model.Cargo;
import com.logiweb.avaji.entity.model.Driver;
import com.logiweb.avaji.entity.model.OrderDetails;
import com.logiweb.avaji.entity.model.PastOrder;
import com.logiweb.avaji.service.api.mq.InformationProducerService;
import com.logiweb.avaji.service.api.path.PathDetailsService;
import com.logiweb.avaji.service.implementetions.utils.PathParser;
import com.logiweb.avaji.service.api.profile.OrderDetailsService;
import com.logiweb.avaji.service.api.profile.ShiftDetailsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
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
    private final InformationProducerService informationService;

    @Autowired
    public OrderDetailsServiceImpl(OrderDetailsDAO orderDetailsDAO, OrderDAO orderDAO, CargoDAO cargoDAO,
                                   PathParser parser, ShiftDetailsService shiftDetailsService,
                                   PathDetailsService pathDetailsService, InformationProducerService informationService) {
        this.orderDetailsDAO = orderDetailsDAO;
        this.orderDAO = orderDAO;
        this.cargoDAO = cargoDAO;
        this.parser = parser;
        this.shiftDetailsService = shiftDetailsService;
        this.pathDetailsService = pathDetailsService;
        this.informationService = informationService;
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

        List<WaypointDTO> waypoints = orderDAO.findWaypointsOfThisOrder(orderDetails.getId());
        orderDetails.setWaypoints(waypoints);

        setCargoAvailableForAction(orderDetails, waypoints);

        return orderDetails;
    }


    @Override
    @Transactional
    public void updateOrderByCargoStatus(long driverId, List<Long> cargoIds){
        if (cargoIds.isEmpty()) {
            return;
        }

        shiftDetailsService.changeShiftDetails(driverId, DriverStatus.LOAD_UNLOAD_WORK);

        updateCargo(cargoIds);

        long orderId = orderDetailsDAO.findOrderIdOfDriverId(driverId);

        if(orderIsComplete(orderId)) {
            createPastOrder(orderId, driverId);

            orderDAO.deleteOrder(orderId);

            logger.info("Order by id {} is completed", orderId);
            informationService.updateOrderInformation();

            informationService.sendInformation();
        }
    }

    private void createPastOrder(long orderId, long driverId) {
        OrderDetailsDTO orderDetails = orderDetailsDAO.findOrderDetails(driverId);
        List<Driver> drivers = orderDAO.findDriversEntitiesByOrderId(orderId);

        PastOrder pastOrder = new PastOrder(orderId, OrderStatus.COMPLETED,
                orderDetails.getPath(), orderDetails.getMaxCapacity(), orderDetails.getTruckId(),
                LocalDateTime.now(), drivers);
        orderDAO.savePastOrder(pastOrder);

        for(Driver driver : drivers) {
            shiftDetailsService.finishShift(driver.getId());
            driver.setCurrentTruck(null);
            driver.setOrderDetails(null);
            driver.getPastOrders().add(pastOrder);
        }

        orderDetailsDAO.freeTruck(orderDetails.getTruckId());

        orderDAO.savePastOrder(pastOrder);

        informationService.updateTruckInformation();
        informationService.updateDriverInformation();
    }

    @Override
    @Transactional
    public void changeCity(long driverId, long orderId) {
        OrderDetails orderDetails = orderDetailsDAO.findOrderDetailsEntity(orderId);

        shiftDetailsService.changeShiftDetails(driverId, DriverStatus.DRIVING);
        orderDAO.findDriversByOrderId(orderId).stream()
                .map(DriverDTO::getId)
                .filter(id -> id != driverId)
                .forEach(id -> shiftDetailsService.changeShiftDetails(id, DriverStatus.SECOND_DRIVER));


        List<Long> path = parser.parseStringToLongList(orderDetails.getRemainingPath());

        Long cityCode = path.get(1);
        path.remove(0);

        double newRemainingHours = pathDetailsService.getShiftHours(path);
        double workedHours = orderDetails.getRemainingWorkingTime() - newRemainingHours;
        shiftDetailsService.updateWorkedHours(orderId, workedHours);

        orderDetails.setRemainingPath(parser.parseLongListToString(path));
        orderDetails.setRemainingWorkingTime(newRemainingHours);

        orderDetailsDAO.updateOrderDetails(orderDetails, cityCode);

        informationService.updateDriverInformation();
        informationService.updateTruckInformation();

        informationService.sendInformation();
    }


    private void setCoDrivers(OrderDetailsDTO orderDetails, long driverId) {
        List<DriverDTO> coDrivers = orderDAO.findDriversByOrderId(orderDetails.getId())
                .stream().filter(d -> d.getId() != driverId)
                .collect(Collectors.toList());

        orderDetails.setCoDrivers(coDrivers);
    }

    private void setRemainingPath(OrderDetailsDTO orderDetails) {
        List<CityDTO> pathList = parser.pathStringToCityDTOList(orderDetails.getRemainingPathString());

        Deque<CityDTO> remainingPath = new ArrayDeque<>(pathList);

        orderDetails.setRemainingPath(remainingPath);

        setNextCity(orderDetails, pathList);
    }

    private void setCargoAvailableForAction(OrderDetailsDTO orderDetails, List<WaypointDTO> waypoints) {
        List<WaypointDTO> actionWaypoint = waypoints.stream()
                .filter(w -> w.getCityCode() == orderDetails.getRemainingPath().getFirst().getCityCode())
                .collect(Collectors.toList());

        List<Cargo> loadCargo = actionWaypoint.stream()
                .filter(w -> w.getCargoStatus() == CargoStatus.PREPARED && w.getType() == WaypointType.LOADING)
                .map(waypoint -> new Cargo(waypoint.getCargoId(), waypoint.getCargoTitle()))
                .collect(Collectors.toList());
        List<Cargo> unloadCargo = actionWaypoint.stream()
                .filter(w -> w.getCargoStatus() == CargoStatus.SHIPPED && w.getType() == WaypointType.UNLOADING)
                .map(waypoint -> new Cargo(waypoint.getCargoId(), waypoint.getCargoTitle()))
                .collect(Collectors.toList());

        orderDetails.setLoadCargo(loadCargo);
        orderDetails.setUnloadCargo(unloadCargo);
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
