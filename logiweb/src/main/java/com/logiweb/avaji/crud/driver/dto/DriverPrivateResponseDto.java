package com.logiweb.avaji.crud.driver.dto;

import com.logiweb.avaji.crud.order.dto.WaypointDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverPrivateResponseDto {
    private long driverId;
    private List<Optional<Long>> soDriversIds;
    private String truckId;
    private long orderId;
    private List<WaypointDTO> waypoints;

}
