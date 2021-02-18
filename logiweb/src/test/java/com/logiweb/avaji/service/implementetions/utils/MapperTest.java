package com.logiweb.avaji.service.implementetions.utils;


import com.logiweb.avaji.config.TestConfig;
import com.logiweb.avaji.dtos.DriverDTO;
import com.logiweb.avaji.dtos.TruckDTO;
import com.logiweb.avaji.entity.enums.DriverStatus;
import com.logiweb.avaji.entity.model.City;
import com.logiweb.avaji.entity.model.Driver;
import com.logiweb.avaji.entity.model.Truck;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(TestConfig.class)
@ActiveProfiles("test")
class MapperTest {

    @Autowired
    public Mapper mapper;

    @Test
    void whenGiveTruckDTO_thenReturnCorrectTruckEntity() {
        Truck expected = new Truck("MD12312", 0, 2, 20, true, false, new City(2L, "B"), null, null);
        TruckDTO dto = new TruckDTO("MD12312", 20, 2, true, 2L, "B", false);

        Truck result = mapper.dtoToTruck(dto);

        assertEquals(expected.toString(), result.toString());
    }

    @Test
    void whenGivenDriverDTO_thenReturnCorrectDriverEntity() {
        DriverDTO dto = new DriverDTO("one@gmail.com", "driver", "Alex", "Matushkin", 1L);
        Driver expected = new Driver.Builder()
                .withFirstName("Alex").withLastName("Matushkin")
                .withHoursWorked(0.0).withDriverStatus(DriverStatus.REST)
                .withCurrentCity(new City(1, "A"))
                .build();

        Driver result = mapper.createDriverFromDto(dto);

        assertEquals(expected, result);
    }

}