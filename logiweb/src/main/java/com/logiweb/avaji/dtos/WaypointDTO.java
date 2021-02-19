package com.logiweb.avaji.dtos;

import com.logiweb.avaji.entity.enums.CargoStatus;
import com.logiweb.avaji.entity.enums.WaypointType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaypointDTO {
    private interface Create{}


    private static final WaypointType load = WaypointType.LOADING;
    private static final WaypointType unload = WaypointType.UNLOADING;

    private long loadCityCode;
    private long unloadCityCode;

    private long cityCode;
    private String cityName;

    private WaypointType type;

    private String cargoTitle;
    private long cargoId;
    private CargoStatus cargoStatus;

    private double cargoWeight;

    public WaypointDTO(long cityCode, String cityName, WaypointType type,
                       String cargoTitle, long cargoId, CargoStatus cargoStatus, double cargoWeight) {
        this.cityCode = cityCode;
        this.cityName = cityName;
        this.type = type;
        this.cargoTitle = cargoTitle;
        this.cargoId = cargoId;
        this.cargoStatus = cargoStatus;
        this.cargoWeight = cargoWeight;
    }

    public WaypointDTO(long loadCityCode, long unloadCityCode,
                       long cargoId, double cargoWeight) {
        this.loadCityCode = loadCityCode;
        this.unloadCityCode = unloadCityCode;
        this.cargoId = cargoId;
        this.cargoWeight = cargoWeight;
    }

}
