package com.logiweb.avaji.service.implementetions.profile;

import com.logiweb.avaji.dao.ShiftDetailsDAO;
import com.logiweb.avaji.dtos.ShiftDetailsDTO;
import com.logiweb.avaji.entity.enums.DriverStatus;
import com.logiweb.avaji.exception.ShiftValidationException;
import com.logiweb.avaji.service.api.mq.InformationProducerService;
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

        public InformationProducerService informationProducerService() {
            return Mockito.mock(InformationProducerService.class);
        }

        @Bean
        public ShiftDetailsServiceImpl shiftDetailsService() {
            return new ShiftDetailsServiceImpl(shiftDetailsDAO(), informationProducerService());
        }
    }


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
        Mockito.when(shiftDetailsDAO.findShiftDetails(1))
                .thenReturn(shiftDetailsList.get(0));

        ShiftDetailsDTO shiftDetails = shiftDetailsService.changeShiftDetails(1 , DriverStatus.DRIVING);
        assertEquals(true, shiftDetails.isShiftActive(), "Shift active change check");
        assertEquals(DriverStatus.DRIVING, shiftDetails.getDriverStatus(), "Driver status change check");
    }

    @Test
    void thenUpdateShiftDetailsThenReturnUpdatedDetailsValues2() throws ShiftValidationException {
        Mockito.when(shiftDetailsDAO.findShiftDetails(2))
                .thenReturn(shiftDetailsList.get(1));

        ShiftDetailsDTO shiftDetails = shiftDetailsService.changeShiftDetails(2, DriverStatus.REST);

        assertEquals(false, shiftDetails.isShiftActive(), "Shift active change check");
        assertEquals(DriverStatus.REST, shiftDetails.getDriverStatus(), "Driver status change check");
    }

}