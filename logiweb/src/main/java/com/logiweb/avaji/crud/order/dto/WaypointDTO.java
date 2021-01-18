package com.logiweb.avaji.crud.order.dto;

import com.logiweb.avaji.entities.enums.WaypointType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaypointDTO {
    private interface Create{}


    private final WaypointType load = WaypointType.LOADING;
    private final WaypointType unload = WaypointType.UNLOADING;

    private long loadCityCode;
    private long unloadCityCode;

    private long cargoId;
}
