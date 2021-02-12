package com.logiweb.avaji.model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class OrderInfo {

    private Deque<Order> lastOrders = new ArrayDeque<>();

    public Deque<Order> getLastOrders() {
        return lastOrders;
    }

    public void setLastOrders(List<Order> lastOrders) {
        this.lastOrders.addAll(lastOrders);
    }
}
