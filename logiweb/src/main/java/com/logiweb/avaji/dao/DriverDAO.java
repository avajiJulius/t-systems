package com.logiweb.avaji.dao;

import com.logiweb.avaji.entities.enums.DriverStatus;
import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.Truck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class DriverDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Driver> findAllDrivers() {
        Query query = entityManager.createNamedQuery("Driver.findAllDrivers");
        return query.getResultList();
    }

    public Driver findDriverById(Integer driverId) {
        return entityManager.find(Driver.class, driverId);
    }

    public void saveDriver(Driver driver) {
        entityManager.persist(driver);
        entityManager.flush();
    }

    public void updateDriver(Driver updatedDriver) {
        entityManager.merge(updatedDriver);
    }


    public void deleteDriver(Integer driverId) {
        Driver driver = entityManager.find(Driver.class, driverId);
        entityManager.remove(driver);
    }


    public List<Driver> findDriverForOrder(Double shiftHours, Integer cityCode) {
        TypedQuery<Driver> query = entityManager.createNamedQuery("Driver.findDriversForOrder", Driver.class)
                .setParameter("shiftHours", shiftHours).setParameter("cityCode", cityCode);
        return query.getResultList();
    }
}
