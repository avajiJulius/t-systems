package com.logiweb.avaji.service.api.management;

import com.logiweb.avaji.dtos.DriverDTO;
import com.logiweb.avaji.entity.model.Driver;

import java.util.List;

/**
 * Service for manipulations with drivers list in database.
 * That service provide methods for create, read,
 * update and delete driver from drivers list from database;
 */
public interface DriverService {

    /**
     * The <code>readDriversPage</code> read Driver dtos
     *
     * @param pageNumber number
     * @param pageSize number of drivers was found
     * @return list of Driver dtos with list size <code>pageSize</code> and start place from database
     * <code>(pageNumber * pageSize)</code>
     */

    List<DriverDTO> readDriversPage(int pageNumber, int pageSize);


    /**
     * The <code>createDriver</code> create and insert driver entity into database
     * by converting driver dto.
     *
     * @param driver dto.
     */
    boolean createDriver(DriverDTO driver);


    /**
     * The <code>updateDriver</code> create updated driver entity by converting dto
     * and replace existing entity with this updated entity.
     *
     * @param id for search existing driver entity.
     * @param updatedDriver dto.
     */
    void updateDriver(long id,DriverDTO updatedDriver);


    void updateDriver(Driver driver);

    /**
     * The <code>deleteDriver</code> delete driver entity from the database by driver id.
     *
     * @param driverId unique identifier for drivers.
     */
    boolean deleteDriver(long driverId);


    /**
     * The <code>readDriverById</code> read driver dto by id
     *
     * @param id
     * @return driver dto
     */
    DriverDTO readDriverById(long id);

    /**
     * The <code>getDriversTotalNumber</code> get total rows of all drivers
     *
     * @return total number of rows in database
     */
    long getDriversTotalNumber();


    /**
     * The <code>readDriversByIds</code> get drivers entities of ids
     *
     * @param driversIds
     * @return drivers entities
     */
    List<Driver> readDriversByIds(List<Long> driversIds);


    /**
     * The <code>updateDrivers</code> update all drivers
     *
     * @param drivers updated drivers entities
     */
    void updateDrivers(List<Driver> drivers);
}
