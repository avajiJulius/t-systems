package com.logiweb.avaji.crud.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private long orderId;
    private boolean completed;
    private String designatedTruckId;
    private int totalShiftSize;
    private int currentShiftSize;
}
