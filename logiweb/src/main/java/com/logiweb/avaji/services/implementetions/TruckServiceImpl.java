package com.logiweb.avaji.services.implementetions;

import com.logiweb.avaji.dao.CountryMapDAO;
import com.logiweb.avaji.entities.dto.TruckDto;
import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.dao.TruckDAO;
import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.services.CountryMapService;
import com.logiweb.avaji.services.TruckService;
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

    @Autowired
    public TruckServiceImpl(TruckDAO truckDAO, CountryMapDAO mapDAO) {
        this.truckDAO = truckDAO;
        this.mapDAO = mapDAO;
    }

    @Override
    public void createTruck(TruckDto truckDto) {
        Truck truck = convertTruckDtoToTruck(truckDto);
        truckDAO.saveTruck(truck);
    }

    private Truck convertTruckDtoToTruck(TruckDto truckDto) {
        City city = mapDAO.findCityByCode(truckDto.getCurrentCityCode());
        Truck truck = new Truck();
        truck.setTruckId(truckDto.getTruckId());
        truck.setWorkShiftSize(2.0);
        truck.setCapacity(truckDto.getCapacity());
        truck.setServiceable(true);
        truck.setCurrentCity(city);
        return truck;
    }

    @Override
    public List<Truck> readTrucks() {
        return truckDAO.findTrucks();
    }

    @Override
    public Truck readTruckById(String truckID) {
        return truckDAO.findTruckById(truckID);
    }

    @Override
    public void updateTruck(Truck updatedTruck) {
        truckDAO.saveTruck(updatedTruck);
    }

    @Override
    public void deleteTruck(String truckID) {
        truckDAO.deleteTruck(truckID);
    }
}
