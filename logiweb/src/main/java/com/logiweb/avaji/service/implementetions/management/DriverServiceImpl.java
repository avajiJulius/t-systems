package com.logiweb.avaji.service.implementetions.management;

import com.logiweb.avaji.dao.DriverDAO;
import com.logiweb.avaji.dtos.DriverDTO;
import com.logiweb.avaji.entitie.model.Driver;
import com.logiweb.avaji.service.api.management.DriverService;
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

    @Autowired
    public DriverServiceImpl(DriverDAO driverDAO, Mapper mapper) {
        this.driverDAO = driverDAO;
        this.mapper = mapper;
    }

    @Override
    public List<DriverDTO> readAllDrivers() {
        return driverDAO.findAllDrivers();
    }

    @Override
    @Transactional
    public boolean createDriver(DriverDTO driverDTO) {
        Driver driver = mapper.createDriverFromDto(driverDTO);

        boolean isSaved = driverDAO.saveDriver(driver);
        if(isSaved) {

            logger.info("Create driver by id: {}", driver.getId());
            boolean isCreated = createWorkShift(driver.getId());

            if(isCreated) {
                return true;
            }
        }
        return false;
    }

    private boolean createWorkShift(long id) {
        boolean isSaved = driverDAO.saveWorkShift(id);
        if(isSaved) {
            logger.info("Create work shift for driver with id: {}", id);
            return true;
        }
        return false;
    }

    @Override
    public void updateDriver(long driverId,DriverDTO updatedDriver) {
        Driver driver = driverDAO.findDriverEntity(driverId);

        driverDAO.updateDriver(mapper.updateDriverFromDto(driver,updatedDriver));
        logger.info("Update driver by id: {}", driverId);
    }

    @Override
    public boolean deleteDriver(long driverID) {
        boolean isDeleted = driverDAO.deleteDriver(driverID);
        if(isDeleted) {
            logger.info("Delete driver by id: {}", driverID);
            return true;
        }
        return false;
    }

    @Override
    public DriverDTO readDriverById(long id) {
        return driverDAO.findDriverById(id);
    }


}
