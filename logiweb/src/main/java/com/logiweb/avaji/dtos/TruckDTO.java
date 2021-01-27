package com.logiweb.avaji.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TruckDTO {
    public interface Create{}
    public interface Update{}


    @Null(groups = {Update.class})
    @NotBlank(groups = {Create.class}, message = "Must be filed")
    @Pattern(groups = {Create.class}, regexp = "^[A-Z]{2}[A-Z0-9]{0,7}[0-9]{5}$",
            message = "Truck ID must consist of two alphabet characters at first and five numbers")
    @Length(groups = {Create.class}, min = 7, max = 7, message = "Length of truck ID must be 7 characters")
    private String truckId;
    private int version;
    @NotNull(message = "Must be filed")
    @Min(groups = {Update.class, Create.class}, value = 10, message = "Capacity should not be less than 10")
    @Max(groups = {Update.class, Create.class}, value = 50, message = "Capacity should not be greater than 50")
    private double capacity;
    @NotNull(message = "Must be filed")
    @Min(groups = {Update.class, Create.class}, value = 1, message = "Shift size should not be less than 1")
    @Max(groups = {Update.class, Create.class}, value = 4, message = "Shift size should not be greater than 4")
    private int shiftSize;
    private boolean serviceable;
    @NotNull(groups = {Update.class, Create.class}, message = "Must be filed")
    private long currentCityCode;
    private String currentCityName;
    private boolean inUse;

    public TruckDTO(String truckId, double capacity, int shiftSize, boolean serviceable,
                    long currentCityCode, String currentCityName, boolean inUse) {
        this.truckId = truckId;
        this.capacity = capacity;
        this.shiftSize = shiftSize;
        this.serviceable = serviceable;
        this.currentCityCode = currentCityCode;
        this.currentCityName = currentCityName;
        this.inUse = inUse;
    }
}
