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

    private long cityCode;
    private WaypointType type;
    private long cargoId;
}
