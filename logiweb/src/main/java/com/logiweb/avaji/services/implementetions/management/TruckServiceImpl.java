package com.logiweb.avaji.services.implementetions.management;

import com.logiweb.avaji.dtos.TruckDTO;
import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.daos.TruckDAO;
import com.logiweb.avaji.exceptions.UniqueValidationException;
import com.logiweb.avaji.services.api.management.TruckService;
import com.logiweb.avaji.services.implementetions.utils.Mapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public void createTruck(TruckDTO truckDTO) {
        if(!idIsUnique(truckDTO.getTruckId())) {
            logger.info("Try to create truck with existing truck id: {}", truckDTO.getTruckId());
            throw new UniqueValidationException("Truck whit this id already existing");
        }
        Truck truck = converter.dtoToTruck(truckDTO);

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
    public int calculateFreeSpaceInShift(long orderId) {
        TruckDTO truck = truckDAO.findTruckByOrderId(orderId);
        int currentSize = (int) truckDAO.countDriversOfTruck(truck.getTruckId());
        int size = truck.getShiftSize();
        return (size - currentSize);
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

    private boolean idIsUnique(String truckId) {
        return !Optional.ofNullable(truckDAO.findTruckById(truckId)).isPresent();
    }
}
