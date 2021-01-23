package com.logiweb.avaji.services.implementetions;

import com.logiweb.avaji.daos.DriverDAO;
import com.logiweb.avaji.dtos.DriverDTO;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.WorkShift;
import com.logiweb.avaji.services.api.DriverService;
import com.logiweb.avaji.mapper.Mapper;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverDAO driverDAO;
    private final Mapper mapper;

    @Autowired
    public DriverServiceImpl(DriverDAO driverDAO,
                             Mapper mapper) {
        this.driverDAO = driverDAO;
        this.mapper = mapper;
    }

    @Override
    public List<DriverDTO> readAllDrivers() {
        return driverDAO.findAllDrivers();
    }

    @Override
    @Transactional
    public void createDriver(DriverDTO driverDTO) {
        Driver driver = mapper.createDriverFromDto(driverDTO);
        driverDAO.saveDriver(driver);
        createWorkShift(driver.getId());
    }

    private void createWorkShift(long id) {
        driverDAO.saveWorkShift(id);
    }

    @Override
    public void updateDriver(long driverId,DriverDTO updatedDriver) {
        Driver driver = driverDAO.findDriverEntity(driverId);


        driverDAO.updateDriver(mapper.updateDriverFromDto(driver,updatedDriver));
    }

    @Override
    public void deleteDriver(long driverID) {
        driverDAO.deleteDriver(driverID);
    }

    @Override
    public DriverDTO readDriverById(long id) {
        return driverDAO.findDriverById(id);
    }


}
