package com.logiweb.avaji.dao;

import com.logiweb.avaji.dtos.TruckDTO;
import com.logiweb.avaji.dtos.mq.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
        OrderInfo info = new OrderInfo();

        List<InfoOrderDTO> result = entityManager.createNamedQuery("Order.findAllInfoOrders", InfoOrderDTO.class)
                .setFirstResult(0).setMaxResults(10).getResultList();

        if (!result.isEmpty()) {
            info.setLastOrdersByList(result);
        }

        return info;
    }

    public TruckInfo getTruckInformation() {
        List<TruckDTO> trucks = entityManager.createNamedQuery("Truck.findAllTrucks", TruckDTO.class).getResultList();

        int total = trucks.size();
        long inUse = trucks.stream().filter(TruckDTO::isInUse).count();
        long available = trucks.stream().filter(t -> t.isServiceable() && !t.isInUse()).count();
        long faulty = trucks.stream().filter(t -> !t.isServiceable()).count();

        return new TruckInfo.Builder()
                .withTotal(total)
                .withAvailable(available)
                .withInUse(inUse)
                .withFaulty(faulty)
                .build();
    }

    public List<InfoDriverDTO> findDriversOfOrder(long orderId) {
        TypedQuery<InfoDriverDTO> query = entityManager.createNamedQuery("Driver.findInfoDriversByOrderId", InfoDriverDTO.class)
                .setParameter("id", orderId);

        return query.getResultList();
    }
}
