package com.logiweb.avaji.dtos.mq;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoOrderDTO {
    private long orderId;
    private boolean completed;
}
