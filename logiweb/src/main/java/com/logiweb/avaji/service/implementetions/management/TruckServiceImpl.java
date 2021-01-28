package com.logiweb.avaji.service.implementetions.management;

import com.logiweb.avaji.dtos.OrderDTO;
import com.logiweb.avaji.dtos.TruckDTO;
import com.logiweb.avaji.entity.model.Truck;
import com.logiweb.avaji.dao.TruckDAO;
import com.logiweb.avaji.service.api.management.TruckService;
import com.logiweb.avaji.service.implementetions.utils.Mapper;
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
    public boolean createTruck(TruckDTO truckDTO) {
        Truck truck = converter.dtoToTruck(truckDTO);

        boolean isSaved = truckDAO.saveTruck(truck);
        if (isSaved) {
            logger.info("Create truck by id: {}", truck.getTruckId());
            return true;
        }
        return false;
    }

    @Override
    public List<TruckDTO> readTrucksPage(int pageNumber, int pageSize) {
        int indexFrom = 0;
        if(pageNumber != 1) {
            indexFrom = (pageNumber - 1) * pageSize;
        }
        return truckDAO.findTrucksPage(indexFrom, pageSize);
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
    public boolean deleteTruck(String truckID) {
        boolean result = truckDAO.deleteTruck(truckID);
        if(result) {
            logger.info("Delete truck by id: {}", truckID);
            return true;
        }
        return false;
    }

    @Override
    public long getTrucksTotalNumbers() {
        return truckDAO.countTrucks();
    }
}
