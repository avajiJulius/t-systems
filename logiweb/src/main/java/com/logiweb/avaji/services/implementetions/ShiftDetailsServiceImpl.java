package com.logiweb.avaji.services.implementetions;

import com.logiweb.avaji.daos.ShiftDetailsDAO;
import com.logiweb.avaji.dtos.ShiftDetailsDTO;
import com.logiweb.avaji.entities.enums.DriverStatus;
import com.logiweb.avaji.exceptions.DriverStatusNotFoundException;
import com.logiweb.avaji.exceptions.ShiftValidationException;
import com.logiweb.avaji.services.api.ShiftDetailsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class ShiftDetailsServiceImpl implements ShiftDetailsService {

    private static final Logger logger = LogManager.getLogger(ShiftDetailsServiceImpl.class);

    private final ShiftDetailsDAO shiftDetailsDAO;

    @Autowired
    public ShiftDetailsServiceImpl(ShiftDetailsDAO shiftDetailsDAO) {
        this.shiftDetailsDAO = shiftDetailsDAO;
    }

    @Override
    public ShiftDetailsDTO readShiftDetails(long driverId) {
        return shiftDetailsDAO.findShiftDetails(driverId);
    }

    @Override
    @Transactional
    public void updateShiftDetails(ShiftDetailsDTO updateDetails)
            throws ShiftValidationException, DriverStatusNotFoundException {
        ShiftDetailsDTO shiftDetails = shiftDetailsDAO.findShiftDetails(updateDetails.getId());

        if(!validateShiftAndDriverStatus(updateDetails.isShiftActive(), updateDetails.getDriverStatus())) {
            logger.error("Driver status and shift status conflict");
            throw new ShiftValidationException("Driver status and shift status conflict");
        }

        if(updateDetails.isShiftActive() != shiftDetails.isShiftActive()) {
            updateWorkShift(shiftDetails, updateDetails);
        }

        shiftDetails.setDriverStatus(updateDetails.getDriverStatus());

        shiftDetailsDAO.updateShiftDetails(shiftDetails);
    }


    public boolean validateShiftAndDriverStatus(boolean active, DriverStatus status) {
        return !active ? status.ordinal() == 0 : status.ordinal() > 0;
    }

    public void updateWorkShift(ShiftDetailsDTO shiftDetails, ShiftDetailsDTO updateDetails) {
        if(updateDetails.isShiftActive() == true) {
            shiftDetails.setShiftActive(true);
            shiftDetails.setStart(LocalDateTime.now());
            shiftDetails.setEnd(null);
        } else {
            shiftDetails.setEnd(LocalDateTime.now());
            shiftDetails.setShiftActive(false);
            updateHoursWorked(shiftDetails);
        }
    }

    private void updateHoursWorked(ShiftDetailsDTO shiftDetails) {
        long shiftHours = ChronoUnit.HOURS.between(shiftDetails.getStart(), shiftDetails.getEnd());
        shiftDetails.setHoursWorked((double)shiftHours);
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


