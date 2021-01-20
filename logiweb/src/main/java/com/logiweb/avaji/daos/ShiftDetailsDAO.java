package com.logiweb.avaji.daos;

import com.logiweb.avaji.dtos.ShiftDetailsDTO;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.WorkShift;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
        driver.setHoursWorked(driver.getHoursWorked() + shiftDetails.getHoursWorked());
        driver.setDriverStatus(shiftDetails.getDriverStatus());

        workShift.setDriver(driver);
        entityManager.merge(workShift);
    }
}
