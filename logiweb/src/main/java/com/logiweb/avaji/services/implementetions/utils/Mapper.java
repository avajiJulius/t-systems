package com.logiweb.avaji.services.implementetions.utils;

import com.logiweb.avaji.daos.CountryMapDAO;
import com.logiweb.avaji.dtos.*;
import com.logiweb.avaji.entities.enums.DriverStatus;
import com.logiweb.avaji.entities.enums.Role;
import com.logiweb.avaji.daos.CargoDAO;
import com.logiweb.avaji.entities.enums.WaypointType;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.entities.models.Waypoint;
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

    private final CountryMapDAO mapDAO;
    private final CargoDAO cargoDAO;
    private final PasswordEncoder encoder;

    @Autowired
    public Mapper(CountryMapDAO mapDAO, CargoDAO cargoDAO,
                  PasswordEncoder encoder) {
        this.mapDAO = mapDAO;
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
        truck.setCurrentCity(mapDAO.findCityByCode(truckDto.getCurrentCityCode()));
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
            Waypoint load = new Waypoint(mapDAO.findCityByCode(dto.getLoadCityCode()),
                    WaypointType.LOADING, order, cargoDAO.findCargoById(dto.getCargoId()));
            Waypoint unload = new Waypoint(mapDAO.findCityByCode(dto.getUnloadCityCode()),
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
                .withCurrentCity(mapDAO.findCityByCode(driverDTO.getCityCode()))
                .build();
    }

    public Driver updateDriverFromDto(Driver driver, DriverDTO driverDTO) {
        return new Driver.Builder(driver)
                .withFirstName(driverDTO.getFirstName())
                .withLastName(driverDTO.getLastName())
                .withHoursWorked(driverDTO.getHoursWorked())
                .withCurrentCity(mapDAO.findCityByCode(driverDTO.getCityCode()))
                .build();
    }
}
