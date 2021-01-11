package com.logiweb.avaji.mapper;

import com.logiweb.avaji.crud.countrymap.dao.CountryMapDAO;
import com.logiweb.avaji.crud.countrymap.dto.CityDto;
import com.logiweb.avaji.crud.driver.dto.DriverDTO;
import com.logiweb.avaji.crud.order.dto.CreateWaypointsDTO;
import com.logiweb.avaji.crud.order.dto.OrderDTO;
import com.logiweb.avaji.crud.truck.dto.TruckDTO;
import com.logiweb.avaji.crud.workdetails.dto.WorkDetailsDTO;
import com.logiweb.avaji.entities.enums.DriverStatus;
import com.logiweb.avaji.entities.enums.Role;
import com.logiweb.avaji.entities.models.utils.WorkDetails;
import com.logiweb.avaji.orderdetails.dao.OrderDetailsDAO;
import com.logiweb.avaji.crud.cargo.dao.CargoDAO;
import com.logiweb.avaji.crud.driver.dao.DriverDAO;
import com.logiweb.avaji.crud.order.dao.OrderDAO;
import com.logiweb.avaji.crud.order.dto.WaypointDTO;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This service convert DTO to Entities and vice versa.
 *
 */

@Service
public class Mapper {

    @Autowired
    private PasswordEncoder encoder;

    private final CountryMapDAO mapDAO;
    private final OrderDAO orderDAO;
    private final CargoDAO cargoDAO;
    private final DriverDAO driverDAO;
    private final OrderDetailsDAO orderDetailsDAO;

    @Autowired
    public Mapper(CountryMapDAO mapDAO, OrderDAO orderDAO,
                  CargoDAO cargoDAO, DriverDAO driverDAO, OrderDetailsDAO orderDetailsDAO) {
        this.mapDAO = mapDAO;
        this.orderDAO = orderDAO;
        this.cargoDAO = cargoDAO;
        this.driverDAO = driverDAO;
        this.orderDetailsDAO = orderDetailsDAO;
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
     * Convert TruckDTO to Truck entity.
     *
     * @param truckDto
     * @return truck entity
     */
    public Truck dtoToTruck(TruckDTO truckDto) {
        Truck truck = new Truck();
        truck.setTruckId(truckDto.getTruckId());
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
        for(WaypointDTO waypointDto: waypointsDtos) {
            waypoints.add(dtoToWaypoint(waypointDto, order));
        }
        return waypoints;
    }

    /**
     * Convert waypointDto to Waypoint entity.
     *
     * @param dto
     * @param order
     * @return waypoint entity.
     */
    public Waypoint dtoToWaypoint(WaypointDTO dto, Order order) {
        return new Waypoint(mapDAO.findCityByCode(dto.getCityCode()), dto.getType(), order, cargoDAO.findCargoById(dto.getCargoId()));
    }


    public Driver createDriverFromDto(DriverDTO driverDTO) {
        Driver driver = new Driver();
        driver.setEmail(driverDTO.getEmail());
        driver.setPassword(encoder.encode(driverDTO.getPassword()));
        driver.setEnable(true);
        driver.setRole(Role.DRIVER);
        driver.setFirstName(driverDTO.getFirstName());
        driver.setLastName(driverDTO.getLastName());
        driver.setHoursWorked(0.0);
        driver.setDriverStatus(DriverStatus.REST);
        driver.setCurrentCity(mapDAO.findCityByCode(driverDTO.getCityCode()));
        return driver;
    }

    public Driver updateDriverFromDto(DriverDTO driverDTO) {
        Driver driver = driverDAO.findDriverById(driverDTO.getId());
        driver.setEnable(driverDTO.isEnable());
        driver.setFirstName(driverDTO.getFirstName());
        driver.setLastName(driverDTO.getLastName());
        driver.setHoursWorked(driverDTO.getHoursWorked());
        driver.setDriverStatus(driverDTO.getDriverStatus());
        driver.setCurrentCity(mapDAO.findCityByCode(driverDTO.getCityCode()));
//        driver.setCurrentTruck(truckDAO.findTruckById(driverDTO.getTruckId()));
        return driver;
    }

}
