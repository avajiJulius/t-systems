package com.logiweb.avaji.crud.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaypointDto {
    private long cityCode;
    private String type;
    private long cargoId;
}
