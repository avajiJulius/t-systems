package com.logiweb.avaji.services.implementetions;

import com.logiweb.avaji.dao.*;
import com.logiweb.avaji.entities.enums.CargoStatus;
import com.logiweb.avaji.entities.enums.DriverStatus;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.utils.WorkShift;
import com.logiweb.avaji.exceptions.CargoStatusException;
import com.logiweb.avaji.exceptions.DriverStatusException;
import com.logiweb.avaji.services.api.WorkShiftService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
public class WorkShiftServiceImpl implements WorkShiftService {

    private final static Logger logger = LogManager.getLogger(WorkShiftServiceImpl.class);

    private final DriverDAO driverDAO;
    private final CargoDAO cargoDAO;
    private final OrderDAO orderDAO;
    private final WaypointDAO waypointDAO;
    private final WorkShiftDAO workShiftDAO;

    @Autowired
    public WorkShiftServiceImpl(DriverDAO driverDAO, CargoDAO cargoDAO,
                                OrderDAO orderDAO, WaypointDAO waypointDAO,
                                WorkShiftDAO workShiftDAO) {
        this.driverDAO = driverDAO;
        this.cargoDAO = cargoDAO;
        this.orderDAO = orderDAO;
        this.waypointDAO = waypointDAO;
        this.workShiftDAO = workShiftDAO;
    }

    @Override
    public void updateWorkShiftStatus(boolean isActive, Integer driverId) {
        Driver driver = driverDAO.findDriverById(driverId);
        WorkShift workShift;
        if(isActive == true) {
            workShift = new WorkShift();
            workShift.setActive(true);
            workShift.setDriver(driver);
            workShift.setStart(LocalDateTime.now());
        } else {
            workShift = workShiftDAO.findShiftByDriverId(driverId);
            workShift.setEnd(LocalDateTime.now());
            workShift.setActive(false);
            long workedHours = ChronoUnit.HOURS.between(workShift.getStart(), workShift.getEnd());
            driver.setWorkShift(workShift);
            driver.setHoursWorked((double) workedHours);
        }
        driverDAO.updateDriver(driver);
    }

    @Override
    public void updateDriverStatus(String driverStatus, Integer driverId) {
        Driver driver = driverDAO.findDriverById(driverId);
        switch (driverStatus) {
            case ("REST"):
                driver.setDriverStatus(DriverStatus.REST);
                break;
            case ("DRIVING"):
                driver.setDriverStatus(DriverStatus.DRIVING);
                break;
            case ("SECOND_DRIVER"):
                driver.setDriverStatus(DriverStatus.SECOND_DRIVER);
                break;
            case ("LOAD_UNLOAD_WORK"):
                driver.setDriverStatus(DriverStatus.LOAD_UNLOAD_WORK);
                break;
            default:
                logger.error("Driver status '{}' not found", driverStatus);
                throw new DriverStatusException("Driver status not found");
        }
        driverDAO.updateDriver(driver);
    }

    @Override
    public void updateCargoStatus(String cargoStatus, Integer cargoId) {
        Cargo cargo = cargoDAO.findCargoById(cargoId);
        CargoStatus status = cargo.getCargoStatus();
        CargoStatus newStatus = computeCargoStatus(cargoStatus, status);
        cargo.setCargoStatus(newStatus);
        cargoDAO.updateCargo(cargo);
        //TODO: update order if all cargo are DELIVERED
    }

    //TODO: refactor
    private CargoStatus computeCargoStatus(String cargoStatus, CargoStatus status) {
        if(cargoStatus.equals("LOAD")) {
            if (status == CargoStatus.PREPARED) {
                status = CargoStatus.SHIPPED;
            } else {
                logger.error("Can not update '{}' status", status.name());
                throw new CargoStatusException();
            }
        } else {
            if (status == CargoStatus.SHIPPED) {
                status = CargoStatus.DELIVERED;
            } else {
                logger.error("Can not update '{}' status", status.name());
                throw new CargoStatusException();
            }
        }
        return status;
    }
}


