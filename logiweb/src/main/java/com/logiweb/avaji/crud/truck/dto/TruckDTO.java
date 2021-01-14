package com.logiweb.avaji.crud.truck.dto;

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
    @Min(groups = {Update.class, Create.class}, value = 1000, message = "Capacity should not be less than 1000")
    @Max(groups = {Update.class, Create.class}, value = 10000, message = "Capacity should not be greater than 10000")
    private double capacity;
    @NotNull(message = "Must be filed")
    @Min(groups = {Update.class, Create.class}, value = 1, message = "Shift size should not be less than 1")
    @Max(groups = {Update.class, Create.class}, value = 4, message = "Shift size should not be greater than 4")
    private int shiftSize;
    private boolean serviceable;
    @NotNull(groups = {Update.class, Create.class}, message = "Must be filed")
    private long currentCityCode;
    private String currentCityName;

    public TruckDTO(@Null(groups = {Update.class}) @NotBlank(groups = {Create.class}, message = "Must be filed") @Pattern(groups = {Create.class}, regexp = "^[A-Z]{2}[A-Z0-9]{0,7}[0-9]{5}$",
            message = "Truck ID must consist of two alphabet characters at first and five numbers") @Length(groups = {Create.class}, min = 7, max = 7, message = "Length of truck ID must be 7 characters") String truckId,
                    @NotNull(message = "Must be filed") @Min(groups = {Update.class, Create.class}, value = 1000, message = "Capacity should not be less than 1000") @Max(groups = {Update.class, Create.class}, value = 10000, message = "Capacity should not be greater than 10000") double capacity,
                    @NotNull(message = "Must be filed") @Min(groups = {Update.class, Create.class}, value = 1, message = "Shift size should not be less than 1") @Max(groups = {Update.class, Create.class}, value = 4, message = "Shift size should not be greater than 4") int shiftSize, boolean serviceable,
                    @NotNull(groups = {Update.class, Create.class}, message = "Must be filed") long currentCityCode, String currentCityName) {
        this.truckId = truckId;
        this.capacity = capacity;
        this.shiftSize = shiftSize;
        this.serviceable = serviceable;
        this.currentCityCode = currentCityCode;
        this.currentCityName = currentCityName;
    }
}
