package com.logiweb.avaji.entities.models;

import com.logiweb.avaji.entities.models.utils.Waypoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(name = "Order.findAllOrders",
        query = "select o from Order o"),
        @NamedQuery(name = "Order.findWaypointsOfThisOrder",
        query = "select w from Waypoint w where w.waypointOrder.orderId = :orderId " ),
        @NamedQuery(name = "Order.findOrderById",
                query = "select o from Order o where o.orderId = :orderId " )
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
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,
            mappedBy = "waypointOrder")
    private List<Waypoint> waypoints;
    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST} ,fetch = FetchType.LAZY)
    @JoinColumn(name = "truck_id")
    private Truck designatedTruck;

}
