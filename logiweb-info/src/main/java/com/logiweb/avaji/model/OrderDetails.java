package com.logiweb.avaji.model;

import java.util.Deque;

public class OrderDetails {

    private Deque<Order> lastOrders;

    public Deque<Order> getLastOrders() {
        return lastOrders;
    }

    public void setLastOrders(Deque<Order> lastOrders) {
        this.lastOrders = lastOrders;
    }
}
