package com.logiweb.avaji.services.implementetions.management;

import com.logiweb.avaji.dtos.TruckDTO;
import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.daos.TruckDAO;
import com.logiweb.avaji.services.api.management.TruckService;
import com.logiweb.avaji.services.implementetions.utils.Mapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TruckServiceImpl implements TruckService {

    private static final Logger logger = LogManager.getLogger(TruckServiceImpl.class);

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
        logger.info("Create truck by id: {}", truck.getTruckId());
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
        logger.info("Update truck by id: {}", truckId);
    }


    @Override
    public void deleteTruck(String truckID) {
        truckDAO.deleteTruck(truckID);
        logger.info("Delete truck by id: {}", truckID);
    }

}
