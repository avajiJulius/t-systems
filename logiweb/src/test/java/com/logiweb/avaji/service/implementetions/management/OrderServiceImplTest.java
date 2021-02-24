package com.logiweb.avaji.service.implementetions.management;

import com.logiweb.avaji.config.TestConfig;
import com.logiweb.avaji.dao.OrderDAO;
import com.logiweb.avaji.dtos.CreateWaypointsDTO;
import com.logiweb.avaji.dtos.DriverDTO;
import com.logiweb.avaji.dtos.TruckDTO;
import com.logiweb.avaji.dtos.WaypointDTO;
import com.logiweb.avaji.entity.model.Order;
import com.logiweb.avaji.service.api.management.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringJUnitConfig(TestConfig.class)
@ActiveProfiles("test")
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDAO orderDAO;

    @Test
    void givenOrderID_whenTwoTruckSuits_thenReturnedTrucksSizeEqualsTwo() {
        List<TruckDTO> trucks = orderService.readTrucksForOrder(4);

        assertEquals(2, trucks.size());
    }

    @Test
    void givenOrderID_whenOneTruckSuits_thenReturnedTrucksSizeEqualsOne() {
        List<TruckDTO> trucks = orderService.readTrucksForOrder(5);

        assertEquals(1, trucks.size());
    }

    @Test
    void givenCreateWaypointDTO_thenOrderCreatedAndIDEqualsExpected() {
        CreateWaypointsDTO dto = new CreateWaypointsDTO();
        WaypointDTO waypointDTO = new WaypointDTO(1, 2, 1, 10);
        dto.addWaypointDto(waypointDTO);

        orderService.createOrderByWaypoints(new Order(), dto);
        Order result = orderDAO.findOrderById(6);

        assertEquals(6, result.getId());
    }

    @Test
    void givenOrderID_whenThreeDriversSuits_thenReturnedDriversSizeEqualsThree() {
        List<DriverDTO> drivers = orderService.readDriversForOrder(1);

        assertEquals(3, drivers.size());
    }

}