package com.logiweb.avaji.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "work_shifts")
@NamedQuery(name = "WorkShift.findShiftDetails",
query = "select new com.logiweb.avaji.dtos.ShiftDetailsDTO(" +
        "d.id, d.firstName, d.lastName, d.currentCity.cityName, " +
        "d.driverStatus , ws.active, " +
        "ws.start, ws.end, d.hoursWorked) from Driver d " +
        "join d.workShift ws " +
        "where d.id = :id")
@NamedQuery(name = "WorkShift.updateOnCompletedOrder",
query = "update WorkShift ws " +
        "set ws.active = false, ws.end = :end " +
        "where ws.id = (select d.id from Driver d where d.orderDetails.id = :id)")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkShift {
    @Id
    @Column(name = "id",updatable = false, nullable = false)
    private long id;
    @MapsId
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Driver driver;
    @Column(name = "active")
    private boolean active;
    @Column(name = "shift_start")
    private LocalDateTime start;
    @Column(name = "shift_end")
    private LocalDateTime end;

}
