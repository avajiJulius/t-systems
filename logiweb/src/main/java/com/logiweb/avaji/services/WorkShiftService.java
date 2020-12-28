package com.logiweb.avaji.services;

public interface WorkShiftService {
    void updateWorkShiftStatus(boolean isActive, Integer driverId);
    void updateDriverStatus(String driverStatus, Integer driverId);
    void updateCargoStatus(String cargoStatus, Integer cargoId);
}
