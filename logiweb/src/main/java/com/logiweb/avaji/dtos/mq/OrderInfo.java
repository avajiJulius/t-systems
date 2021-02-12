package com.logiweb.avaji.dtos.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo implements Serializable {
    private List<InfoOrderDTO> lastOrders = new ArrayList<>();

    public void setLastOrdersByList(List<InfoOrderDTO> orders) {
        this.lastOrders.addAll(orders);
    }

    public List<InfoOrderDTO> getLastOrders() {
        return lastOrders;
    }
}
