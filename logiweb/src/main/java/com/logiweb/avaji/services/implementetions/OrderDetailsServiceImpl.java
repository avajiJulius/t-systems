package com.logiweb.avaji.services.implementetions;


import com.logiweb.avaji.daos.CargoDAO;
import com.logiweb.avaji.daos.OrderDAO;
import com.logiweb.avaji.daos.OrderDetailsDAO;
import com.logiweb.avaji.dtos.CityDTO;
import com.logiweb.avaji.dtos.DriverDTO;
import com.logiweb.avaji.dtos.OrderDetailsDTO;
import com.logiweb.avaji.entities.enums.CargoStatus;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.OrderDetails;
import com.logiweb.avaji.parser.PathStringParser;
import com.logiweb.avaji.services.api.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final OrderDetailsDAO orderDetailsDAO;
    private final OrderDAO orderDAO;
    private final PathStringParser parser;
    private final CargoDAO cargoDAO;

    @Autowired
    public OrderDetailsServiceImpl(OrderDetailsDAO orderDetailsDAO, OrderDAO orderDAO,
                                   PathStringParser parser, CargoDAO cargoDAO) {
        this.orderDetailsDAO = orderDetailsDAO;
        this.orderDAO = orderDAO;
        this.parser = parser;
        this.cargoDAO = cargoDAO;
    }

    @Override
    public OrderDetailsDTO readOrderDetails(long driverId) {
        OrderDetailsDTO orderDetails = orderDetailsDAO.findOrderDetails(driverId);

        List<DriverDTO> coDrivers = orderDetailsDAO.findDriversByOrderId(orderDetails.getId())
                .stream().filter(d -> d.getId() != driverId).collect(Collectors.toList());
        orderDetails.setCoDrivers(coDrivers);

        Deque<CityDTO> remainingPath = new ArrayDeque<>();
        List<CityDTO> pathList = parser.pathStringToCityDTOList(orderDetails.getRemainingPathString());
        remainingPath.addAll(pathList);
        orderDetails.setRemainingPath(remainingPath);


        if (pathList.size() > 1) {
            orderDetails.setNextCity(pathList.get(1));
        } else {
            orderDetails.setNextCity(new CityDTO(-1L, "No city left"));
        }

        boolean completed = cargoDAO.findCargoByOrderId(orderDetails.getId())
                .stream().allMatch(cargo -> cargo.getCargoStatus() == CargoStatus.DELIVERED);
        orderDetails.setCompleted(completed);

        orderDetails.setPrettyPath(parser.toPrettyPath(orderDetails.getPath()));
        orderDetails.setWaypoints(orderDAO.findWaypointsOfThisOrder(orderDetails.getId()));

        return orderDetails;
    }

    @Override
    public void updateOrderByCargoStatus(long orderId, List<Long> cargoIds) {

        for(long id : cargoIds) {
            Cargo cargo = cargoDAO.findCargoById(id);
            int index = cargo.getCargoStatus().ordinal() + 1;
            cargo.setCargoStatus(CargoStatus.values()[index]);
            cargoDAO.updateCargo(cargo);
        }

        boolean orderCompleted = cargoDAO.findCargoByOrderId(orderId)
                .stream().allMatch(cargo -> cargo.getCargoStatus() == CargoStatus.DELIVERED);

        if(orderCompleted) {
            orderDetailsDAO.updateOnCompletedOrder(orderId);
        }
    }

    @Override
    public void changeCity(long orderId) {
        OrderDetails orderDetails = orderDetailsDAO.findOrderDetailsEntity(orderId);
        orderDetails.setRemainingPath(orderDetails.getRemainingPath().substring(2));
        orderDetailsDAO.updateOrderDetails(orderDetails);
    }
}
