package com.logiweb.avaji.services;

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
     * The <code>readDrivers</code> read all drivers from database.
     *
     * @return list of drivers entities.
     */
    List<Driver> readDrivers();

    /**
     * The <code>updateDriver</code> create updated driver entity
     * and replace existing entity with this updated entity.
     *
     * @param updatedDriver entity.
     */
    void updateDriver(Driver updatedDriver);

    /**
     * The <code>deleteDriver</code> delete driver entity from the database by driver id.
     *
     * @param driverID unique identifier for drivers.
     */
    void deleteDriver(String driverID);
}
