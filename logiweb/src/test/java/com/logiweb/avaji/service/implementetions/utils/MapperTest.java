package com.logiweb.avaji.service.implementetions.utils;


import com.logiweb.avaji.config.TestConfig;
import com.logiweb.avaji.dao.CargoDAO;
import com.logiweb.avaji.dtos.CreateWaypointsDTO;
import com.logiweb.avaji.dtos.DriverDTO;
import com.logiweb.avaji.dtos.TruckDTO;
import com.logiweb.avaji.dtos.WaypointDTO;
import com.logiweb.avaji.entity.enums.DriverStatus;
import com.logiweb.avaji.entity.enums.OrderStatus;
import com.logiweb.avaji.entity.enums.WaypointType;
import com.logiweb.avaji.entity.model.*;
import com.logiweb.avaji.service.api.map.CountryMapService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(TestConfig.class)
@ActiveProfiles("test")
class MapperTest {

    @Autowired
    public Mapper mapper;

    @Autowired
    public CargoDAO cargoDAO;
    @Autowired
    public CountryMapService countryMapService;

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

    @Test
    void whenGivenDriverDTO_thenReturnUpdatedDriverEntity() {
        DriverDTO dto = new DriverDTO("UpdatedName", "UpdatedSurname", 100.0, 2);
        Driver driver = new Driver.Builder()
                .withFirstName("Alex").withLastName("Matushkin")
                .withHoursWorked(0.0).withDriverStatus(DriverStatus.REST)
                .withCurrentCity(new City(1, "A"))
                .build();


        Driver expected = new Driver.Builder()
                .withFirstName("UpdatedName").withLastName("UpdatedSurname")
                .withHoursWorked(100.0).withDriverStatus(DriverStatus.REST)
                .withCurrentCity(new City(2, "B"))
                .build();
        Driver result = mapper.updateDriverFromDto(driver, dto);

        assertEquals(expected, result);
    }

    @Test
    void whenGivenWaypointDTOWithOrder_thenReturnWaypointEntity() {
        LocalDateTime now = LocalDateTime.now();
        Order order = new Order(OrderStatus.UNCOMPLETED, "1-2-3-", new Truck(), 31.2, now);
        CreateWaypointsDTO dto = new CreateWaypointsDTO();

        for (int i = 1; i < 4; i++) {
            int index = i;
            dto.addWaypointDto(new WaypointDTO(index, index + 1, index, 10.0));
        }

        List<Waypoint> expected = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            int index = i;
            expected.add(new Waypoint(countryMapService.findCityByCode(index),
                    WaypointType.LOADING, order, cargoDAO.findCargoById(index)));
            expected.add(new Waypoint(countryMapService.findCityByCode(index+1),
                    WaypointType.UNLOADING, order, cargoDAO.findCargoById(index)));
        }
        List<Waypoint> result = mapper.dtoToWaypoints(dto, order);

        assertEquals(expected.size(), result.size());
        assertEquals(expected, result);
    }

}