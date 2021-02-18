package com.logiweb.avaji.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NamedQuery(name = "Order.findAllOrders",
query = "select new com.logiweb.avaji.dtos.OrderDTO(" +
        "o.id, o.version, o.completed, o.designatedTruck.truckId, " +
        "o.maxCapacity,o.path) from Order o order by o.lastEditDate desc ")
@NamedQuery(name = "Order.findAllInfoOrders",
        query = "select new com.logiweb.avaji.dtos.mq.InfoOrderDTO(" +
                "o.id, o.completed, o.designatedTruck.truckId, o.path) from Order o order by o.lastEditDate desc ")
@NamedQuery(name = "Order.findWaypointsOfThisOrder",
query = "select w from Waypoint w where w.waypointOrder.id = :orderId " )
@NamedQuery(name = "Order.findOrderById",
        query = "select o from Order o where o.id = :orderId")
@NamedQuery(name = "Order.findTruckByOrderId",
query = "select o.designatedTruck from Order o where o.id = :orderId")
@NamedQuery(name = "Order.findOrderByTruckId",
query = "select o from Order o where o.designatedTruck.truckId like :truckId")
@NamedQuery(name = "Order.findOrderIdOfDriverId",
        query = "select o.id from Order o where o.designatedTruck.truckId = " +
                "(select d.currentTruck.truckId from Driver d where d.id = :id)")
@NamedQuery(name = "Order.updateOnCompletedOrder",
        query = "update Order o set o.completed = true, " +
                "o.designatedTruck = null, o.lastEditDate = :date where o.id = :id")
@NamedQuery(name="Order.countOrders",
        query = "select count(o) from Order o")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Version
    @Column(name = "version")
    private int version;
    @Column(name = "completed")
    private boolean completed;
    @Column(name = "path")
    private String path;
    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST} ,fetch = FetchType.LAZY)
    @JoinColumn(name = "truck_id")
    private Truck designatedTruck;
    @Column(name = "max_capacity")
    private double maxCapacity;
    @Column(name = "last_edit_date")
    private LocalDateTime lastEditDate;


    @OneToMany(cascade = CascadeType.REFRESH ,fetch = FetchType.LAZY,
            mappedBy = "waypointOrder")
    private List<Waypoint> waypoints = new ArrayList<>();

    @OneToOne(mappedBy = "order")
    private OrderDetails orderDetails;


    public Order(boolean completed, String path, Truck designatedTruck,
                 double maxCapacity, LocalDateTime lastEditDate) {
        this.completed = completed;
        this.path = path;
        this.designatedTruck = designatedTruck;
        this.maxCapacity = maxCapacity;
        this.lastEditDate = lastEditDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", version=" + version +
                ", completed=" + completed +
                ", path='" + path + '\'' +
                ", designatedTruck=" + designatedTruck +
                '}';
    }
}
