package com.logiweb.avaji.services.implementetions;

import com.logiweb.avaji.dao.CargoDAO;
import com.logiweb.avaji.dao.DriverDAO;
import com.logiweb.avaji.dao.OrderDAO;
import com.logiweb.avaji.dao.WaypointDAO;
import com.logiweb.avaji.entities.enums.CargoStatus;
import com.logiweb.avaji.entities.enums.DriverStatus;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.utils.WorkShift;
import com.logiweb.avaji.exceptions.CargoStatusException;
import com.logiweb.avaji.exceptions.DriverStatusException;
import com.logiweb.avaji.services.WorkShiftService;
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

    @Autowired
    public WorkShiftServiceImpl(DriverDAO driverDAO, CargoDAO cargoDAO,
                                OrderDAO orderDAO, WaypointDAO waypointDAO) {
        this.driverDAO = driverDAO;
        this.cargoDAO = cargoDAO;
        this.orderDAO = orderDAO;
        this.waypointDAO = waypointDAO;
    }

    @Override
    public void updateWorkShiftStatus(boolean isActive, Integer driverId) {
        Driver driver = driverDAO.findDriverById(driverId);
        if(isActive == true) {
            driver.setWorkShift(new WorkShift(true, LocalDateTime.now(), null));
        } else {
            WorkShift workShift = driver.getWorkShift();
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


