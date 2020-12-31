package com.logiweb.avaji.crud.driver.dto;

import com.logiweb.avaji.crud.order.dto.WaypointDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverPrivateResponseDto {
    private Integer driverId;
    private List<Optional<Integer>> soDriversIds;
    private String truckId;
    private Integer orderId;
    private List<WaypointDto> waypoints;

}
