package com.logiweb.avaji.entities.models.utils;

import com.logiweb.avaji.entities.models.Driver;
import com.logiweb.avaji.entities.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "work_shifts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkShift {
    @Id
    @Column(name = "id",updatable = false, nullable = false)
    private long id;
    @MapsId
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private User user;
    @Column(name = "active")
    private boolean active;
    @Column(name = "shift_start")
    private LocalDateTime start;
    @Column(name = "shift_end")
    private LocalDateTime end;

}
