package com.logiweb.avaji.entity.model.mq;

import com.logiweb.avaji.dtos.OrderDTO;
import lombok.Data;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

@Data
public class OrderInfo {
    private Deque<OrderDTO> lastOrders = new ArrayDeque<>();

    public void setLastOrdersByList(List<OrderDTO> orders) {
        this.lastOrders.addAll(orders);
    }
}
