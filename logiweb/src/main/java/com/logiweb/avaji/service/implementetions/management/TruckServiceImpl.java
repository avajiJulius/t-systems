package com.logiweb.avaji.service.implementetions.management;

import com.logiweb.avaji.dtos.TruckDTO;
import com.logiweb.avaji.entity.model.Truck;
import com.logiweb.avaji.dao.TruckDAO;
import com.logiweb.avaji.service.api.management.TruckService;
import com.logiweb.avaji.service.api.mq.InformationProducerService;
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
    private final InformationProducerService producerService;

    @Autowired
    public TruckServiceImpl(TruckDAO truckDAO, Mapper converter,
                            InformationProducerService informationProducerService) {
        this.truckDAO = truckDAO;
        this.converter = converter;
        this.producerService = informationProducerService;
    }

    @Override
    public boolean createTruck(TruckDTO truckDTO) {
        Truck truck = converter.dtoToTruck(truckDTO);

        truckDAO.saveTruck(truck);
        boolean isSaved = truckDAO.containsTruck(truck.getTruckId());

        if (isSaved) {
            logger.info("Create truck by id: {}", truck.getTruckId());
            producerService.updateTruckInformation();

            producerService.sendInformation();
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
        producerService.updateTruckInformation();

        producerService.sendInformation();
    }


    @Override
    public boolean deleteTruck(String truckID) {
        truckDAO.deleteTruck(truckID);
        boolean isExist = truckDAO.containsTruck(truckID);

        if(!isExist) {
            logger.info("Delete truck by id: {}", truckID);
            producerService.updateTruckInformation();

            producerService.sendInformation();
            return true;
        }
        return false;
    }

    @Override
    public long getTrucksTotalNumbers() {
        return truckDAO.countTrucks();
    }
}
