package com.logiweb.avaji.services.implementetions;

import com.logiweb.avaji.dao.*;
import com.logiweb.avaji.entities.dto.*;
import com.logiweb.avaji.entities.enums.WaypointType;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This service convert DTO to Entities and vice versa.
 *
 */

@Service
public class DtoConverter {

    private final CountryMapDAO mapDAO;
    private final OrderDAO orderDAO;
    private final CargoDAO cargoDAO;
    private final DriverDAO driverDAO;
    private final WaypointDAO waypointDAO;

    @Autowired
    public DtoConverter(CountryMapDAO mapDAO, OrderDAO orderDAO, 
                        CargoDAO cargoDAO, DriverDAO driverDAO, WaypointDAO waypointDAO) {
        this.mapDAO = mapDAO;
        this.orderDAO = orderDAO;
        this.cargoDAO = cargoDAO;
        this.driverDAO = driverDAO;
        this.waypointDAO = waypointDAO;
    }

    /**
     * Convert list of city to cityDto
     *
     * @param cities
     * @return cityDto list
     */
    public List<CityDto> citiesToDtos(List<City> cities) {
        List<CityDto> dtos = new ArrayList<>();
        for(City city: cities) {
            dtos.add(new CityDto(city.getCityCode(), city.getCityName()));
        }
        return dtos;
    }

    /**
     * Convert cityDto to City entity.
     *
     * @param cityDto
     * @return city entity.
     */
    public City dtoToCity(CityDto cityDto) {
        City city = new City();
        city.setCityCode(cityDto.getCityCode());
        city.setCityName(cityDto.getCityName());
        return city;
    }

    /**
     * Convert TruckDto to Truck entity.
     *
     * @param truckDto
     * @return truck entity
     */
    public Truck dtoToTruck(TruckDto truckDto) {
        Truck truck = new Truck();
        truck.setTruckId(truckDto.getTruckId());
        truck.setCurrentCity(mapDAO.findCityByCode(truckDto.getCurrentCityCode()));
        truck.setCapacity(truckDto.getCapacity());
        truck.setServiceable(truckDto.isServiceable());
        return truck;
    }

    /**
     * Convert Truck entity to TruckDto
     *
     * @param truck
     * @return truckDto
     */
    public TruckDto truckToDto(Truck truck) {
        return new TruckDto(truck.getTruckId(), truck.getCapacity(),
                truck.isServiceable(), truck.getCurrentCity().getCityCode());
    }


    /**
     * Convert list of Truck entities to truckDto list.
     *
     * @param trucks
     * @return truckDto list
     */
    public List<TruckDto> trucksToDtos(List<Truck> trucks) {
        List<TruckDto> dtos = new ArrayList<>();
        for(Truck truck: trucks) {
            dtos.add(truckToDto(truck));
        }
        return dtos;
    }


    /**
     * Convert list of Driver entities to DriverPublicResponseDto.
     *
     * @param allDrivers
     * @return DriverPublicResponseDto
     * @see DriverPublicResponseDto
     */
    public List<DriverPublicResponseDto> driversToDtos(List<Driver> allDrivers) {
        List<DriverPublicResponseDto> dtos = new ArrayList<>();
        for(Driver driver: allDrivers) {
            dtos.add(new DriverPublicResponseDto(driver.getDriverId(), driver.getFirstName(),
                    driver.getLastName(), driver.getHoursWorked(), driver.getDriverStatus().name(),
                    driver.getCurrentCity(), driver.getCurrentTruck()));
        }
        return dtos;
    }

    /**
     * Convert waypointDto to Waypoint entity.
     *
     * @param dto
     * @param order
     * @return waypoint entity.
     */
    public Waypoint dtoToWaypoint(WaypointDto dto, Order order) {
        WaypointType type;
        if (dto.getType().equals("LOADING")) {
            type = WaypointType.LOADING;
        } else {
            type = WaypointType.UNLOADING;
        }
        return new Waypoint(mapDAO.findCityByCode(dto.getCityCode()),type, order, cargoDAO.findCargoById(dto.getCargoId()));
    }

    /**
     * Convert list of waypointDto to waypoint list
     *
     * @param waypointDtos
     * @param order
     * @return list of waypoint entities.
     */
    public List<Waypoint> dtosToWaypoints(List<WaypointDto> waypointDtos, Order order) {
        List<Waypoint> waypoints = new ArrayList<>();
        for(WaypointDto waypointDto: waypointDtos) {
            waypoints.add(dtoToWaypoint(waypointDto, order));
        }
        return waypoints;
    }

    /**
     * Convert driver to DriverPrivateResponseDto
     * 
     * @param driver
     * @return DriverPrivateResponseDto
     */
    public DriverPrivateResponseDto driverToDto(Driver driver) {
        String truckId = driver.getCurrentTruck().getTruckId();
        List<Driver> soDrivers = driverDAO.findDriversByTruckId(truckId);
        Order order = orderDAO.findOrderByTruckId(truckId);
        List<Waypoint> waypoints = waypointDAO.findWaypointsOfThisOrder(order.getOrderId());
        List<Optional<Integer>> coDriversIds = soDrivers.stream().map(d -> d.getDriverId())
                .filter(i -> i != driver.getDriverId()).map(Optional::ofNullable).collect(Collectors.toList());
        return new DriverPrivateResponseDto(driver.getDriverId(), coDriversIds,
                truckId, order.getOrderId(), waypointsToDtos(waypoints));
    }

    private List<WaypointDto> waypointsToDtos(List<Waypoint> waypoints) {
        List<WaypointDto> dtos = new ArrayList<>();
        for(Waypoint waypoint: waypoints) {
            dtos.add(waypointToDto(waypoint));
        }
        return dtos;
    }

    private WaypointDto waypointToDto(Waypoint waypoint) {
        String type;
        if(waypoint.getWaypointType() == WaypointType.LOADING) {
            type = "LOADING";
        } else {
            type = "UNLOADING";
        }
        return new WaypointDto(waypoint.getWaypointId(), type, waypoint.getWaypointCargo().getCargoId());
    }
}
