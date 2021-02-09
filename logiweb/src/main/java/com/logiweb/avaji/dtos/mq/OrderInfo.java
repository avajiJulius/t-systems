package com.logiweb.avaji.dtos.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo implements Serializable {
    private Deque<InfoOrderDTO> lastOrders = new ArrayDeque<>();

    public void setLastOrdersByList(List<InfoOrderDTO> orders) {
        this.lastOrders.addAll(orders);
    }
}
