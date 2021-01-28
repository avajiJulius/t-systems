package com.logiweb.avaji.entity.model;

import com.logiweb.avaji.entity.enums.DriverStatus;
import com.logiweb.avaji.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "drivers")
@NamedQuery(name = "Driver.findAllDrivers",
    query = "select new com.logiweb.avaji.dtos.DriverDTO(d.id, d.firstName, d.lastName, " +
            "d.hoursWorked, d.driverStatus , d.currentTruck.truckId, d.currentCity.cityCode, d.currentCity.cityName) " +
            "from Driver d")
@NamedQuery(name = "Driver.findDriversForOrder",
        query = "select new com.logiweb.avaji.dtos.DriverDTO(d.id, d.version,d.firstName, d.lastName, " +
                "d.hoursWorked, d.driverStatus, d.currentCity.cityCode, d.currentCity.cityName) " +
                "from Driver d " +
                "where (176 - d.hoursWorked) > :shiftHours " +
                "and d.currentCity.cityCode = :cityCode " +
                "and d.currentTruck is null")
@NamedQuery(name = "Driver.refreshWorkedHours",
        query = "update Driver d set d.hoursWorked = 0")
@NamedQuery(name = "Driver.findDriversByOrderId",
        query = "select new com.logiweb.avaji.dtos.DriverDTO(d.id, d.firstName, d.lastName) from Driver d " +
                "where d.currentTruck.truckId = " +
                "(select o.designatedTruck.truckId from Order o where o.id = :id)")
@NamedQuery(name = "Driver.countDriversOfTruck",
                query = "select count(d) from Driver d " +
                        "where d.currentTruck in (select t from Truck t where t.truckId = :id)")
@NamedQuery(name = "Driver.countDrivers",
        query = "select count(d) from Driver d")
@NamedQuery(name = "Driver.findDriverById",
        query = "select new com.logiweb.avaji.dtos.DriverDTO(d.id, d.version,d.firstName, d.lastName, " +
                "d.hoursWorked, d.driverStatus, d.currentCity.cityCode, d.currentCity.cityName) " +
                "from Driver d where d.id in :id")
@NamedQuery(name = "Driver.findDriversByIds",
        query = "select d from Driver d where d.id in :driversIds")
@NamedQuery(name = "Driver.updateOnCompletedOrder",
        query = "update Driver d set d.currentTruck = null, d.orderDetails = null " +
                "where d.id = :id")
@NamedQuery(name = "Driver.updateDriverOnCityChange",
        query = "update Driver d set d.currentCity = " +
                "(select c from City c where c.cityCode = :cityCode) " +
                "where d.id in (select d.id from Driver d where d.orderDetails.id = :id)")
@NamedQuery(name = "Driver.findTruckIdByDriver",
        query = "select d.currentTruck.truckId from Driver d where d.id = :id")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver extends User{

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "hours_worked")
    private double hoursWorked;
    @Column(name = "driver_status")
    @Enumerated(value = EnumType.STRING)
    private DriverStatus driverStatus;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "city_code")
    private City currentCity;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "truck_id")
    private Truck currentTruck;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_details")
    private OrderDetails orderDetails;

    @OneToOne(mappedBy = "driver")
    private WorkShift workShift;

    public static class Builder {
        private Driver newDriver;

        public Builder() {
            newDriver = new Driver();
        }

        public Builder(Driver driver) {
            newDriver = driver;
        }

        public Builder withVersion(int version) {
            newDriver.setVersion(version);
            return this;
        }

        public Builder withId(long id) {
            newDriver.setId(id);
            return this;
        }

        public Builder withEmail(String email) {
            newDriver.setEmail(email);
            return this;
        }


        public Builder withPassword(String password) {
            newDriver.setPassword(password);
            return this;
        }


        public Builder withFirstName(String firstName) {
            newDriver.firstName = firstName;
            return this;
        }


        public Builder withLastName(String lastName) {
            newDriver.lastName = lastName;
            return this;
        }


        public Builder withEnable(boolean enable) {
            newDriver.setEnable(enable);
            return this;
        }

        public Builder withRole(Role role) {
            newDriver.setRole(role);
            return this;
        }


        public Builder withHoursWorked(double hoursWorked) {
            newDriver.hoursWorked = hoursWorked;
            return this;
        }


        public Builder withDriverStatus(DriverStatus status) {
            newDriver.driverStatus = status;
            return this;
        }


        public Builder withCurrentCity(City city) {
            newDriver.currentCity = city;
            return this;
        }

        public Driver build() {
            return newDriver;
        }
    }

    @Override
    public String toString() {
        return "Driver{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", hoursWorked=" + hoursWorked +
                ", driverStatus=" + driverStatus +
                ", currentCity=" + currentCity +
                ", currentTruck=" + currentTruck +
                '}';
    }
}
