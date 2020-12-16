package com.logiweb.avaji.services;

import com.logiweb.avaji.entities.enums.DriverStatus;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.views.DriverResponse;

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
     * The <code>readDriverById</code> read driver with current driverId.
     *
     * @return driver response with driverId, set of co-drivers ids, truck id,
     * order id, set of waypoints.
     */
    DriverResponse readDriverById(String driverId);

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

    /**
     * The <code>readAndAssignDriverForTruck</code> take driver if workedHour limit
     * will not be exceeded, driver status is <code>REST</code>, and the truck current city
     * is the same as driver current city.
     *
     * @param truck on the basis of which the selection is conducted.
     */
    void readAndAssignDriverForTruck(Truck truck);

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
