package com.logiweb.avaji.dao;

import com.logiweb.avaji.dtos.DriverDTO;
import com.logiweb.avaji.dtos.ShiftDetailsDTO;
import com.logiweb.avaji.entitie.model.Driver;
import com.logiweb.avaji.entitie.model.WorkShift;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ShiftDetailsDAO {
    
    @PersistenceContext
    private EntityManager entityManager;


    public ShiftDetailsDTO findShiftDetails(long driverId) {
        TypedQuery<ShiftDetailsDTO> query = entityManager.createNamedQuery("WorkShift.findShiftDetails", ShiftDetailsDTO.class)
                .setParameter("id", driverId);
        return query.getSingleResult();
    }

    public void updateShiftDetails(ShiftDetailsDTO shiftDetails) {
        WorkShift workShift = entityManager.find(WorkShift.class, shiftDetails.getId());
        workShift.setActive(shiftDetails.isShiftActive());
        workShift.setStart(shiftDetails.getStart());
        workShift.setEnd(shiftDetails.getEnd());

        Driver driver = entityManager.find(Driver.class, shiftDetails.getId());
        driver.setDriverStatus(shiftDetails.getDriverStatus());

        workShift.setDriver(driver);
        entityManager.merge(workShift);
    }

    public void updateShiftDetailsOnCompletedOrder(long id) {
        String truckId = entityManager.createNamedQuery("Driver.findTruckIdByDriver", String.class)
                .setParameter("id", id).getSingleResult();
        entityManager.createNamedQuery("Truck.updateOnCompletedOrder")
                .setParameter("id", truckId).executeUpdate();
        entityManager.createNamedQuery("Driver.updateOnCompletedOrder")
                .setParameter("id", id).executeUpdate();
    }

    public void updateWorkedHours(long id, double hoursWorked) {
        List<DriverDTO> drivers = entityManager.createNamedQuery("Driver.findDriversByOrderId", DriverDTO.class)
                .setParameter("id", id).getResultList();

        for(DriverDTO driverDTO: drivers) {
            Driver driver = entityManager.find(Driver.class, driverDTO.getId());
            double updatedHours = driver.getHoursWorked() + hoursWorked;
            driver.setHoursWorked(updatedHours);
            entityManager.merge(driver);
        }
    }
}
