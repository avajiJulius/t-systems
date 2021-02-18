package com.logiweb.avaji.service.implementetions.utils;

import com.logiweb.avaji.dao.CountryMapDAO;
import com.logiweb.avaji.dtos.*;
import com.logiweb.avaji.entity.enums.DriverStatus;
import com.logiweb.avaji.entity.enums.Role;
import com.logiweb.avaji.dao.CargoDAO;
import com.logiweb.avaji.entity.enums.WaypointType;
import com.logiweb.avaji.entity.model.Driver;
import com.logiweb.avaji.entity.model.Order;
import com.logiweb.avaji.entity.model.Truck;
import com.logiweb.avaji.entity.model.Waypoint;
import com.logiweb.avaji.service.api.map.CountryMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This service convert DTO to Entities and vice versa.
 *
 */

@Service
public class Mapper {

    private final CountryMapService countryMapService;
    private final CargoDAO cargoDAO;
    private final PasswordEncoder encoder;

    @Autowired
    public Mapper(CountryMapService countryMapService, CargoDAO cargoDAO,
                  PasswordEncoder encoder) {
        this.countryMapService = countryMapService;
        this.cargoDAO = cargoDAO;
        this.encoder = encoder;
    }

    /**
     * Convert TruckDTO to Truck entity.
     *
     * @param truckDto
     * @return truck entity
     */
    public Truck dtoToTruck(TruckDTO truckDto) {
        Truck truck = new Truck();
        truck.setTruckId(truckDto.getTruckId());
        truck.setVersion(truckDto.getVersion());
        truck.setShiftSize(truckDto.getShiftSize());
        truck.setCurrentCity(countryMapService.findCityByCode(truckDto.getCurrentCityCode()));
        truck.setCapacity(truckDto.getCapacity());
        truck.setServiceable(truckDto.isServiceable());
        return truck;
    }

    /**
     * Convert list of waypointDto to waypoint list
     *
     * @param dto CreateWaypointsDTO
     * @param order
     * @return list of waypoint entities.
     */
    public List<Waypoint> dtoToWaypoints(CreateWaypointsDTO dto, Order order) {
        List<Waypoint> waypoints = new ArrayList<>();
        List<WaypointDTO> waypointsDtos = dto.getWaypointsDto();
        return addWaypoints(waypoints, waypointsDtos, order);
    }

    private List<Waypoint> addWaypoints(List<Waypoint> waypoints, List<WaypointDTO> waypointsDtos, Order order) {
        for (WaypointDTO dto: waypointsDtos) {
            Waypoint load = new Waypoint(countryMapService.findCityByCode(dto.getLoadCityCode()),
                    WaypointType.LOADING, order, cargoDAO.findCargoById(dto.getCargoId()));
            Waypoint unload = new Waypoint(countryMapService.findCityByCode(dto.getUnloadCityCode()),
                    WaypointType.UNLOADING, order, cargoDAO.findCargoById(dto.getCargoId()));
            waypoints.add(load);
            waypoints.add(unload);
        }
        return waypoints;
    }


    public Driver createDriverFromDto(DriverDTO driverDTO) {
        return new Driver.Builder()
                .withVersion(0).withEmail(driverDTO.getEmail())
                .withPassword(encoder.encode(driverDTO.getPassword()))
                .withEnable(true).withRole(Role.DRIVER)
                .withFirstName(driverDTO.getFirstName())
                .withLastName(driverDTO.getLastName())
                .withHoursWorked(0.0).withDriverStatus(DriverStatus.REST)
                .withCurrentCity(countryMapService.findCityByCode(driverDTO.getCityCode()))
                .build();
    }

    public Driver updateDriverFromDto(Driver driver, DriverDTO driverDTO) {
        return new Driver.Builder(driver)
                .withFirstName(driverDTO.getFirstName())
                .withLastName(driverDTO.getLastName())
                .withHoursWorked(driverDTO.getHoursWorked())
                .withCurrentCity(countryMapService.findCityByCode(driverDTO.getCityCode()))
                .build();
    }
}
