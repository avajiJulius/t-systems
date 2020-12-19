package com.logiweb.avaji.entities.models.utils;

import com.logiweb.avaji.entities.enums.WaypointType;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "waypoints")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Waypoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "waypoint_id")
    private long waypointId;
    @OneToOne
    @JoinColumn(name = "city_code")
    private City waypointCity;
    @Column(name = "type")
    private WaypointType waypointType;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order waypointOrder;
    @OneToOne
    @JoinColumn(name = "cargo_id")
    private Cargo waypointCargo;
}
