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
        @NamedQuery(name = "Cargo.findCargoByOrderId",
        query = "select c from Cargo c where waypoints in " +
                "(select w from Waypoint w where w.waypointOrder = :orderId)")
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
    @Enumerated(value = EnumType.STRING)
    private CargoStatus cargoStatus;
    @ManyToMany(mappedBy = "waypointCargo")
    private List<Waypoint> waypoints;


}
