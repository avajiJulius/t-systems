package com.logiweb.avaji.dtos;

import com.logiweb.avaji.entities.enums.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftDetailsDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String cityName;
    private DriverStatus driverStatus;
    private boolean shiftActive;
    private LocalDateTime start;
    private LocalDateTime end;
    private double hoursWorked;
}
