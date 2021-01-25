package com.logiweb.avaji.services.api;


import com.logiweb.avaji.dtos.ShiftDetailsDTO;
import com.logiweb.avaji.exceptions.DriverStatusNotFoundException;
import com.logiweb.avaji.exceptions.ShiftValidationException;

public interface ShiftDetailsService {
    ShiftDetailsDTO updateShiftDetails(ShiftDetailsDTO shiftDetails) throws ShiftValidationException;
    ShiftDetailsDTO readShiftDetails(long driverId);
}
