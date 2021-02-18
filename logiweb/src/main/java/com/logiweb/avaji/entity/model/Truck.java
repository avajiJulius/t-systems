package com.logiweb.avaji.entity.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trucks")
@NamedQuery(name = "Truck.findAllTrucks",
        query = "select new com.logiweb.avaji.dtos.TruckDTO(" +
                "t.truckId, t.capacity, t.shiftSize, t.serviceable, " +
                "c.cityCode, c.cityName, t.inUse) from Truck t " +
                "join t.currentCity c", lockMode = LockModeType.READ)
@NamedQuery(name = "Truck.findTruckById",
        query = "select new com.logiweb.avaji.dtos.TruckDTO(" +
                "t.truckId, t.version, t.capacity, t.shiftSize, t.serviceable, " +
                "t.currentCity.cityCode, t.currentCity.cityName, t.inUse) from Truck t " +
                "where t.truckId = :id", lockMode = LockModeType.READ)
@NamedQuery(name = "Truck.findTrucksForOrder",
        query = "select new com.logiweb.avaji.dtos.TruckDTO(" +
                "t.truckId, t.version, t.capacity, t.shiftSize, t.serviceable, " +
                "t.currentCity.cityCode, t.currentCity.cityName, t.inUse) from Truck t " +
                "where t.serviceable = true " +
                "and t.capacity >= :maxCapacity " +
                "and t.currentCity.cityCode = :startCode " +
                "and t.inUse = false ", lockMode = LockModeType.READ)
@NamedQuery(name = "Truck.findTruckByDriverId",
        query = "select d.currentTruck from Driver d " +
                "where d.id = :driverId")
@NamedQuery(name = "Truck.findTruckByOrderId",
        query = "select new com.logiweb.avaji.dtos.TruckDTO(" +
                "t.truckId, t.version, t.capacity, t.shiftSize, t.serviceable, " +
                "t.currentCity.cityCode, t.currentCity.cityName, t.inUse) from Truck t " +
                "where t.truckId = (select o.designatedTruck.truckId " +
                "from Order o where o.id = :id)", lockMode = LockModeType.READ)
@NamedQuery(name = "Truck.updateTruckOnCityChange",
        query = "update Truck t set t.currentCity = " +
                "(select c from City c where c.cityCode = :cityCode) " +
                "where t.truckId = (select o.designatedTruck.truckId from Order o where o.id = :id)")
@NamedQuery(name = "Truck.updateOnCompletedOrder",
        query = "update Truck t set t.inUse = false " +
                "where t.truckId = :id")
@NamedQuery(name = "Truck.countTrucks",
            query = "select count(t) from Truck t")
@NamedQuery(name = "Truck.countTruckById",
            query = "select count(1) from Truck t where t.truckId = :id")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Truck {
    @Id
    @Column(name = "id")
    private String truckId;
    @Version
    @Column(name = "version")
    private int version;
    @Column(name= "shift_size")
    private int shiftSize;
    @Column(name = "capacity")
    private double capacity;
    @Column(name = "serviceable")
    private boolean serviceable;
    @Column(name = "in_use")
    private boolean inUse;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "city_code")
    private City currentCity;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY,
            mappedBy = "designatedTruck")
    private Order currentOrder;
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY,
            mappedBy = "currentTruck")
    private List<Driver> drivers = new ArrayList<>();

    @Override
    public String toString() {
        return "Truck{" +
                "truckId='" + truckId + '\'' +
                ", version=" + version +
                ", shiftSize=" + shiftSize +
                ", capacity=" + capacity +
                ", serviceable=" + serviceable +
                ", currentCity=" + currentCity +
                ", currentOrder=" + currentOrder +
                '}';
    }
}
