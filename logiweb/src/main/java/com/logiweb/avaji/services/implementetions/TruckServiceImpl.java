package com.logiweb.avaji.services.implementetions;

import com.logiweb.avaji.dao.CountryMapDAO;
import com.logiweb.avaji.dao.WaypointDAO;
import com.logiweb.avaji.entities.dto.TruckDto;
import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.dao.TruckDAO;
import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.services.api.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
public class TruckServiceImpl implements TruckService {

    @PersistenceContext
    private EntityManager entityManager;

    private final TruckDAO truckDAO;
    private final CountryMapDAO mapDAO;
    private final WaypointDAO waypointDAO;
    private final ComputingService computingService;
    private final DtoConverter converter;

    @Autowired
    public TruckServiceImpl(TruckDAO truckDAO, CountryMapDAO mapDAO, WaypointDAO waypointDAO,
                            ComputingService computingService, DtoConverter converter) {
        this.truckDAO = truckDAO;
        this.mapDAO = mapDAO;
        this.waypointDAO = waypointDAO;
        this.computingService = computingService;
        this.converter = converter;
    }

    @Override
    public void createTruck(TruckDto truckDto) {
        City city = getCity(truckDto);
        Truck truck = converter.dtoToTruck(truckDto);
        truck.setCurrentCity(city);
        truckDAO.saveTruck(truck);
    }


    @Override
    public List<Truck> readTrucks() {
        return truckDAO.findTrucks();
    }

    @Override
    public TruckDto readTruckById(String truckID) {
        Truck truck = truckDAO.findTruckById(truckID);
        return converter.truckToDto(truck);
    }

    @Override
    public List<Truck> readTrucksForOrder(Integer orderId) {
        Double maxCapacity = computingService.new OrderCalculation(orderId).getMaxCapacity();
        return truckDAO.findTrucksForOrder(maxCapacity);
    }

    @Override
    public void updateTruck(TruckDto updatedTruck) {
        Truck truck = converter.dtoToTruck(updatedTruck);
        truck.setCurrentCity(getCity(updatedTruck));
        truckDAO.updateTruck(truck);
    }


    @Override
    public void deleteTruck(String truckID) {
        truckDAO.deleteTruck(truckID);
    }

    private City getCity(TruckDto truckDto) {
        City city = mapDAO.findCityByCode(truckDto.getCurrentCityCode());
        return city;
    }
}
