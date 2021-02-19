package com.logiweb.avaji.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "work_shifts")
@NamedQuery(name = "WorkShift.findShiftDetails",
query = "select new com.logiweb.avaji.dtos.ShiftDetailsDTO(" +
        "ws.driver.id, ws.driver.firstName, ws.driver.lastName, ws.driver.currentCity.cityName, " +
        "ws.driver.driverStatus , ws.active, " +
        "ws.start, ws.end, ws.driver.hoursWorked) from WorkShift ws " +
        "where ws.id = :id")
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
    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Driver driver;

    @Column(name = "active")
    private boolean active;

    @Column(name = "shift_start")
    private LocalDateTime start;

    @Column(name = "shift_end")
    private LocalDateTime end;


    @Override
    public String toString() {
        return "WorkShift{" +
                "id=" + id +
                ", active=" + active +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
