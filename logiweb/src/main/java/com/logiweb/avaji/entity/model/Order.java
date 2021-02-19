package com.logiweb.avaji.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.logiweb.avaji.entity.enums.OrderStatus;
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
        "o.id, o.status, o.designatedTruck.truckId, " +
        "o.maxCapacity, o.path) from Order o order by o.lastEditDate desc ")
@NamedQuery(name = "Order.findAllPastOrders",
        query = "select new com.logiweb.avaji.dtos.OrderDTO(" +
                "o.id, o.status, o.truckId, o.maxCapacity, o.path) from PastOrder o order by o.lastEditDate desc ")
@NamedQuery(name = "Order.findAllInfoOrders",
        query = "select new com.logiweb.avaji.dtos.mq.InfoOrderDTO(" +
                "o.id, o.status, o.designatedTruck.truckId, o.path) from Order o order by o.lastEditDate desc ")
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
@NamedQuery(name="Order.countOrders",
        query = "select count(o) from Order o")
@NamedQuery(name="Order.countPastOrders",
        query = "select count(o) from PastOrder o")
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

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @Column(name = "path")
    private String path;

    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "truck_id")
    private Truck designatedTruck;

    @Column(name = "max_capacity")
    private double maxCapacity;

    @Column(name = "last_edit_date")
    private LocalDateTime lastEditDate;

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.REMOVE},fetch = FetchType.LAZY,
            mappedBy = "waypointOrder")
    private List<Waypoint> waypoints = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY,
            mappedBy = "order")
    @JsonIgnore
    @Transient
    private OrderDetails orderDetails;


    public Order(OrderStatus status, String path, Truck designatedTruck,
                 double maxCapacity, LocalDateTime lastEditDate) {
        this.status = status;
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
                ", completed=" + status +
                ", path='" + path + '\'' +
                ", designatedTruck=" + designatedTruck +
                '}';
    }
}
