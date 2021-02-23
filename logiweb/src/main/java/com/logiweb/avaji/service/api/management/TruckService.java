package com.logiweb.avaji.service.api.management;

import com.logiweb.avaji.dtos.TruckDTO;

import java.util.List;

/**
 * Service for manipulations with truck list in database.
 * That service provide methods for create, read,
 * update and delete truck from trucks list from database;
 */
public interface TruckService {
    /**
     * The <code>createTruck</code> convert truckDTO into Truck entity
     * and insert into database.
     *
     * @param truckDto
     */
    boolean createTruck(TruckDTO truckDto);

    /**
     * The <code>readTrucksPage</code> read trucks dtos
     *
     * @param pageNumber
     * @param pageSize size of returned list of dtos
     * @return list with size(pageSize) of trucks dtos from (pageNumber * pageSize) index from database
     */
    List<TruckDTO> readTrucksPage(int pageNumber, int pageSize);

    /**
     * The <code>readTruckById</code> read truck from database by id and convert
     * it into truckDTO.
     *
     * @param truckID unique identifier for truck.
     * @return truckDto
     */
    TruckDTO readTruckById(String truckID);


    /**
     * Calculate free space in truck shift by finding all drivers who have this truck entity and
     * subtract form truck shift size.
     *
     * @param orderId
     * @return free shift size.
     */
    int calculateFreeSpaceInShift(long orderId);

    /**
     * The <code>updateTruck</code> create updated truck entity
     * by convert truckDto to Truck entity and update entity in database.
     *
     * @param updatedTruck truckDto
     */
    void updateTruck(String truckId, TruckDTO updatedTruck);

    /**
     * The <code>deleteTruck</code> delete truck entity from the database by truck id.
     *
     * @param truckID unique identifier for trucks.
     */
    boolean deleteTruck(String truckID);

    /**
     * The <code>getTrucksTotalNumbers</code> get total rows of all trucks
     *
     * @return total number of rows in database
     */
    long getTrucksTotalNumbers();

}
