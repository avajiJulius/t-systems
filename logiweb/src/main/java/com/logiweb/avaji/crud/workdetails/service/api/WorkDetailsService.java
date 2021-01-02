package com.logiweb.avaji.crud.workdetails.service.api;

import com.logiweb.avaji.crud.workdetails.dto.WorkDetailsDto;

public interface WorkDetailsService {
    WorkDetailsDto readWorkDetailsByUserId(long userId);
    void updateWorkShiftStatus(boolean isActive, long userId);
    void updateDriverStatus(String status, long driverId);
    void updateCargoStatus(String status, long cargoId);
}
