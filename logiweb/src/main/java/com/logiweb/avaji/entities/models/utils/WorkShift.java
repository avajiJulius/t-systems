package com.logiweb.avaji.entities.models.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkShift {
    @Column(name = "shift_active")
    private boolean active;
    @Column(name = "shift_start")
    private LocalDateTime start;
    @Column(name = "shift_end")
    private LocalDateTime end;
}
