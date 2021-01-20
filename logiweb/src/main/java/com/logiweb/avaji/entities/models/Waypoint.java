package com.logiweb.avaji.entities.models;

import com.logiweb.avaji.entities.enums.WaypointType;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.City;
import com.logiweb.avaji.entities.models.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "waypoints")
@NamedQueries(value = {
        @NamedQuery(name = "Waypoint.findWaypointsOfThisOrder",
        query = "select new com.logiweb.avaji.dtos.WaypointDTO(" +
                "w.waypointCity.cityCode, w.waypointCity.cityName, " +
                "w.waypointType, w.waypointCargo.cargoTitle, w.waypointCargo.cargoId, " +
                "w.waypointCargo.cargoStatus, w.waypointCargo.cargoWeight) " +
                "from Waypoint w " +
                "where w.waypointOrder.id = :orderId"),
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
    private long waypointId;
    @Version
    @Column(name = "version")
    private int version;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "city_code")
    private City waypointCity;
    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private WaypointType waypointType;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order waypointOrder;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "cargo_id")
    private Cargo waypointCargo;

    public Waypoint(City waypointCity, WaypointType waypointType,
                    Order waypointOrder, Cargo waypointCargo) {
        this.waypointCity = waypointCity;
        this.waypointType = waypointType;
        this.waypointOrder = waypointOrder;
        this.waypointCargo = waypointCargo;
    }

    @Override
    public String toString() {
        return "Waypoint{" +
                "waypointId=" + waypointId +
                ", version=" + version +
                ", waypointCity=" + waypointCity +
                ", waypointType=" + waypointType +
                ", waypointOrder=" + waypointOrder +
                ", waypointCargo=" + waypointCargo +
                '}';
    }
}
