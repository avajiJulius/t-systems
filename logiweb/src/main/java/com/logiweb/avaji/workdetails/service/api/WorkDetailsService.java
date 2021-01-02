package com.logiweb.avaji.workdetails.service.api;

import com.logiweb.avaji.workdetails.dto.WorkDetailsDto;

public interface WorkDetailsService {
    WorkDetailsDto readWorkDetailsByDriverId(long driverId);
    void updateWorkShiftStatus(boolean isActive, long workShiftId);
    void updateDriverStatus(String status, long driverId);
    void updateCargoStatus(String status, long cargoId);
}
