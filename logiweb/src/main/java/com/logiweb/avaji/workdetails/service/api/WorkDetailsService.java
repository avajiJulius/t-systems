package com.logiweb.avaji.workdetails.service.api;

import com.logiweb.avaji.workdetails.dto.WorkDetailsDto;

public interface WorkDetailsService {
    WorkDetailsDto readWorkDetailsByDriverId(Integer driverId);
    void updateWorkShiftStatus(boolean isActive, Integer workShiftId);
    void updateDriverStatus(String status, Integer driverId);
    void updateCargoStatus(String status, Integer cargoId);
}
