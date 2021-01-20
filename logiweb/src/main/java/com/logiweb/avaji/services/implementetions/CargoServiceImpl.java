package com.logiweb.avaji.services.implementetions;

import com.logiweb.avaji.daos.CargoDAO;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.services.api.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoServiceImpl implements CargoService {

    private final CargoDAO cargoDAO;

    @Autowired
    public CargoServiceImpl(CargoDAO cargoDAO) {
        this.cargoDAO = cargoDAO;
    }


    @Override
    public List<Cargo> readAllFreeCargo() {
        return cargoDAO.findAllFreeCargo();
    }

    @Override
    public List<Cargo> readCargoByOrderId(long orderId) {
        return cargoDAO.findCargoByOrderId(orderId);
    }

    @Override
    public void createCargo(Cargo cargo) {
        throw new UnsupportedOperationException();
    }


}
