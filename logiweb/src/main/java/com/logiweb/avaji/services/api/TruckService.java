package com.logiweb.avaji.services.api;

import com.logiweb.avaji.entities.dto.TruckDto;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.Truck;

import java.util.List;

/**
 * Service for manipulations with truck list in database.
 * That service provide methods for create, read,
 * update and delete truck from trucks list from database;
 */
public interface TruckService {
    /**
     * The <code>createTruck</code> convert truckDto into Truck entity
     * and insert into database.
     *
     * @param truckDto
     */
    void createTruck(TruckDto truckDto);

    /**
     * The <code>readTrucks</code> read all trucks from database.
     *
     * @return list of trucks entities.
     */
    List<Truck> readTrucks();

    /**
     * The <code>readTruckById</code> read truck from database by id and convert
     * it into truckDto.
     *
     * @param truckID unique identifier for truck.
     * @return truckDto
     */
    TruckDto readTruckById(String truckID);

    /**
     * The <code>readTrucksForOrder</code> read waypoints for order by orderId.
     * Count <code>maxCapacity</code> value and read all free and serviceable trucks
     * with capacity greater than <code>maxCapacity</code>.
     *
     * @param orderId
     * @return all free, serviceable and suitable for capacity.
     */
    List<Truck> readTrucksForOrder(Integer orderId);

    /**
     * The <code>updateTruck</code> create updated truck entity
     * by convert truckDto to Truck entity and update entity in database.
     *
     * @param updatedTruck truckDto
     */
    void updateTruck(TruckDto updatedTruck);

    /**
     * The <code>deleteTruck</code> delete truck entity from the database by truck id.
     *
     * @param truckID unique identifier for trucks.
     */
    void deleteTruck(String truckID);
}
