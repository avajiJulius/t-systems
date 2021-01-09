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
@NamedQuery(name = "WorkDetails.findWorkDetailsById",
query = "select new com.logiweb.avaji.crud.workdetails.dto.WorkDetailsDTO(" +
        "wd.id, t.drivers, wd.truck.truckId, wd.order.orderId, wd.order.waypoints," +
        "wd.workShift.active, wd.driver.driverStatus) " +
        "from WorkDetails wd " +
        "left join fetch Truck t where t = wd.truck " +
        "and wd.id = :id")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkDetails {
    @Id
    @Column(name = "id",updatable = false, nullable = false)
    private long id;
    @MapsId
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Driver driver;
    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "truck_id")
    private Truck truck;
    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_id")
    private WorkShift workShift;
}
