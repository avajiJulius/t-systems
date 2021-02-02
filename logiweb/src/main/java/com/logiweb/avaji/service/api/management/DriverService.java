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


    List<DriverDTO> readDriversPage(int pageNumber, int page_size);


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

    long getDriversTotalNumber();

    List<Driver> readDriversByIds(List<Long> driversIds);

    void updateDrivers(List<Driver> drivers);
}
