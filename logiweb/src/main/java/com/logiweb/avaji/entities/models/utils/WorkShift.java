package com.logiweb.avaji.entities.models.utils;

import com.logiweb.avaji.entities.models.Driver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "work_shifts")
@NamedQueries(value = {
        @NamedQuery(name = "WorkShift.findShiftByDriverId",
        query = "select s from WorkShift s " +
                "where s.driver.driverId = :driverId")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkShift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shift_id")
    private Integer id;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER,
            mappedBy = "workShift")
    private Driver driver;
    @Column(name = "active")
    private boolean active;
    @Column(name = "shift_start")
    private LocalDateTime start;
    @Column(name = "shift_end")
    private LocalDateTime end;

}
