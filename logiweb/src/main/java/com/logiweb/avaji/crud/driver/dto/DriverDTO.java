package com.logiweb.avaji.crud.driver.dto;

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
    interface Create{}
    interface Read{}
    interface Update{}

    @Null(groups = {Update.class, Create.class})
    @NotNull(groups = {Read.class})
    private long id;
    private int version;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private boolean enable;
    @Null(groups = {Create.class})
    @NotNull(groups = {Read.class, Update.class})
    private double hoursWorked;
    @Null(groups = {Create.class})
    @NotNull(groups = {Read.class, Update.class})
    private DriverStatus driverStatus;
    private String truckId;
    private long cityCode;
    private String cityName;

    public DriverDTO(@Null(groups = {Update.class, Create.class}) @NotNull(groups = {Read.class}) long id,
                     @NotNull String firstName, @NotNull String lastName,
                     @Null(groups = {Create.class}) @NotNull(groups = {Read.class, Update.class}) double hoursWorked,
                     @Null(groups = {Create.class}) @NotNull(groups = {Read.class, Update.class}) DriverStatus driverStatus,
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



    public DriverDTO(@Null(groups = {Update.class, Create.class}) @NotNull(groups = {Read.class}) long id,
                     int version, @NotNull String firstName, @NotNull String lastName,
                     @Null(groups = {Create.class}) @NotNull(groups = {Read.class, Update.class}) double hoursWorked,
                     @Null(groups = {Create.class}) @NotNull(groups = {Read.class, Update.class}) DriverStatus driverStatus,
                     long cityCode, String cityName) {
        this.id = id;
        this.version = version;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hoursWorked = hoursWorked;
        this.driverStatus = driverStatus;
        this.cityCode = cityCode;
        this.cityName = cityName;
    }
}
