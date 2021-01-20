package com.logiweb.avaji.services.api;


import com.logiweb.avaji.dtos.ShiftDetailsDTO;
import com.logiweb.avaji.exceptions.DriverStatusNotFoundException;
import com.logiweb.avaji.exceptions.ShiftValidationException;

public interface ShiftDetailsService {
    void updateShiftDetails(ShiftDetailsDTO shiftDetails) throws ShiftValidationException, DriverStatusNotFoundException;
    ShiftDetailsDTO readShiftDetails(long driverId);
}
