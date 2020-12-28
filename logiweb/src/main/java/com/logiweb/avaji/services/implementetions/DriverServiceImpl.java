package com.logiweb.avaji.services.implementetions;

import com.logiweb.avaji.dao.DriverDAO;
import com.logiweb.avaji.dao.OrderDAO;
import com.logiweb.avaji.entities.dto.DriverPrivateResponseDto;
import com.logiweb.avaji.entities.dto.DriverPublicResponseDto;
import com.logiweb.avaji.entities.enums.DriverStatus;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.services.api.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverDAO driverDAO;
    private final OrderDAO orderDAO;
    private final DtoConverter converter;
    private final ComputingService computingService;

    @Autowired
    public DriverServiceImpl(DriverDAO driverDAO, OrderDAO orderDAO,
                             DtoConverter converter, ComputingService computingService) {
        this.driverDAO = driverDAO;
        this.orderDAO = orderDAO;
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
    public void updateDriver(Driver updatedDriver) {
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
