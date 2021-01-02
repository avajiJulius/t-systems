package com.logiweb.avaji.crud.driver.service.api;

import com.logiweb.avaji.crud.driver.dto.DriverPrivateResponseDto;
import com.logiweb.avaji.crud.driver.dto.DriverPublicResponseDto;
import com.logiweb.avaji.entities.enums.DriverStatus;
import com.logiweb.avaji.entities.models.Driver;

import java.util.List;

/**
 * Service for manipulations with drivers list in database.
 * That service provide methods for create, read,
 * update and delete driver from drivers list from database;
 */
public interface DriverService {
    /**
     * The <code>createDriver</code> create and insert driver entity into database.
     *
     * @param driver entity.
     */
    void createDriver(Driver driver);

    /**
     * The <code>readDrivers</code> read all drivers from database and convert driver entities
     * into DriverPublicResponseDto.
     *
     * @return list of DriverPublicResponseDto.
     */
    List<DriverPublicResponseDto> readDrivers();

    /**
     * The <code>readDriverById</code> read driver with current driverId.
     *
     * @return DriverPrivateResponseDto
     * @see DriverPrivateResponseDto
     */
    DriverPrivateResponseDto readDriverById(long driverId);

    /**
     * The <code>updateDriver</code> create updated driver entity
     * and replace existing entity with this updated entity.
     *
     * @param updatedDriver entity.
     */
    void updateDriver(long driverId,Driver updatedDriver);

    /**
     * The <code>deleteDriver</code> delete driver entity from the database by driver id.
     *
     * @param driverId unique identifier for drivers.
     */
    void deleteDriver(long driverId);


    /**
     * The <code>updateDriverStatus</code> update status for driver.
     *
     * @param driverStatus for change.
     */
    void updateDriverStatus(DriverStatus driverStatus);

    /**
     * The <code>updateWorkShiftStatus</code> change worked shift status
     * to start if status is stop and vice versa.
     *
     */
    void updateWorkShiftStatus();

}
