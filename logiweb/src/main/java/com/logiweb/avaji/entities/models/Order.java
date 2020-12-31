package com.logiweb.avaji.entities.models;

import com.logiweb.avaji.entities.models.utils.Waypoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(name = "Order.findAllOrders",
        query = "select o from Order o"),
        @NamedQuery(name = "Order.findWaypointsOfThisOrder",
        query = "select w from Waypoint w where w.waypointOrder.orderId = :orderId " ),
        @NamedQuery(name = "Order.findOrderById",
                query = "select o from Order o " +
                        "join fetch o.waypoints w " +
                        "where w.waypointOrder.orderId = :orderId"),
        @NamedQuery(name = "Order.findTruckByOrderId",
        query = "select o.designatedTruck from Order o where o.orderId = :orderId"),
        @NamedQuery(name = "Order.findOrderByTruckId",
        query = "select o from Order o where o.designatedTruck.truckId like :truckId")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;
    @Column(name = "completed")
    private boolean completed;
    @OneToMany(cascade = CascadeType.ALL ,fetch = FetchType.LAZY,
            mappedBy = "waypointOrder")
    private List<Waypoint> waypoints = new ArrayList<>();
    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST} ,fetch = FetchType.EAGER)
    @JoinColumn(name = "truck_id")
    private Truck designatedTruck;

}
