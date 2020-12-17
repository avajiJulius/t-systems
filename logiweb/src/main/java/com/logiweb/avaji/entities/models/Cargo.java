package com.logiweb.avaji.entities.models;

import com.logiweb.avaji.entities.enums.CargoStatus;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cargo")
@NamedQueries(value = {
//        @NamedQuery(name = "Cargo.findCargoByOrderId",
//        query = "select w.waypointCargo from Order o " +
//                "join o.waypoints w " +
//                "where o.orderId = 1")
        @NamedQuery(name = "Cargo.findCargoByOrderId",
        query = "select c from Waypoint w " +
                "join w.waypointCargo c " +
                "where w.waypointOrder.id = :orderId")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cargo_id")
    private Long cargoId;
    @Column(name = "title")
    private String cargoTitle;
    @Column(name = "weight")
    private double cargoWeight;
    @Column(name = "cargo_status")
    @Enumerated(value = EnumType.STRING)
    private CargoStatus cargoStatus;

}
