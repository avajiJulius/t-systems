package com.logiweb.avaji.entity.model;

import com.logiweb.avaji.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PastOrder {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "path")
    private String path;

    @Column(name = "max_capacity")
    private double maxCapacity;

    @Column(name = "truck_id")
    private String truckId;

    @Column(name = "last_edit_date")
    private LocalDateTime lastEditDate;


    @ManyToMany(mappedBy = "pastOrders")
    private List<Driver> drivers = new ArrayList<>();
}
