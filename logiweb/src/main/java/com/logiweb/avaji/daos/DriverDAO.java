package com.logiweb.avaji.daos;

import com.logiweb.avaji.dtos.DriverDTO;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.entities.models.WorkShift;
import com.logiweb.avaji.exceptions.DriverNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class DriverDAO {

    private static final Logger logger = LogManager.getLogger(DriverDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    public List<DriverDTO> findAllDrivers() {
        Query query = entityManager.createNamedQuery("Driver.findAllDrivers", DriverDTO.class);
        return query.getResultList();
    }

    public void saveDriver(Driver driver) {
        entityManager.persist(driver);
        entityManager.flush();
    }

    public void updateDriver(Driver updatedDriver) {
        entityManager.merge(updatedDriver);

    }

    public void updateDrivers(List<Driver> drivers) {
        for (Driver driver: drivers) {
            entityManager.merge(driver);
        }
    }


    public void deleteDriver(long driverId) {
        Driver driver = Optional.ofNullable(entityManager.find(Driver.class, driverId))
                .<DriverNotFoundException>orElseThrow(()  -> {
                    logger.error("Driver with ID {} not found", driverId);
                    throw new DriverNotFoundException("Driver by ID not found");
                });
        WorkShift workShift = entityManager.find(WorkShift.class, driverId);
        entityManager.remove(workShift);
        entityManager.remove(driver);
    }


    @Scheduled(cron = "0 0 0 1 */1 *")
    public void refreshWorkedHours() {
        Query query = entityManager.createNamedQuery("Driver.refreshWorkedHours");
        int rows = query.executeUpdate();
        logger.debug("Worked Hours success refreshed on {} rows", rows);
    }


    public void saveWorkShift(long id) {
        Driver driver = entityManager.find(Driver.class, id);
        WorkShift workShift = new WorkShift();
        workShift.setDriver(driver);
        entityManager.persist(workShift);
        entityManager.flush();
    }

    public DriverDTO findDriverById(long id) {
        TypedQuery<DriverDTO> query = entityManager.createNamedQuery("Driver.findDriverById", DriverDTO.class)
                .setParameter("id", id);
        return query.getSingleResult();
    }

    public List<Driver> findDriversByIds(List<Long> driversIds) {
        TypedQuery<Driver> query = entityManager.createNamedQuery("Driver.findDriversByIds", Driver.class)
                .setParameter("driversIds", driversIds);
        return query.getResultList();
    }

    public Driver findDriverEntity(long driverId) {
        return entityManager.find(Driver.class, driverId);
    }
}
