package com.logiweb.avaji.crud.truck.dto;

import com.logiweb.avaji.entities.models.utils.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TruckDto {
    private String truckId;
    private double capacity;
    private int shiftSize;
    private boolean serviceable;
    private Integer currentCityCode;
}
