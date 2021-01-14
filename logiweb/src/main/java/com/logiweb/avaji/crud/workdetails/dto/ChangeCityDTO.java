package com.logiweb.avaji.crud.workdetails.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeCityDTO {
    private long driverId;
    private long cityCode;
}
