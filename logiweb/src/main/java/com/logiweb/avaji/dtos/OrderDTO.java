package com.logiweb.avaji.dtos;

import com.logiweb.avaji.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private long orderId;
    private int version;
    private OrderStatus status;
    private String truckId;
    private int shiftSize;
    private double maxCapacity;
    private List<DriverDTO> drivers = new ArrayList<>();
    private String prettyPath;

    public OrderDTO(long orderId, OrderStatus status, String truckId,
                    double maxCapacity, String prettyPath) {
        this.orderId = orderId;
        this.status = status;
        this.truckId = truckId;
        this.maxCapacity = maxCapacity;
        this.prettyPath = prettyPath;
    }
}
