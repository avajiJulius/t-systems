package com.logiweb.avaji.service.implementetions.profile;

import com.logiweb.avaji.config.TestConfig;
import com.logiweb.avaji.dao.CargoDAO;
import com.logiweb.avaji.dao.OrderDetailsDAO;
import com.logiweb.avaji.dao.ShiftDetailsDAO;
import com.logiweb.avaji.dtos.OrderDetailsDTO;
import com.logiweb.avaji.dtos.ShiftDetailsDTO;
import com.logiweb.avaji.entity.enums.CargoStatus;
import com.logiweb.avaji.entity.enums.DriverStatus;
import com.logiweb.avaji.entity.model.OrderDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

@SpringJUnitConfig(TestConfig.class)
@ActiveProfiles("test")
class OrderDetailsServiceTest {

    @Autowired
    public OrderDetailsServiceImpl orderDetailsService;

    @Autowired
    public ShiftDetailsDAO shiftDetailsDAO;

    @Autowired
    public CargoDAO cargoDAO;

    @Autowired
    public OrderDetailsDAO orderDetailsDAO;

    @Test
    void givenDriverId_whenOrderDetailsExist_thenReturnedDTOBeEqualsToExpectedDTO() {
        OrderDetailsDTO expected =  new OrderDetailsDTO(1, "MN12121", "1-2-3-4-", "2-3-5-", 10.0, 5.0);
        OrderDetailsDTO result = orderDetailsService.readOrderDetails(1);

        assertEquals(expected.getTruckId(), result.getTruckId());
        assertEquals(expected.getPath(), result.getPath());
        assertEquals(expected.getRemainingPathString(), result.getRemainingPathString());
    }


    @Test
    void givenDriverId_whenOrderDetailsNotExist_thenReturnNull() {
        OrderDetailsDTO result = orderDetailsService.readOrderDetails(4);

        assertNull(result);
    }

    @Test
    void givenDriverIDAndCargoIDs_whenOrderNotCompleted_thenShiftDetailsStatusAndCargoStatusEqualsToUpdatedValues() {
        orderDetailsService.updateOrderByCargoStatus(1L, Stream.of(1L).collect(Collectors.toList()));
        ShiftDetailsDTO result = shiftDetailsDAO.findShiftDetails(1);

        assertEquals(DriverStatus.LOAD_UNLOAD_WORK, result.getDriverStatus());
        assertEquals(CargoStatus.SHIPPED, cargoDAO.findCargoById(1L).getCargoStatus());
    }

    @Test
    void givenDriverIDAndCargoIDs_whenOrderNotCompleted_thenShiftDetailsStatusAndCargoStatusNotEqualsToPast() {
        orderDetailsService.updateOrderByCargoStatus(1L, Stream.of(2L).collect(Collectors.toList()));
        ShiftDetailsDTO result = shiftDetailsDAO.findShiftDetails(1);

        assertNotEquals(DriverStatus.REST, result.getDriverStatus());
        assertNotEquals(CargoStatus.PREPARED, cargoDAO.findCargoById(2L).getCargoStatus());
    }


    @Test
    void givenDriverIDAndCargoIDs_whenOrderCompleted_thenShiftDetailsStatusAndCargoStatusEqualsToUpdatedValues() {
        orderDetailsService.updateOrderByCargoStatus(1L, Stream.of(5L).collect(Collectors.toList()));
        ShiftDetailsDTO result = shiftDetailsDAO.findShiftDetails(1);

        assertNotEquals(DriverStatus.REST, result.getDriverStatus());
        assertNotEquals(CargoStatus.DELIVERED, cargoDAO.findCargoById(1L).getCargoStatus());
    }

    @Test
    void givenDriverIDAndOrderID_thenRemainingPathAndRemainingTimeEqualsNewValues() {
        orderDetailsService.changeCity(1, 1);
        OrderDetails orderDetailsEntity = orderDetailsDAO.findOrderDetailsEntity(1);

        assertEquals("3-5-", orderDetailsEntity.getRemainingPath());
        assertEquals(2, orderDetailsEntity.getRemainingWorkingTime());
    }


    @Test
    void givenDriverIDAndOrderID_thenRemainingPathAndTimeNotEqualsPastValues() {
        orderDetailsService.changeCity(1, 2);
        OrderDetails orderDetailsEntity = orderDetailsDAO.findOrderDetailsEntity(2);

        assertNotEquals("3-5-", orderDetailsEntity.getRemainingPath());
        assertEquals("5-", orderDetailsEntity.getRemainingPath());
        assertNotEquals(2, orderDetailsEntity.getRemainingWorkingTime());
        assertEquals(0, orderDetailsEntity.getRemainingWorkingTime());
    }


    @Test
    void whenAllCargoStatusAreDeliveredReturnTrue() {
        boolean result = orderDetailsService.orderIsComplete(4);

        assertTrue(result);
    }

    @Test
    void whenNotAllCargoStatusAreDeliveredReturnFalse() {
        boolean result = orderDetailsService.orderIsComplete(3);

        assertFalse(result);
    }


}