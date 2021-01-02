package com.logiweb.avaji.crud.workdetails.service.implementetion;

import com.logiweb.avaji.auth.dao.UserDAO;
import com.logiweb.avaji.dtoconverter.DtoConverter;
import com.logiweb.avaji.entities.enums.Role;
import com.logiweb.avaji.entities.models.utils.WorkDetails;
import com.logiweb.avaji.crud.cargo.dao.CargoDAO;
import com.logiweb.avaji.crud.driver.dao.DriverDAO;
import com.logiweb.avaji.crud.workdetails.dao.WorkDetailsDAO;
import com.logiweb.avaji.crud.workdetails.dto.WorkDetailsDto;
import com.logiweb.avaji.entities.enums.CargoStatus;
import com.logiweb.avaji.entities.enums.DriverStatus;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.utils.WorkShift;
import com.logiweb.avaji.exceptions.CargoStatusException;
import com.logiweb.avaji.exceptions.DriverStatusException;
import com.logiweb.avaji.crud.workdetails.service.api.WorkDetailsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class WorkDetailsServiceImpl implements WorkDetailsService {

    private final static Logger logger = LogManager.getLogger(WorkDetailsServiceImpl.class);

    private final DriverDAO driverDAO;
    private final CargoDAO cargoDAO;
    private final UserDAO userDAO;
    private final WorkDetailsDAO workDetailsDAO;
    private final DtoConverter converter;

    @Autowired
    public WorkDetailsServiceImpl(DriverDAO driverDAO, CargoDAO cargoDAO,
                                  UserDAO userDAO, WorkDetailsDAO workDetailsDAO, DtoConverter converter) {
        this.driverDAO = driverDAO;
        this.cargoDAO = cargoDAO;
        this.userDAO = userDAO;
        this.workDetailsDAO = workDetailsDAO;
        this.converter = converter;
    }

    @Override
    @Transactional
    public WorkDetailsDto readWorkDetailsByUserId(long userId) {
        //TODO CHANGE FOR ALL USERS
        WorkDetails workDetails = workDetailsDAO.findWorkDetailsByDriverId(userId);
        return converter.workDetailsToDto(workDetails);
    }

    @Override
    public void updateWorkShiftStatus(boolean isActive, long userId) {
        WorkDetails workDetails = workDetailsDAO.findWorkDetailsById(userId);
        WorkShift workShift = workDetails.getWorkShift();
        if(isActive == true) {
            workShift.setActive(true);
            workShift.setStart(LocalDateTime.now());
        } else {
            workShift.setEnd(LocalDateTime.now());
            workShift.setActive(false);
            long shiftHours = ChronoUnit.HOURS.between(workShift.getStart(), workShift.getEnd());
            double hoursWorked = workShift.getDriver().getHoursWorked();
            workShift.getDriver().setHoursWorked((double) shiftHours + hoursWorked);
        }
        workDetails.setWorkShift(workShift);
        workDetailsDAO.updateWorkDetails(workDetails);
    }


    @Override
    public void updateDriverStatus(String driverStatus, long driverId) {
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
    public void updateCargoStatus(String cargoStatus, long cargoId) {
        Cargo cargo = cargoDAO.findCargoById(cargoId);
        CargoStatus status = cargo.getCargoStatus();
        CargoStatus newStatus = computeCargoStatus(cargoStatus, status);
        cargo.setCargoStatus(newStatus);
        cargoDAO.updateCargo(cargo);
        //TODO: update order if all cargo are DELIVERED
    }

    //TODO: refactor
    private CargoStatus computeCargoStatus(String cargoStatus, CargoStatus status) {
        if(cargoStatus.equals("LOAD ")) {
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


