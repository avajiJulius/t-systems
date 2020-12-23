package com.logiweb.avaji.entities.dto;

import com.logiweb.avaji.entities.enums.DriverStatus;
import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.entities.models.utils.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverPublicResponseDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private Double hoursWorked;
    private String driverStatus;
    private City currentCity;
    private Truck currentTruck;
}
