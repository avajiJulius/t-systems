package com.logiweb.avaji.crud.driver.dao;

import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.exceptions.DriverNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class DriverDAO {

    private static final Logger logger = LogManager.getLogger(DriverDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    public List<Driver> findAllDrivers() {
        Query query = entityManager.createNamedQuery("Driver.findAllDrivers");
        return query.getResultList();
    }

    public Driver findDriverById(long driverId) {
        return Optional.ofNullable(entityManager.find(Driver.class, driverId))
                .<DriverNotFoundException>orElseThrow(()  -> {
                    logger.error("Driver with ID {} not found", driverId);
                    throw new DriverNotFoundException("Driver by ID not found");
                });
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
        entityManager.remove(driver);
    }


    public List<Driver> findDriverForOrder(Double shiftHours, long cityCode) {
        TypedQuery<Driver> query = entityManager.createNamedQuery("Driver.findDriversForOrder", Driver.class)
                .setParameter("shiftHours", shiftHours).setParameter("cityCode", cityCode);
        return Optional.ofNullable(query.getResultList())
                .<DriverNotFoundException>orElseThrow(()  -> {
                    logger.error("Free Drivers with rest of worked hours less then {} " +
                            "and city code {} not found", shiftHours, cityCode);
                    throw new DriverNotFoundException("Driver by such parameters not found");
                });
    }

    @Scheduled(cron = "0 0 0 1 */1 *")
    public void refreshWorkedHours() {
        Query query = entityManager.createNamedQuery("Driver.refreshWorkedHours");
        int rows = query.executeUpdate();
        logger.debug("Worked Hours success refreshed on {} rows", rows);
    }

    public List<Driver> findDriversByTruckId(String truckId) {
        TypedQuery<Driver> query = entityManager.createNamedQuery("Driver.findDriversByTruckId", Driver.class)
                .setParameter("truckId", truckId);
        return Optional.ofNullable(query.getResultList())
                .<DriverNotFoundException>orElseThrow(() -> {
                    logger.error("Drivers with truck ID {} not found", truckId);
                    throw new DriverNotFoundException("Drivers by truck ID not found");
                });
    }


}
