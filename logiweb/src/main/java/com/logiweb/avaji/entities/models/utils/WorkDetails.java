package com.logiweb.avaji.entities.models.utils;

import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.Truck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "work_details")
@NamedQueries(value = {
        @NamedQuery(name = "WorkDetails.findWorkDetailsByDriverId",
        query = "select w from WorkDetails  w where w.driver.id = :driverId")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_id")
    private Driver driver;
    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "truck_id")
    private Truck truck;
    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;
    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "shift_id")
    private WorkShift workShift;
}
