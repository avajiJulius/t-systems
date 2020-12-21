package com.logiweb.avaji.services.implementetions;

import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.dao.TruckDAO;
import com.logiweb.avaji.services.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TruckServiceImpl implements TruckService {

    private final TruckDAO truckDAO;

    @Autowired
    public TruckServiceImpl(TruckDAO truckDAO) {
        this.truckDAO = truckDAO;
    }

    @Override
    public void createTruck(Truck truck) {
        truckDAO.saveTruck(truck);
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
        truckDAO.updateTruck(updatedTruck);
    }

    @Override
    public void deleteTruck(String truckID) {
        truckDAO.deleteTruck(truckID);
    }
}
