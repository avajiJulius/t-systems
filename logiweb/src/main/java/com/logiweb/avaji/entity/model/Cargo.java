package com.logiweb.avaji.entity.model;

import com.logiweb.avaji.entity.enums.CargoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cargo")
@NamedQuery(name = "Cargo.findAllFreeCargo",
query = "select c from Cargo c " +
        "where c.cargoId not in " +
        "(select w.waypointCargo.cargoId from Waypoint w where w.waypointOrder is not null)")
@NamedQuery(name = "Cargo.findCargoByOrderId",
query = "select distinct(c) from Waypoint w " +
        "join w.waypointCargo c " +
        "where w.waypointOrder.id = :orderId")
@NamedQuery(name = "Cargo.findCargoByWaypoints",
query = "select w.waypointCargo from Waypoint w " +
        "where w in :waypoints")
@NamedQuery(name = "Cargo.findCargoWeightById",
query = "select c.cargoWeight from Cargo c where c.cargoId = :id")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long cargoId;

    @Version
    @Column(name = "version")
    private int version;

    @Column(name = "title")
    private String cargoTitle;

    @Column(name = "weight")
    private double cargoWeight;

    @Column(name = "cargo_status")
    @Enumerated(value = EnumType.STRING)
    private CargoStatus cargoStatus;

    public Cargo(long cargoId, String title) {
        this.cargoId = cargoId;
        this.cargoTitle = title;
    }
}
