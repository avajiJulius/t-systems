package com.logiweb.avaji.entities.models;


import com.logiweb.avaji.entities.models.utils.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "trucks")
@NamedQueries({
        @NamedQuery(name = "Truck.findTrucks",
                query = "select t from Truck t"),
        @NamedQuery(name = "Truck.findTrucksForOrder",
                query = "select t from Truck t " +
                        "where t.serviceable = true " +
                        "and t.capacity > :maxCapacity " +
                        "and t not in (select o.designatedTruck from Order o)")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Truck {
    @Id
    @Column(name = "truck_id")
    private String truckId;
    @Column(name= "work_shift_size")
    private double workShiftSize;
    @Column(name = "capacity")
    private double capacity;
    @Column(name = "serviceable")
    private boolean serviceable;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "city_code")
    private City currentCity;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY,
            mappedBy = "designatedTruck")
    private Order currentOrder;

}
