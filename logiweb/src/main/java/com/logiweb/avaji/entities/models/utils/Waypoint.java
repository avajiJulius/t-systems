package com.logiweb.avaji.entities.models.utils;

import com.logiweb.avaji.entities.enums.WaypointType;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "waypoints")
@NamedQueries(value = {
        @NamedQuery(name = "Waypoint.findWaypointsOfThisOrder",
        query = "select w from Waypoint w join fetch w.waypointOrder " +
                "where w.waypointOrder.orderId = :orderId"),
        @NamedQuery(name = "Waypoint.findWaypointsOfThisCargo",
                query = "select w from Waypoint w where w.waypointCargo.cargoId = :cargoId")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Waypoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "waypoint_id")
    private Integer waypointId;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "city_code")
    private City waypointCity;
    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private WaypointType waypointType;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order waypointOrder;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "cargo_id")
    private Cargo waypointCargo;

    public Waypoint(City waypointCity, WaypointType waypointType,
                    Order waypointOrder, Cargo waypointCargo) {
        this.waypointCity = waypointCity;
        this.waypointType = waypointType;
        this.waypointOrder = waypointOrder;
        this.waypointCargo = waypointCargo;
    }
}
