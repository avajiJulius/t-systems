package com.logiweb.avaji.services;

import com.logiweb.avaji.entities.models.Truck;

import java.util.List;

/**
 * Service for manipulations with truck list in database.
 * That service provide methods for create, read,
 * update and delete truck from trucks list from database;
 */
public interface TruckService {
    /**
     * The <code>createTruck</code> create and insert truck entity into database.
     *
     * @param truck entity.
     */
    void createTruck(Truck truck);

    /**
     * The <code>readTrucks</code> read all trucks from database.
     *
     * @return list of trucks entities.
     */
    List<Truck> readTrucks();

    /**
     * The <code>updateTruck</code> create updated truck entity
     * and replace existing entity with this updated entity.
     *
     * @param updatedTruck entity.
     */
    void updateTruck(Truck updatedTruck);

    /**
     * The <code>deleteTruck</code> delete truck entity from the database by truck id.
     *
     * @param truckID unique identifier for trucks.
     */
    void deleteTruck(Long truckID);
}
