package com.logiweb.avaji.services.api.profile;


import com.logiweb.avaji.dtos.ShiftDetailsDTO;
import com.logiweb.avaji.entities.enums.DriverStatus;
import com.logiweb.avaji.exceptions.ShiftValidationException;

public interface ShiftDetailsService {
    ShiftDetailsDTO changeShiftDetails(long id, DriverStatus status) throws ShiftValidationException;
    ShiftDetailsDTO readShiftDetails(long driverId);
    void finishShiftOfCompletedOrder(long id) throws ShiftValidationException;
}
