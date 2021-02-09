package com.logiweb.avaji.service.implementetions.management;

import com.logiweb.avaji.dao.DriverDAO;
import com.logiweb.avaji.dtos.DriverDTO;
import com.logiweb.avaji.entity.model.Driver;
import com.logiweb.avaji.service.api.management.DriverService;
import com.logiweb.avaji.service.api.mq.InformationProducerService;
import com.logiweb.avaji.service.api.validator.UniqueValidatorService;
import com.logiweb.avaji.service.implementetions.utils.Mapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    private static final Logger logger = LogManager.getLogger(DriverServiceImpl.class);

    private final DriverDAO driverDAO;
    private final Mapper mapper;
    private final UniqueValidatorService uniqueValidatorService;
    private final InformationProducerService producerService;

    @Autowired
    public DriverServiceImpl(DriverDAO driverDAO, Mapper mapper,
                             UniqueValidatorService uniqueValidatorService,
                             InformationProducerService producerService) {
        this.driverDAO = driverDAO;
        this.mapper = mapper;
        this.uniqueValidatorService = uniqueValidatorService;
        this.producerService = producerService;
    }

    @Override
    public List<DriverDTO> readDriversPage(int pageNumber, int pageSize) {
        int indexFrom = 0;
        if(pageNumber != 1) {
            indexFrom = (pageNumber - 1) * pageSize;
        }
        return driverDAO.findDriversPage(indexFrom, pageSize);
    }

    @Override
    @Transactional
    public boolean createDriver(DriverDTO driverDTO) {
        uniqueValidatorService.validateEmailUnique(driverDTO.getEmail());

        Driver driver = mapper.createDriverFromDto(driverDTO);

        boolean isSaved = driverDAO.saveDriver(driver);
        if(isSaved) {

            boolean isCreated = createWorkShift(driver.getId());

            if(isCreated) {
                logger.info("Create driver by id: {}", driver.getId());
                logger.info("Create work shift for driver with id: {}", driver.getId());
                producerService.updateDriverInformation();

                return true;
            }
        }
        return false;
    }

    private boolean createWorkShift(long id) {
        boolean isSaved = driverDAO.saveWorkShift(id);
        if(isSaved) {
            return true;
        }
        return false;
    }

    @Override
    public void updateDriver(long driverId,DriverDTO updatedDriver) {
        Driver driver = driverDAO.findDriverEntity(driverId);
        updateDriver(mapper.updateDriverFromDto(driver, updatedDriver));
    }

    @Override
    public void updateDriver(Driver driver) {
        driverDAO.updateDriver(driver);
        logger.info("Update driver by id: {}", driver.getId());

        producerService.updateDriverInformation();
    }

    @Override
    public boolean deleteDriver(long driverID) {
        boolean isDeleted = driverDAO.deleteDriver(driverID);
        if(isDeleted) {
            logger.info("Delete driver by id: {}", driverID);
            producerService.updateDriverInformation();

            return true;
        }
        return false;
    }

    @Override
    public DriverDTO readDriverById(long id) {
        return driverDAO.findDriverById(id);
    }

    @Override
    public long getDriversTotalNumber() {
        return driverDAO.countDrivers();
    }

    @Override
    public List<Driver> readDriversByIds(List<Long> driversIds) {
        return driverDAO.findDriversByIds(driversIds);
    }

    @Override
    public void updateDrivers(List<Driver> drivers) {
        driverDAO.updateDrivers(drivers);
        producerService.updateDriverInformation();
    }


}
