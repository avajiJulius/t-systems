package com.logiweb.avaji.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private long orderId;
    private int version;
    private boolean completed;
    private String truckId;
    private long currentShiftSize;
    private String prettyPath;
}
