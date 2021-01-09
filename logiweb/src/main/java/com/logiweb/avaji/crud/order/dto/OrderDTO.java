package com.logiweb.avaji.crud.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private long orderId;
    private boolean completed;
    private String truckId;
    private long currentShiftSize;
}
