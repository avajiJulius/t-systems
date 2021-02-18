package com.logiweb.avaji.service.implementetions.profile;

import com.logiweb.avaji.config.TestConfig;
import com.logiweb.avaji.dao.ShiftDetailsDAO;
import com.logiweb.avaji.dtos.ShiftDetailsDTO;
import com.logiweb.avaji.entity.enums.DriverStatus;
import com.logiweb.avaji.exception.ShiftValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(TestConfig.class)
@ActiveProfiles("test")
class ShiftDetailsServiceTest {

    @Autowired
    public ShiftDetailsDAO shiftDetailsDAO;

    @Autowired
    private ShiftDetailsServiceImpl shiftDetailsService;


    @Test
    void givenRestDriverStatus_WhenShiftActiveTrue_ThenItShouldReturnFalse() {
        boolean result = shiftDetailsService.shiftAndDriverStatusValid(true, DriverStatus.REST);

        assertFalse(result);
    }


    @Test
    void givenRestDriverStatus_WhenShiftActiveFalse_ThenItShouldReturnTrue() {
        boolean result = shiftDetailsService.shiftAndDriverStatusValid(false, DriverStatus.REST);

        assertTrue(result);
    }

    @Test
    void givenDrivingDriverStatus_WhenShiftActiveTrue_ThenItShouldReturnTrue() {
        boolean result = shiftDetailsService.shiftAndDriverStatusValid(true, DriverStatus.SECOND_DRIVER);

        assertTrue(result);
    }

    @Test
    void givenDrivingDriverStatus_WhenShiftActiveFalse_ThenItShouldReturnFalse() {
        boolean result = shiftDetailsService.shiftAndDriverStatusValid(false, DriverStatus.LOAD_UNLOAD_WORK);

        assertFalse(result);
    }

    @Test
    void thenUpdateShiftDetailsThenReturnUpdatedDetailsValues1() throws ShiftValidationException {
        ShiftDetailsDTO shiftDetails = shiftDetailsService.changeShiftDetails(1 , DriverStatus.DRIVING);

        assertEquals(true, shiftDetails.isShiftActive(), "Shift active change check");
        assertEquals(DriverStatus.DRIVING, shiftDetails.getDriverStatus(), "Driver status change check");
    }

    @Test
    void thenUpdateShiftDetailsThenReturnUpdatedDetailsValues2() throws ShiftValidationException {
        ShiftDetailsDTO shiftDetails = shiftDetailsService.changeShiftDetails(2, DriverStatus.REST);

        assertEquals(false, shiftDetails.isShiftActive(), "Shift active change check");
        assertEquals(DriverStatus.REST, shiftDetails.getDriverStatus(), "Driver status change check");
    }


}