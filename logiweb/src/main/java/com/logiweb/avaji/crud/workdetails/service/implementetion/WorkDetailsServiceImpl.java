package com.logiweb.avaji.crud.workdetails.service.implementetion;

import com.logiweb.avaji.crud.workdetails.dto.ShiftDetailsDto;
import com.logiweb.avaji.crud.cargo.dao.CargoDAO;
import com.logiweb.avaji.crud.driver.dao.DriverDAO;
import com.logiweb.avaji.crud.workdetails.dao.WorkDetailsDAO;
import com.logiweb.avaji.crud.workdetails.dto.WorkDetailsDTO;
import com.logiweb.avaji.entities.enums.CargoStatus;
import com.logiweb.avaji.entities.enums.DriverStatus;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.utils.WorkShift;
import com.logiweb.avaji.exceptions.DriverStatusNotFoundException;
import com.logiweb.avaji.crud.workdetails.service.api.WorkDetailsService;
import com.logiweb.avaji.exceptions.ShiftValidationException;
import com.logiweb.avaji.orderdetails.dao.OrderDetailsDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class WorkDetailsServiceImpl implements WorkDetailsService {

    private static final Logger logger = LogManager.getLogger(WorkDetailsServiceImpl.class);

    private final DriverDAO driverDAO;
    private final CargoDAO cargoDAO;
    private final WorkDetailsDAO workDetailsDAO;
    private final OrderDetailsDAO orderDetailsDAO;

    public WorkDetailsServiceImpl(DriverDAO driverDAO, CargoDAO cargoDAO,
                                  WorkDetailsDAO workDetailsDAO, OrderDetailsDAO orderDetailsDAO) {
        this.driverDAO = driverDAO;
        this.cargoDAO = cargoDAO;
        this.workDetailsDAO = workDetailsDAO;
        this.orderDetailsDAO = orderDetailsDAO;
    }

    @Override
    @Transactional
    public WorkDetailsDTO readWorkDetailsById(long userId) {
        WorkDetailsDTO workDetails = workDetailsDAO.findWorkDetailsById(userId);
        workDetails.setDriversIds(workDetailsDAO.findDriversIds(workDetails.getTruckId()));
        workDetails.setWaypointsList(orderDetailsDAO.findWaypointsOfThisOrder(workDetails.getOrderId()));
        return workDetails;
    }



    @Override
    public void updateCargoStatus(List<Long> cargoIds) {
        for(long id : cargoIds) {
            Cargo cargo = cargoDAO.findCargoById(id);
            int index = cargo.getCargoStatus().ordinal() + 1;
            cargo.setCargoStatus(CargoStatus.values()[index]);
            cargoDAO.updateCargo(cargo);
        }
        //TODO: update order if all cargo are DELIVERED
    }

    @Override
    @Transactional
    public void updateShiftDetails(long id, ShiftDetailsDto shiftDetails)
            throws ShiftValidationException, DriverStatusNotFoundException {
        Driver driver = driverDAO.findDriverById(id);
        WorkShift shift = workDetailsDAO.findShiftById(id);

        DriverStatus status = driver.getDriverStatus();

        if(!shiftDetails.getDriverStatus().equals(status.name())) {
            status = convertToDriverStatus(shiftDetails.getDriverStatus());
        }
        if(!validateShiftAndDriverStatus(shiftDetails.isActive(), status)) {
            logger.error("Driver status and shift status conflict");
            throw new ShiftValidationException("Driver status and shift status conflict");
        }

        if(shift.isActive() != shiftDetails.isActive()) {
            shift = updateWorkShiftStatus(shiftDetails.isActive(), shift, driver);
            workDetailsDAO.updateWorkShift(shift);
        }

        driver.setDriverStatus(status);
        driverDAO.updateDriver(driver);
    }

    @Override
    public List<Long> readCoDriversIds(String id) {
        return workDetailsDAO.findDriversIds(id);
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

}


