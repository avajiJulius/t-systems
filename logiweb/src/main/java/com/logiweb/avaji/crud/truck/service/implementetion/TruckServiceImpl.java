package com.logiweb.avaji.crud.truck.service.implementetion;

import com.logiweb.avaji.crud.order.dao.OrderDAO;
import com.logiweb.avaji.crud.truck.dto.TruckDTO;
import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.crud.truck.dao.TruckDAO;
import com.logiweb.avaji.crud.truck.service.api.TruckService;
import com.logiweb.avaji.orderdetails.service.implementetion.ShiftDetailsServiceImpl;
import com.logiweb.avaji.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
public class TruckServiceImpl implements TruckService {

    @PersistenceContext
    private EntityManager entityManager;

    private final TruckDAO truckDAO;
    private final OrderDAO orderDAO;
    private final ShiftDetailsServiceImpl computingService;
    private final Mapper converter;

    @Autowired
    public TruckServiceImpl(TruckDAO truckDAO, OrderDAO orderDAO,
                            ShiftDetailsServiceImpl computingService, Mapper converter) {
        this.truckDAO = truckDAO;
        this.orderDAO = orderDAO;
        this.computingService = computingService;
        this.converter = converter;
    }

    @Override
    public void createTruck(TruckDTO truckDto) {
        Truck truck = converter.dtoToTruck(truckDto);

        truckDAO.saveTruck(truck);
    }


    @Override
    public List<TruckDTO> readTrucks() {
        return truckDAO.findTrucks();
    }

    @Override
    public TruckDTO readTruckById(String truckID) {
        return truckDAO.findTruckById(truckID);
    }


    @Override
    public void updateTruck(String truckId, TruckDTO updatedTruck) {
        updatedTruck.setTruckId(truckId);
        Truck truck = converter.dtoToTruck(updatedTruck);

        truckDAO.updateTruck(truck);
    }


    @Override
    public void deleteTruck(String truckID) {
        truckDAO.deleteTruck(truckID);
    }

}
