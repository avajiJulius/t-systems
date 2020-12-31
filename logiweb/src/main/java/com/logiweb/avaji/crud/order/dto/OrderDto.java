package com.logiweb.avaji.crud.order.dto;

import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Integer orderId;
    private boolean completed;
    private List<Waypoint> waypoints;
    private Truck designatedTruck;
}
