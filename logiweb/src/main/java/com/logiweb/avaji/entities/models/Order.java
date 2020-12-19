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
        @NamedQuery(name = "Order.deleteOrder",
        query = "delete from Order o where o.orderId = :orderId" )
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "completed")
    private boolean completed;
    @OneToMany(mappedBy = "waypointOrder")
    private List<Waypoint> waypoints;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "truck_id")
    private Truck designatedTruck;

}
