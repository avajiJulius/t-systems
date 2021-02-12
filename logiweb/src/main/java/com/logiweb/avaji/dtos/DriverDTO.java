package com.logiweb.avaji.dtos;

import com.logiweb.avaji.entity.enums.DriverStatus;
import com.logiweb.avaji.validation.annotation.EmailUnique;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {
    public interface Create{}
    public interface Read{}
    public interface Update{}

    private long id;
    private int version;
    @NotBlank(groups = {Create.class, Update.class}, message = "First name must be filled")
    @Pattern(groups = {Create.class, Update.class}, regexp = "^[\\p{L} \\.'\\-]+$",
            message = "Not a valid first name")
    private String firstName;
    @NotBlank(groups = {Create.class, Update.class}, message = "Last name must be filled")
    @Pattern(groups = {Create.class, Update.class}, regexp = "^[\\p{L} \\.'\\-]+$",
            message = "Not a valid last name")
    private String lastName;
    @Null(groups = {Update.class}, message = "Cannot update email")
    @NotBlank(groups = {Create.class}, message = "Email must be not null")
    @EmailUnique(groups = {Create.class})
    private String email;
    @Null(groups = {Update.class}, message = "Cannot update password")
    @NotBlank(groups = {Create.class}, message = "Password must be not null")
    private String password;
    private boolean enable;
    @NotNull(groups = {Read.class, Update.class}, message = "Hours worked must be")
    private double hoursWorked;
    private DriverStatus driverStatus;
    private String truckId;
    @NotNull(groups = {Create.class},message = "cityCode")
    private long cityCode;
    private String cityName;

    public DriverDTO(long id, String firstName, String lastName,
                     double hoursWorked, DriverStatus driverStatus,
                     String truckId, long cityCode, String cityName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hoursWorked = hoursWorked;
        this.driverStatus = driverStatus;
        this.truckId = truckId;
        this.cityCode = cityCode;
        this.cityName = cityName;
    }

    public DriverDTO(long id, int version, String firstName,
                     String lastName, double hoursWorked,
                     DriverStatus driverStatus, long cityCode, String cityName) {
        this.id = id;
        this.version = version;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hoursWorked = hoursWorked;
        this.driverStatus = driverStatus;
        this.cityCode = cityCode;
        this.cityName = cityName;
    }

    public DriverDTO(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
