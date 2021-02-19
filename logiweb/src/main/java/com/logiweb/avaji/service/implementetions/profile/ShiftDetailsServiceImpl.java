package com.logiweb.avaji.service.implementetions.profile;

import com.logiweb.avaji.dao.ShiftDetailsDAO;
import com.logiweb.avaji.dtos.ShiftDetailsDTO;
import com.logiweb.avaji.entity.enums.DriverStatus;
import com.logiweb.avaji.exception.ShiftValidationException;
import com.logiweb.avaji.service.api.mq.InformationProducerService;
import com.logiweb.avaji.service.api.profile.ShiftDetailsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ShiftDetailsServiceImpl implements ShiftDetailsService {

    private static final Logger logger = LogManager.getLogger(ShiftDetailsServiceImpl.class);

    private final ShiftDetailsDAO shiftDetailsDAO;
    private final InformationProducerService informationService;

    @Autowired
    public ShiftDetailsServiceImpl(ShiftDetailsDAO shiftDetailsDAO, InformationProducerService informationService) {
        this.shiftDetailsDAO = shiftDetailsDAO;
        this.informationService = informationService;
    }

    @Override
    public ShiftDetailsDTO readShiftDetails(long driverId) {
        return shiftDetailsDAO.findShiftDetails(driverId);
    }

    @Override
    @Transactional
    public ShiftDetailsDTO changeShiftDetails(long id, DriverStatus driverStatus) {
        ShiftDetailsDTO shiftDetails = shiftDetailsDAO.findShiftDetails(id);

        updateShiftDetails(shiftDetails, driverStatus);
        informationService.updateDriverInformation();
        informationService.sendInformation();

        return shiftDetailsDAO.findShiftDetails(id);
    }

    @Override
    @Transactional
    public void finishShift(long id){
        ShiftDetailsDTO shiftDetails = shiftDetailsDAO.findShiftDetails(id);

        if(shiftDetails.isShiftActive() && shiftDetails.getDriverStatus() != DriverStatus.REST) {
            updateShiftDetails(shiftDetails, DriverStatus.REST);
        }

        logger.info("Driver by id {} finish shift of completed order", id);
    }

    @Override
    public void updateWorkedHours(long id, double hoursWorked) {
        shiftDetailsDAO.updateWorkedHours(id, hoursWorked);
    }

    private void updateShiftDetails(ShiftDetailsDTO shiftDetails, DriverStatus status) {
        setParametersToShiftDetails(shiftDetails, status);

        if(!shiftAndDriverStatusValid(shiftDetails.isShiftActive(), shiftDetails.getDriverStatus())) {
            logger.error("Driver status and shift status conflict");
            throw new ShiftValidationException("Driver status and shift status conflict");
        }

        shiftDetailsDAO.updateShiftDetails(shiftDetails);
        logger.info("Shift details of id {} updated", shiftDetails.getId());
    }


    private void setParametersToShiftDetails(ShiftDetailsDTO shiftDetails, DriverStatus status) {
        boolean activeDriverStatus = driverStatusIsActive(status);

        if (activeDriverStatus != shiftDetails.isShiftActive()) {
            updateWorkShift(shiftDetails, activeDriverStatus);
        }
        shiftDetails.setDriverStatus(status);
    }

    private void updateWorkShift(ShiftDetailsDTO shiftDetails, boolean isStatusActive) {
        if(isStatusActive) {
            shiftDetails.setShiftActive(true);
            shiftDetails.setStart(LocalDateTime.now());
            shiftDetails.setEnd(null);
        } else {
            shiftDetails.setEnd(LocalDateTime.now());
            shiftDetails.setShiftActive(false);
        }
    }

    private boolean driverStatusIsActive(DriverStatus status) {
        return status.ordinal() > 0;
    }

    public boolean shiftAndDriverStatusValid(boolean active, DriverStatus status) {
        return !active ? status.ordinal() == 0 : status.ordinal() > 0;
    }




}


