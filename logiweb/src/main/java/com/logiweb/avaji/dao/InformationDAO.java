package com.logiweb.avaji.dao;

import com.logiweb.avaji.dtos.OrderDTO;
import com.logiweb.avaji.dtos.TruckDTO;
import com.logiweb.avaji.dtos.mq.DriverInfo;
import com.logiweb.avaji.dtos.mq.InfoOrderDTO;
import com.logiweb.avaji.dtos.mq.OrderInfo;
import com.logiweb.avaji.dtos.mq.TruckInfo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class InformationDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public DriverInfo getDriverInformation() {
        long total = entityManager.createNamedQuery("Driver.countDrivers", Long.class).getSingleResult();
        long available = entityManager.createNamedQuery("Driver.countAvailableDrivers", Long.class).getSingleResult();
        long unavailable = total - available;

        return new DriverInfo.Builder()
                .withTotalNumber(total)
                .withAvailableNumber(available)
                .withUnavailableNumber(unavailable)
                .build();
    }

    public OrderInfo getOrderInformation() {
        List<InfoOrderDTO> result = entityManager.createNamedQuery("Order.findAllInfoOrders", InfoOrderDTO.class)
                .setFirstResult(0).setMaxResults(10).getResultList();
        OrderInfo info = new OrderInfo();
        if (!result.isEmpty()) {
            info.setLastOrdersByList(result);
        }
        return info;
    }

    public TruckInfo getTruckInformation() {
        List<TruckDTO> trucks = entityManager.createNamedQuery("Truck.findAllTrucks", TruckDTO.class).getResultList();
        int total = trucks.size();
        long inUse = trucks.stream().filter(t -> t.isInUse()).count();
        long available = trucks.stream().filter(t -> t.isServiceable() && !t.isInUse()).count();
        long faulty = trucks.stream().filter(t -> !t.isServiceable()).count();

        return new TruckInfo.Builder()
                .withTotal(total)
                .withAvailable(available)
                .withInUse(inUse)
                .withFaulty(faulty)
                .build();
    }
}
