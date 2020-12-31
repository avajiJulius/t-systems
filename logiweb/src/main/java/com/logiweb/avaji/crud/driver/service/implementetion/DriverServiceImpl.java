package com.logiweb.avaji.crud.driver.service.implementetion;

import com.logiweb.avaji.crud.driver.dao.DriverDAO;
import com.logiweb.avaji.crud.truck.dao.TruckDAO;
import com.logiweb.avaji.crud.driver.dto.DriverPrivateResponseDto;
import com.logiweb.avaji.crud.driver.dto.DriverPublicResponseDto;
import com.logiweb.avaji.entities.enums.DriverStatus;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.crud.driver.service.api.DriverService;
import com.logiweb.avaji.orderdetails.service.implementetion.OrderDetailsServiceImpl;
import com.logiweb.avaji.dtoconverter.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverDAO driverDAO;
    private final TruckDAO truckDAO;
    private final DtoConverter converter;
    private final OrderDetailsServiceImpl computingService;

    @Autowired
    public DriverServiceImpl(DriverDAO driverDAO, TruckDAO truckDAO,
                             DtoConverter converter, OrderDetailsServiceImpl computingService) {
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
    public DriverPrivateResponseDto readDriverById(Integer driverId) {
        Driver driver = driverDAO.findDriverById(driverId);
        return converter.driverToDto(driver);
    }

    @Override
    public void updateDriver(Integer driverId, Driver updatedDriver) {
        Driver driver = driverDAO.findDriverById(driverId);
        boolean isBusy = Optional.ofNullable(driver.getCurrentTruck()).isPresent();

        updatedDriver.setDriverId(driverId);
        updatedDriver.setFree(!isBusy);

        driverDAO.updateDriver(updatedDriver);
    }



    @Override
    public void deleteDriver(Integer driverID) {
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
