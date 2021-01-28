package com.logiweb.avaji.service.api.management;

import com.logiweb.avaji.entitie.model.Cargo;

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
    List<Cargo> readAllFreeCargo();

    /**
     * The <code>readCargoByOrderId</code> read list of cargo of certain order specified by order id;
     *
     * @param orderId
     * @return list or cargo of certain order.
     */
    List<Cargo> readCargoByOrderId(long orderId);

    /**
     * The <code>createCargo</code> create and check the valid of cargo entity.
     *
     * @param cargo
     */
    void createCargo(Cargo cargo);
}
