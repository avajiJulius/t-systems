package com.logiweb.avaji.crud.driver.service.implementetion;

import com.logiweb.avaji.crud.driver.dao.DriverDAO;
import com.logiweb.avaji.crud.truck.dao.TruckDAO;
import com.logiweb.avaji.crud.driver.dto.DriverPrivateResponseDto;
import com.logiweb.avaji.crud.driver.dto.DriverPublicResponseDto;
import com.logiweb.avaji.entities.enums.DriverStatus;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.crud.driver.service.api.DriverService;
import com.logiweb.avaji.orderdetails.service.implementetion.ShiftDetailsServiceImpl;
import com.logiweb.avaji.dtoconverter.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverDAO driverDAO;
    private final TruckDAO truckDAO;
    private final DtoConverter converter;
    private final ShiftDetailsServiceImpl computingService;

    @Autowired
    public DriverServiceImpl(DriverDAO driverDAO, TruckDAO truckDAO,
                             DtoConverter converter, ShiftDetailsServiceImpl computingService) {
        this.driverDAO = driverDAO;
        this.truckDAO = truckDAO;
        this.converter = converter;
        this.computingService = computingService;
    }



    @Override
    public void createDriver(Driver driver) {
        driverDAO.saveDriver(driver);
    }

    @Override
    public List<DriverPublicResponseDto> readDrivers() {

        return converter.driversToDtos(driverDAO.findAllDrivers());
    }

    @Override
    public DriverPrivateResponseDto readDriverById(long driverId) {
        Driver driver = driverDAO.findDriverById(driverId);
        return converter.driverToDto(driver);
    }

    @Override
    public void updateDriver(long driverId, Driver updatedDriver) {
        Driver driver = driverDAO.findDriverById(driverId);
        updatedDriver.setId(driverId);
        driverDAO.updateDriver(updatedDriver);
    }



    @Override
    public void deleteDriver(long driverID) {
        driverDAO.deleteDriver(driverID);
    }



    @Override
    public void updateDriverStatus(DriverStatus driverStatus) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateWorkShiftStatus() {
        throw new UnsupportedOperationException();
    }
}
