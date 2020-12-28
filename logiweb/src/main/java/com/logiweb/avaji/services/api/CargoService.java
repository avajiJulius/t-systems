package com.logiweb.avaji.services.api;

import com.logiweb.avaji.entities.models.Cargo;

import java.util.List;

/**
 * This service responsible for working with cargo entities.
 *
 */
public interface CargoService {

    /**
     * The <code>readAllCargo</code> read all cargo from database and return list of cargo entities.
     *
     * @return list of cargo.
     */
    List<Cargo> readAllCargo();

    /**
     * The <code>readCargoByOrderId</code> read list of cargo of certain order specified by order id;
     *
     * @param orderId
     * @return list or cargo of certain order.
     */
    List<Cargo> readCargoByOrderId(Integer orderId);

    /**
     * The <code>createCargo</code> create and check the valid of cargo entity.
     *
     * @param cargo
     */
    void createCargo(Cargo cargo);
}
