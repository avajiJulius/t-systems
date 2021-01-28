package com.logiweb.avaji.service.api.profile;


import com.logiweb.avaji.dtos.ShiftDetailsDTO;
import com.logiweb.avaji.entitie.enums.DriverStatus;
import com.logiweb.avaji.exception.ShiftValidationException;


/**
 * This service manipulate with ShiftDetails and update related entities.
 *
 */
public interface ShiftDetailsService {

    /**
     * Update DriverStatus and shift activity. If its start of the shift add start time to it, else it is end of shift
     * calculate and add hours worked and end time.
     *
     * @param id of ShiftDetails
     * @param status for update
     * @return updated ShiftDetailsDTO
     * @throws ShiftValidationException if DriverStatus and shift activity have conflict.
     */
    ShiftDetailsDTO changeShiftDetails(long id, DriverStatus status) throws ShiftValidationException;

    /**
     * Read shift details by driver id
     *
     * @param driverId
     * @return ShiftDetailsDTO
     */
    ShiftDetailsDTO readShiftDetails(long driverId);

    /**
     * While order complete this method end shift, change DriverStatus to REST
     * and detach orderDetails and truck from driver.
     *
     * @param id by finding Shift Details
     * @throws ShiftValidationException if DriverStatus and shift activity have conflict.
     */
    void finishShiftOfCompletedOrder(long id) throws ShiftValidationException;

    /**
     * Update hours worked for driver
     *
     * @param id
     * @param hoursWorked
     */
    void updateWorkedHours(long id, double hoursWorked);

}
