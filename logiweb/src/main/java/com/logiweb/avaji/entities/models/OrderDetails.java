package com.logiweb.avaji.entities.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_details")
@NamedQuery(name = "OrderDetails.findOrderDetails",
query = "select new com.logiweb.avaji.dtos.OrderDetailsDTO(" +
        "od.id, od.order.designatedTruck.truckId, od.order.path, od.remainingPath) from Driver d " +
        "join d.orderDetails od where d.id = :id")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {
    @Id
    @Column(name = "id")
    private long id;
    @MapsId
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Order order;
    @Version
    @Column(name = "version")
    private int version;
    @Column(name = "remaining_path")
    private String remainingPath;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "orderDetails")
    private List<Driver> drivers = new ArrayList<>();

    @Override
    public String toString() {
        return "OrderDetails{" +
                "id=" + id +
                ", version=" + version +
                ", remainingPath='" + remainingPath + '\'' +
                ", drivers=" + drivers +
                '}';
    }
}
