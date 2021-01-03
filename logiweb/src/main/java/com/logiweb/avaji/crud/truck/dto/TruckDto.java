package com.logiweb.avaji.crud.truck.dto;

import com.logiweb.avaji.annotation.ValueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TruckDto {
    public interface Create{}
    public interface Update{}


    @Null(groups = {Update.class})
    @NotBlank(groups = {Create.class}, message = "Must be filed")
    private String truckId;
    @NotBlank(message = "Must be filed")
    @Min(value = 1000, message = "Capacity should not be less than 1000")
    @Max(value = 10000, message = "Capacity should not be greater than 10000")
    private double capacity;
    @NotBlank(message = "Must be filed")
    @Min(value = 1, message = "Shift size should not be less than 1")
    @Max(value = 4, message = "Shift size should not be greater than 4")
    private int shiftSize;
    private boolean serviceable;
    @NotBlank(message = "Must be filed")
    private long currentCityCode;
}
