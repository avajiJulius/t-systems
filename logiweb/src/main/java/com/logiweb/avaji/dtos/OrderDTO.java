package com.logiweb.avaji.dtos;

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
    private boolean completed;
    private String truckId;
    private int shiftSize;
    private List<DriverDTO> drivers = new ArrayList<>();
    private String prettyPath;

    public OrderDTO(long orderId, int version, boolean completed,
                    String truckId, String prettyPath) {
        this.orderId = orderId;
        this.version = version;
        this.completed = completed;
        this.truckId = truckId;
        this.prettyPath = prettyPath;
    }
}
