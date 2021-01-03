package com.logiweb.avaji.crud.workdetails.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftDetailsDto {
    private boolean active;
    private String driverStatus;
}
