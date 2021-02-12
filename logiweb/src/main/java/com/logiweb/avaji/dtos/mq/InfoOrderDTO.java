package com.logiweb.avaji.dtos.mq;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoOrderDTO {
    private long orderId;
    private boolean completed;
    private String truckId;
    private String path;
    private List<InfoDriverDTO> drivers;

    public InfoOrderDTO(long orderId, boolean completed,
                        String truckId, String path) {
        this.orderId = orderId;
        this.completed = completed;
        this.truckId = truckId;
        this.path = path;
    }
}
