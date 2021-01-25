package com.logiweb.avaji.services.implementetions.profile;

import com.logiweb.avaji.daos.ShiftDetailsDAO;
import com.logiweb.avaji.dtos.ShiftDetailsDTO;
import com.logiweb.avaji.entities.enums.DriverStatus;
import com.logiweb.avaji.exceptions.ShiftValidationException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(ShiftDetailsServiceTest.Config.class)
class ShiftDetailsServiceTest {

    List<ShiftDetailsDTO> shiftDetailsList = Stream.of(
            new ShiftDetailsDTO(1, DriverStatus.REST, false, null, null, 20),
            new ShiftDetailsDTO(2, DriverStatus.DRIVING, true,
                    LocalDateTime.of(2021,1, 12, 7,0), null, 20)
    ).collect(Collectors.toList());

    private ShiftDetailsDTO noConflict1 = new ShiftDetailsDTO(1, DriverStatus.SECOND_DRIVER, true ,null, null, 0);
    private ShiftDetailsDTO noConflict2 = new ShiftDetailsDTO(2, DriverStatus.REST, false ,null, null, 0);
    private ShiftDetailsDTO conflict1 = new ShiftDetailsDTO(1, DriverStatus.REST, true ,null, null, 0);
    private ShiftDetailsDTO conflict2 = new ShiftDetailsDTO(1, DriverStatus.SECOND_DRIVER, false ,null, null, 0);



    @Configuration
    static class Config {

        @Bean
        public EntityManagerFactory entityManagerFactory() {
            return Mockito.mock(EntityManagerFactory.class);
        }

        @Bean
        public ShiftDetailsDAO shiftDetailsDAO() {
            return Mockito.mock(ShiftDetailsDAO.class);
        }

        @Bean
        public ShiftDetailsServiceImpl shiftDetailsService() {
            return new ShiftDetailsServiceImpl(shiftDetailsDAO());
        }
    }


    @Autowired
    public ShiftDetailsDAO shiftDetailsDAO;

    @Autowired
    private ShiftDetailsServiceImpl shiftDetailsService;


    @Test
    void givenRestDriverStatus_WhenShiftActiveTrue_ThenItShouldReturnFalse() {
        boolean result = shiftDetailsService.validateShiftAndDriverStatus(true, DriverStatus.REST);

        assertFalse(result);
    }


    @Test
    void givenRestDriverStatus_WhenShiftActiveFalse_ThenItShouldReturnTrue() {
        boolean result = shiftDetailsService.validateShiftAndDriverStatus(false, DriverStatus.REST);

        assertTrue(result);
    }

    @Test
    void givenDrivingDriverStatus_WhenShiftActiveTrue_ThenItShouldReturnTrue() {
        boolean result = shiftDetailsService.validateShiftAndDriverStatus(true, DriverStatus.SECOND_DRIVER);

        assertTrue(result);
    }

    @Test
    void givenDrivingDriverStatus_WhenShiftActiveFalse_ThenItShouldReturnFalse() {
        boolean result = shiftDetailsService.validateShiftAndDriverStatus(false, DriverStatus.LOAD_UNLOAD_WORK);

        assertFalse(result);
    }

    @Test
    void thenUpdateShiftDetailsToNoConflictDTOThenReturnUpdatedDetailsValues1() throws ShiftValidationException {
        Mockito.when(shiftDetailsDAO.findShiftDetails(1))
                .thenReturn(shiftDetailsList.get(0));

        ShiftDetailsDTO shiftDetails = shiftDetailsService.updateShiftDetails(noConflict1);
        assertEquals(noConflict1.isShiftActive(), shiftDetails.isShiftActive(), "Shift active change check");
        assertEquals(noConflict1.getDriverStatus(), shiftDetails.getDriverStatus(), "Driver status change check");
    }

    @Test
    void thenUpdateShiftDetailsToNoConflictDTOThenReturnUpdatedDetailsValues2() throws ShiftValidationException {
        Mockito.when(shiftDetailsDAO.findShiftDetails(2))
                .thenReturn(shiftDetailsList.get(1));

        ShiftDetailsDTO shiftDetails = shiftDetailsService.updateShiftDetails(noConflict2);

        assertEquals(noConflict2.isShiftActive(), shiftDetails.isShiftActive(), "Shift active change check");
        assertEquals(noConflict2.getDriverStatus(), shiftDetails.getDriverStatus(), "Driver status change check");
    }

    @Test
    void thenUpdateShiftDetailsToConflictDTOThenThrowShiftValidationException1() {
        Mockito.when(shiftDetailsDAO.findShiftDetails(1))
                .thenReturn(shiftDetailsList.get(0));

        assertThrows(ShiftValidationException.class, () -> shiftDetailsService.updateShiftDetails(conflict1));
    }

    @Test
    void thenUpdateShiftDetailsToConflictDTOThenThrowShiftValidationException2() {
        Mockito.when(shiftDetailsDAO.findShiftDetails(2))
                .thenReturn(shiftDetailsList.get(1));

        assertThrows(ShiftValidationException.class, () -> shiftDetailsService.updateShiftDetails(conflict2));
    }

}