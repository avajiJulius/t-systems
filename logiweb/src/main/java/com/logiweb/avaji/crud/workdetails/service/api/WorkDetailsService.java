package com.logiweb.avaji.crud.workdetails.service.api;


import com.logiweb.avaji.crud.workdetails.dto.ShiftDetailsDto;
import com.logiweb.avaji.crud.workdetails.dto.WorkDetailsDto;
import com.logiweb.avaji.exceptions.ShiftValidationException;

public interface WorkDetailsService {
    WorkDetailsDto readWorkDetailsByUserId(long userId);
    void updateCargoStatus(String status, long cargoId);
    void updateShiftDetails(long id, ShiftDetailsDto shiftDetails) throws ShiftValidationException;
}
