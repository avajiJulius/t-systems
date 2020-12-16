package com.logiweb.avaji.services;

import com.logiweb.avaji.dao.DriverDAO;
import com.logiweb.avaji.dao.OrderDAO;
import com.logiweb.avaji.dao.TruckDAO;
import com.logiweb.avaji.entities.enums.DriverStatus;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.views.DriverResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DriverServiceImpl implements DriverService{

    private final DriverDAO driverDAO;

    @Autowired
    public DriverServiceImpl(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    @Override
    public void createDriver(Driver driver) {
        driverDAO.saveDriver(driver);
    }

    @Override
    public List<Driver> readDrivers() {
        return driverDAO.findDrivers();
    }

    @Override
    public DriverResponse readDriverById(String driverId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateDriver(Driver updatedDriver) {
        driverDAO.updateDriver(updatedDriver);
    }

    @Override
    public void deleteDriver(String driverID) {

        driverDAO.deleteDriver(driverID);
    }

    @Override
    public void readAndAssignDriverForTruck(Truck truck) {
        throw new UnsupportedOperationException();
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
