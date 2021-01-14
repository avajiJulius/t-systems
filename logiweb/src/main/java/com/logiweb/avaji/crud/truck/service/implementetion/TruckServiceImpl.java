package com.logiweb.avaji.crud.truck.service.implementetion;

import com.logiweb.avaji.crud.truck.dto.TruckDTO;
import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.crud.truck.dao.TruckDAO;
import com.logiweb.avaji.crud.truck.service.api.TruckService;
import com.logiweb.avaji.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TruckServiceImpl implements TruckService {

    private final TruckDAO truckDAO;
    private final Mapper converter;

    @Autowired
    public TruckServiceImpl(TruckDAO truckDAO, Mapper converter) {
        this.truckDAO = truckDAO;
        this.converter = converter;
    }

    @Override
    public void createTruck(TruckDTO truckDto) {
        Truck truck = converter.dtoToTruck(truckDto);

        truckDAO.saveTruck(truck);
    }


    @Override
    public List<TruckDTO> readTrucks() {
        return truckDAO.findTrucks();
    }

    @Override
    public TruckDTO readTruckById(String truckID) {
        return truckDAO.findTruckById(truckID);
    }


    @Override
    public void updateTruck(String truckId, TruckDTO updatedTruck) {
        updatedTruck.setTruckId(truckId);
        Truck truck = converter.dtoToTruck(updatedTruck);

        truckDAO.updateTruck(truck);
    }


    @Override
    public void deleteTruck(String truckID) {
        truckDAO.deleteTruck(truckID);
    }

}
