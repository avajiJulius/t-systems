package com.logiweb.avaji.entities.models;


import javax.persistence.*;

@Entity
@Table(name = "trucks")
@NamedQueries({
        @NamedQuery(name = "Truck.findTrucks",
                query = "select t from Truck t"),
        @NamedQuery(name = "Truck.findTruckById",
                query = "select t from Truck t where t.truckId = :truckId"),
        @NamedQuery(name = "Truck.deleteTruck",
                query = "delete from Truck t where t.truckId = :truckId"),
})
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "truck_id")
    private String truckId;
    //уточнить у куратора про размер смены водителей
    @Column(name= "work_shift_size")
    private double workShiftSize;
    @Column(name = "capacity")
    private double capacity;
    @Column(name = "serviceable")
    private boolean serviceable;
    @Column(name = "current_city")
    private String currentCity;

    public Truck() {
    }

    public Truck(String truckId, double workShiftSize, double capacity,
                 boolean serviceable, String currentCity) {
        this.truckId = truckId;
        this.workShiftSize = workShiftSize;
        this.capacity = capacity;
        this.serviceable = serviceable;
        this.currentCity = currentCity;
    }

    public String getTruckId() {
        return truckId;
    }

    public void setTruckId(String truckId) {
        this.truckId = truckId;
    }

    public double getWorkShiftSize() {
        return workShiftSize;
    }

    public void setWorkShiftSize(double workShiftSize) {
        this.workShiftSize = workShiftSize;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public boolean isServiceable() {
        return serviceable;
    }

    public void setServiceable(boolean serviceable) {
        this.serviceable = serviceable;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }
}
