package com.logiweb.avaji.entitie.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NamedQuery(name = "Order.findAllOrders",
query = "select new com.logiweb.avaji.dtos.OrderDTO(" +
        "o.id, o.version, o.completed, o.designatedTruck.truckId, " +
        "o.maxCapacity,o.path) from Order o")
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
                "o.designatedTruck = null where o.id = :id")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
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


    @OneToMany(cascade = CascadeType.REFRESH ,fetch = FetchType.LAZY,
            mappedBy = "waypointOrder")
    private List<Waypoint> waypoints = new ArrayList<>();

    @OneToOne(mappedBy = "order")
    private OrderDetails orderDetails;


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
