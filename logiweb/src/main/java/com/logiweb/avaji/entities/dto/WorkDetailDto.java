package com.logiweb.avaji.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkDetailDto {
    private boolean shiftActive;
    private String driverStatus;
    private String statusAboutCargo;
}
