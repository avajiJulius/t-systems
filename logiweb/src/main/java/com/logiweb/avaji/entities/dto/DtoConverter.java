package com.logiweb.avaji.entities.dto;

import com.logiweb.avaji.dao.CargoDAO;
import com.logiweb.avaji.dao.CountryMapDAO;
import com.logiweb.avaji.dao.OrderDAO;
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

@Service
public class DtoConverter {

    private final CountryMapDAO mapDAO;
    private final OrderDAO orderDAO;
    private final CargoDAO cargoDAO;

    @Autowired
    public DtoConverter(CountryMapDAO mapDAO, OrderDAO orderDAO, CargoDAO cargoDAO) {
        this.mapDAO = mapDAO;
        this.orderDAO = orderDAO;
        this.cargoDAO = cargoDAO;
    }

    public List<CityDto> citiesToDtos(List<City> cities) {
        List<CityDto> dtos = new ArrayList<>();
        for(City city: cities) {
            dtos.add(new CityDto(city.getCityCode(), city.getCityName()));
        }
        return dtos;
    }

    public City dtoToCity(CityDto cityDto) {
        City city = new City();
        city.setCityCode(cityDto.getCityCode());
        city.setCityName(cityDto.getCityName());
        return city;
    }


    public Truck dtoToTruck(TruckDto truckDto) {
        Truck truck = new Truck();
        truck.setTruckId(truckDto.getTruckId());
        truck.setWorkShiftSize(0);
        truck.setCurrentCity(mapDAO.findCityByCode(truckDto.getCurrentCityCode()));
        truck.setCapacity(truckDto.getCapacity());
        truck.setServiceable(truckDto.isServiceable());
        return truck;
    }

    public TruckDto truckToDto(Truck truck) {
        return new TruckDto(truck.getTruckId(), truck.getCapacity(),
                truck.isServiceable(), truck.getCurrentCity().getCityCode());
    }

    public List<TruckDto> trucksToDtos(List<Truck> trucks) {
        List<TruckDto> dtos = new ArrayList<>();
        for(Truck truck: trucks) {
            dtos.add(truckToDto(truck));
        }
        return dtos;
    }

    public List<DriverPublicResponseDto> driversToDtos(List<Driver> allDrivers) {
        List<DriverPublicResponseDto> dtos = new ArrayList<>();
        for(Driver driver: allDrivers) {
            dtos.add(new DriverPublicResponseDto(driver.getDriverId(), driver.getFirstName(),
                    driver.getLastName(), driver.getHoursWorked(), driver.getDriverStatus().name(),
                    driver.getCurrentCity(), driver.getCurrentTruck()));
        }
        return dtos;
    }

    public Waypoint dtoToWaypoint(WaypointDto dto, Order order) {
        WaypointType type;
        if (dto.getType().equals("LOADING")) {
            type = WaypointType.LOADING;
        } else {
            type = WaypointType.UNLOADING;
        }
        return new Waypoint(mapDAO.findCityByCode(dto.getCityCode()),type, order, cargoDAO.findCargoById(dto.getCargoId()));
    }

    public List<Waypoint> dtosToWaypoints(List<WaypointDto> waypointDtos, Order order) {
        List<Waypoint> waypoints = new ArrayList<>();
        for(WaypointDto waypointDto: waypointDtos) {
            waypoints.add(dtoToWaypoint(waypointDto, order));
        }
        return waypoints;
    }
}
