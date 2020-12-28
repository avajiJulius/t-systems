package com.logiweb.avaji.entities.dto;

import com.logiweb.avaji.entities.dto.WaypointDto;
import com.logiweb.avaji.entities.dto.WaypointsCreationDto;
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
