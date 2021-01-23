package com.logiweb.avaji.dtos;

import com.logiweb.avaji.entities.enums.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {
    public interface Create{}
    public interface Read{}
    public interface Update{}

    @Null(groups = {Create.class})
    @NotNull(groups = {Read.class})
    private long id;
    private int version;
    @NotNull(groups = {Create.class, Update.class})
    private String firstName;
    @NotNull(groups = {Create.class, Update.class})
    private String lastName;
    @Null(groups = {Update.class})
    @NotNull(groups = {Create.class})
    private String email;
    @Null(groups = {Update.class})
    @NotNull(groups = {Create.class})
    private String password;
    @Null(groups = {Create.class})
    private boolean enable;
    @Null(groups = {Create.class})
    @NotNull(groups = {Read.class, Update.class})
    private double hoursWorked;
    @Null(groups = {Create.class})
    @NotNull(groups = {Read.class})
    private DriverStatus driverStatus;
    private String truckId;
    private long cityCode;
    private String cityName;

    public DriverDTO(@Null(groups = {Update.class, Create.class}) @NotNull(groups = {Read.class}) long id,
                     @NotNull String firstName, @NotNull String lastName,
                     @Null(groups = {Create.class}) @NotNull(groups = {Read.class, Update.class}) double hoursWorked,
                     @Null(groups = {Create.class}) @NotNull(groups = {Read.class}) DriverStatus driverStatus,
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


    public DriverDTO(@Null(groups = {Update.class, Create.class}) @NotNull(groups = {Read.class}) long id, int version,
                     @NotNull(groups = {Create.class, Update.class}) String firstName, @NotNull(groups = {Create.class, Update.class}) String lastName,
                     @Null(groups = {Create.class}) @NotNull(groups = {Read.class, Update.class}) double hoursWorked,
                     @Null(groups = {Create.class}) @NotNull(groups = {Read.class}) DriverStatus driverStatus, long cityCode, String cityName) {
        this.id = id;
        this.version = version;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hoursWorked = hoursWorked;
        this.driverStatus = driverStatus;
        this.cityCode = cityCode;
        this.cityName = cityName;
    }

    public DriverDTO(@Null(groups = {Update.class, Create.class}) @NotNull(groups = {Read.class}) long id,
                     @NotNull String firstName, @NotNull String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
