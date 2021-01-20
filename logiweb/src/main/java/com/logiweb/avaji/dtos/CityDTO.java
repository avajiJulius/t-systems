package com.logiweb.avaji.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO {
    private long cityCode;
    private String cityName;
}
