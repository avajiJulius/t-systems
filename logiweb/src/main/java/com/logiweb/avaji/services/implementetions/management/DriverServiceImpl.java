package com.logiweb.avaji.services.implementetions.management;

import com.logiweb.avaji.daos.DriverDAO;
import com.logiweb.avaji.daos.UserDAO;
import com.logiweb.avaji.dtos.DriverDTO;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.exceptions.UniqueValidationException;
import com.logiweb.avaji.services.api.management.DriverService;
import com.logiweb.avaji.services.implementetions.utils.Mapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {

    private static final Logger logger = LogManager.getLogger(DriverServiceImpl.class);

    private final DriverDAO driverDAO;
    private final Mapper mapper;
    private final UserDAO userDAO;

    @Autowired
    public DriverServiceImpl(DriverDAO driverDAO, Mapper mapper, UserDAO userDAO) {
        this.driverDAO = driverDAO;
        this.mapper = mapper;
        this.userDAO = userDAO;
    }

    @Override
    public List<DriverDTO> readAllDrivers() {
        return driverDAO.findAllDrivers();
    }

    @Override
    @Transactional
    public void createDriver(DriverDTO driverDTO) {
        if(!emailIsUnique(driverDTO.getEmail())) {
            logger.info("Try to create user whit register email");
            throw new UniqueValidationException("Current email already used");
        }

        Driver driver = mapper.createDriverFromDto(driverDTO);
        driverDAO.saveDriver(driver);

        logger.info("Create driver by id: {}", driver.getId());
        createWorkShift(driver.getId());
    }

    private boolean emailIsUnique(String email) {
        return !Optional.ofNullable(userDAO.findUserByEmail(email)).isPresent();
    }

    private void createWorkShift(long id) {
        driverDAO.saveWorkShift(id);
        logger.info("Create work shift for driver with id: {}", id);
    }

    @Override
    public void updateDriver(long driverId,DriverDTO updatedDriver) {
        Driver driver = driverDAO.findDriverEntity(driverId);

        driverDAO.updateDriver(mapper.updateDriverFromDto(driver,updatedDriver));
        logger.info("Update driver by id: {}", driverId);
    }

    @Override
    public void deleteDriver(long driverID) {
        driverDAO.deleteDriver(driverID);
        logger.info("Delete driver by id: {}", driverID);
    }

    @Override
    public DriverDTO readDriverById(long id) {
        return driverDAO.findDriverById(id);
    }


}
