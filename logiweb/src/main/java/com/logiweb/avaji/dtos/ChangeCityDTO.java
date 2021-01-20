package com.logiweb.avaji.dtos;

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
