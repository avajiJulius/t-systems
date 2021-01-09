package com.logiweb.avaji.crud.driver.service.api;

import com.logiweb.avaji.crud.driver.dto.DriverDTO;
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
     * The <code>readDrivers</code> read all drivers from database and convert driver entities
     * into DriverPublicResponseDto.
     *
     * @return list of DriverPublicResponseDto.
     */
    List<DriverDTO> readAllDrivers();

    /**
     * The <code>createDriver</code> create and insert driver entity into database.
     *
     * @param driver entity.
     */
    void createDriver(DriverDTO driver);


    /**
     * The <code>updateDriver</code> create updated driver entity
     * and replace existing entity with this updated entity.
     *
     * @param updatedDriver entity.
     */
    void updateDriver(DriverDTO updatedDriver);

    /**
     * The <code>deleteDriver</code> delete driver entity from the database by driver id.
     *
     * @param driverId unique identifier for drivers.
     */
    void deleteDriver(long driverId);


}
