package com.logiweb.avaji.entities.dto;

import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.entities.models.utils.City;

import java.util.ArrayList;
import java.util.List;

public class DtoConverter {

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
        truck.setCapacity(truckDto.getCapacity());
        truck.setServiceable(truckDto.isServiceable());
        return truck;
    }

    public TruckDto truckToDto(Truck truck) {
        return new TruckDto(truck.getTruckId(), truck.getCapacity(),
                truck.isServiceable(), truck.getCurrentCity().getCityCode());
    }
}
