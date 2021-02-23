package com.logiweb.avaji.dtos.mq;


import com.logiweb.avaji.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoOrderDTO implements Serializable {
    private long orderId;
    private OrderStatus status;
    private String truckId;
    private String path;
    private List<InfoDriverDTO> drivers;

    public InfoOrderDTO(long orderId, OrderStatus status,
                        String truckId, String path) {
        this.orderId = orderId;
        this.status = status;
        this.truckId = truckId;
        this.path = path;
    }
}
