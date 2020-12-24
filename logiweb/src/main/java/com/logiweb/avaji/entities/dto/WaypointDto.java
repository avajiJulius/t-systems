package com.logiweb.avaji.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaypointDto {
    private Integer cityCode;
    private String type;
    private Integer cargoId;
}
