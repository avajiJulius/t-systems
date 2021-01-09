package com.logiweb.avaji.crud.driver.service.implementetion;

import com.logiweb.avaji.crud.driver.dao.DriverDAO;
import com.logiweb.avaji.crud.driver.dto.DriverDTO;
import com.logiweb.avaji.crud.truck.dao.TruckDAO;
import com.logiweb.avaji.crud.driver.dto.DriverPrivateResponseDto;
import com.logiweb.avaji.entities.enums.DriverStatus;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.crud.driver.service.api.DriverService;
import com.logiweb.avaji.orderdetails.service.implementetion.ShiftDetailsServiceImpl;
import com.logiweb.avaji.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverDAO driverDAO;
    private final TruckDAO truckDAO;
    private final Mapper mapper;
    private final ShiftDetailsServiceImpl computingService;

    @Autowired
    public DriverServiceImpl(DriverDAO driverDAO, TruckDAO truckDAO,
                             Mapper mapper, ShiftDetailsServiceImpl computingService) {
        this.driverDAO = driverDAO;
        this.truckDAO = truckDAO;
        this.mapper = mapper;
        this.computingService = computingService;
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
    }

    @Override
    public void updateDriver(DriverDTO updatedDriver) {
        Driver driver = mapper.updateDriverFromDto(updatedDriver);
        driverDAO.updateDriver(driver);
    }

    @Override
    public void deleteDriver(long driverID) {
        driverDAO.deleteDriver(driverID);
    }


}
