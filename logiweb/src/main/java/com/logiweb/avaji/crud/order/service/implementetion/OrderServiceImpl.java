package com.logiweb.avaji.crud.order.service.implementetion;

import com.logiweb.avaji.crud.driver.dao.DriverDAO;
import com.logiweb.avaji.crud.order.dao.OrderDAO;
import com.logiweb.avaji.crud.order.dto.OrderDto;
import com.logiweb.avaji.crud.truck.dao.TruckDAO;
import com.logiweb.avaji.crud.order.dto.WaypointDto;
import com.logiweb.avaji.entities.enums.WaypointType;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import com.logiweb.avaji.exceptions.CityValidateException;
import com.logiweb.avaji.exceptions.LoadAndUnloadValidateException;
import com.logiweb.avaji.crud.order.service.api.OrderService;
import com.logiweb.avaji.orderdetails.service.implementetion.ShiftDetailsServiceImpl;
import com.logiweb.avaji.dtoconverter.DtoConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    private final OrderDAO orderDAO;
    private final TruckDAO truckDAO;
    private final DriverDAO driverDAO;
    private final DtoConverter converter;
    private final ShiftDetailsServiceImpl computingService;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, TruckDAO truckDAO, DriverDAO driverDAO,
                            DtoConverter converter, ShiftDetailsServiceImpl computingService) {
        this.orderDAO = orderDAO;
        this.truckDAO = truckDAO;
        this.driverDAO = driverDAO;
        this.converter = converter;
        this.computingService = computingService;
    }

    @Override
    @Transactional
    public List<OrderDto> readAllOrders() {
        List<Order> allOrders = orderDAO.findAllOrders();
        return converter.ordersToDtos(allOrders);
    }


    @Override
    public void createOrderByWaypoints(Order order, List<WaypointDto> waypointsDto) throws CityValidateException, LoadAndUnloadValidateException {
        List<Waypoint> waypoints = converter.dtosToWaypoints(waypointsDto, order);
        order.setWaypoints(waypoints);
        if(validateOrderByWaypoints(waypoints)) {
            orderDAO.saveOrder(order);
        }
    }

    @Override
    public void deleteOrder(long orderId) {
        orderDAO.deleteOrder(orderId);
    }





    private boolean  validateOrderByWaypoints(List<Waypoint> waypoints) throws LoadAndUnloadValidateException, CityValidateException {
        int count = 0;
        Map<Cargo,City> load = waypoints.stream()
                .filter(w -> w.getWaypointType() == WaypointType.LOADING)
                .collect(Collectors.toMap(Waypoint::getWaypointCargo ,
                        Waypoint::getWaypointCity));
        Map<Cargo, City> unload = waypoints.stream()
                .filter(w -> w.getWaypointType() == WaypointType.UNLOADING)
                .collect(Collectors.toMap(Waypoint::getWaypointCargo ,
                        Waypoint::getWaypointCity));
        if(load.size() != unload.size()) {
            logger.error("Wrong number of load or unload points");
            throw new LoadAndUnloadValidateException("Wrong number of load or unload points");
        }
        Set<Cargo> cargos = load.keySet();
        for (Cargo cargo : cargos) {
            if(load.get(cargo) != unload.get(cargo)) {
                count++;
            }
        }
        if(cargos.size() == count) {
            return true;
        }
        logger.error("City validation is failed");
        throw new CityValidateException("City validation is failed");
    }

}
