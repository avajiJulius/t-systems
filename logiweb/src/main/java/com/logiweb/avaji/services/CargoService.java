package com.logiweb.avaji.services;

import com.logiweb.avaji.entities.models.Cargo;

import java.util.List;

public interface CargoService {

    /**
     * The <code>readCargoByOrderId</code> read list of cargo of specified order by order id;
     *
     * @param orderId
     * @return list or cargo of specified order
     */
    List<Cargo> readCargoByOrderId(Long orderId);

    /**
     * The <code>createCargo</code> create and check the valid of cargo entity.
     *
     * @param cargo
     */
    void createCargo(Cargo cargo);
}
