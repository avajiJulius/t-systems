package com.logiweb.avaji.crud.workdetails.service.implementetion;

import com.logiweb.avaji.crud.workdetails.dto.ShiftDetailsDto;
import com.logiweb.avaji.dtoconverter.DtoConverter;
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
import com.logiweb.avaji.exceptions.DriverStatusNotFoundException;
import com.logiweb.avaji.crud.workdetails.service.api.WorkDetailsService;
import com.logiweb.avaji.exceptions.ShiftValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class WorkDetailsServiceImpl implements WorkDetailsService {

    private static final Logger logger = LogManager.getLogger(WorkDetailsServiceImpl.class);

    private final DriverDAO driverDAO;
    private final CargoDAO cargoDAO;
    private final WorkDetailsDAO workDetailsDAO;
    private final DtoConverter converter;

    @Autowired
    public WorkDetailsServiceImpl(DriverDAO driverDAO, CargoDAO cargoDAO,
                                  WorkDetailsDAO workDetailsDAO, DtoConverter converter) {
        this.driverDAO = driverDAO;
        this.cargoDAO = cargoDAO;
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
    public void updateCargoStatus(String cargoStatus, long cargoId) throws CargoStatusException {
        Cargo cargo = cargoDAO.findCargoById(cargoId);
        CargoStatus status = cargo.getCargoStatus();
        CargoStatus newStatus = computeCargoStatus(cargoStatus, status);
        cargo.setCargoStatus(newStatus);
        cargoDAO.updateCargo(cargo);
        //TODO: update order if all cargo are DELIVERED
    }

    @Override
    @Transactional
    public void updateShiftDetails(long id, ShiftDetailsDto shiftDetails)
            throws ShiftValidationException, DriverStatusNotFoundException {
        WorkDetails workDetails = workDetailsDAO.findWorkDetailsByDriverId(id);
        Driver driver = driverDAO.findDriverById(id);

        DriverStatus status = driver.getDriverStatus();
        WorkShift shift = workDetailsDAO.findShiftById(id);
        if(!shiftDetails.getDriverStatus().equals(status.name())) {
            status = convertToDriverStatus(shiftDetails.getDriverStatus());
        }
        if(!validateShiftAndDriverStatus(shiftDetails.isActive(), status)) {
            logger.error("Driver status and shift status conflict");
            throw new ShiftValidationException("Driver status and shift status conflict");
        }

        if(shift.isActive() != shiftDetails.isActive()) {
            shift = updateWorkShiftStatus(shiftDetails.isActive(), shift, driver);
            workDetails.setWorkShift(shift);
            workDetailsDAO.updateWorkDetails(workDetails);
        }

        driver.setDriverStatus(status);
        driverDAO.updateDriver(driver);
    }

    public boolean validateShiftAndDriverStatus(boolean active, DriverStatus status) {
        return !active ? status.ordinal() == 0 : status.ordinal() > 0;
    }

    public WorkShift updateWorkShiftStatus(boolean isActive, WorkShift workShift, Driver driver) {
        if(isActive == true) {
            workShift.setActive(true);
            workShift.setStart(LocalDateTime.now());
            workShift.setEnd(null);
        } else {
            workShift.setEnd(LocalDateTime.now());
            workShift.setActive(false);
            updateHoursWorked(workShift, driver);
        }
        return workShift;
    }

    private void updateHoursWorked(WorkShift workShift, Driver driver) {
        long shiftHours = ChronoUnit.HOURS.between(workShift.getStart(), workShift.getEnd());
        double hoursWorked = driver.getHoursWorked();
        driver.setHoursWorked((double)shiftHours + hoursWorked);
    }


    public DriverStatus convertToDriverStatus(String driverStatus) throws DriverStatusNotFoundException {
        DriverStatus status;
        switch (driverStatus) {
            case ("REST"):
                status = DriverStatus.REST;
                break;
            case ("DRIVING"):
                status = DriverStatus.DRIVING;
                break;
            case ("SECOND_DRIVER"):
                status = DriverStatus.SECOND_DRIVER;
                break;
            case ("LOAD_UNLOAD_WORK"):
                status = DriverStatus.LOAD_UNLOAD_WORK;
                break;
            default:
                logger.error("Driver status '{}' not found", driverStatus);
                throw new DriverStatusNotFoundException("Driver status not found");
        }

        return status;
    }

    //TODO: refactor
    private CargoStatus computeCargoStatus(String cargoStatus, CargoStatus status) throws CargoStatusException {
        String statusName = status.name();
        if(cargoStatus.equals("LOAD ")) {
            if (status == CargoStatus.PREPARED) {
                status = CargoStatus.SHIPPED;
            } else {
                logger.error("Can not update '{}' status", statusName);
                throw new CargoStatusException("Cargo status conflict");
            }
        } else {
            if (status == CargoStatus.SHIPPED) {
                status = CargoStatus.DELIVERED;
            } else {
                logger.error("Can not update '{}' status", statusName);
                throw new CargoStatusException("Cargo status conflict");
            }
        }
        return status;
    }
}


