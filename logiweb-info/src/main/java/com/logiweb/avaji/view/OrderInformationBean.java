package com.logiweb.avaji.view;

import com.logiweb.avaji.ejb.InformationManager;
import com.logiweb.avaji.model.Order;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Deque;

@Named(value = "orderInfo")
@ViewScoped
public class OrderInformationBean implements Serializable {
    private Deque<Order> lastOrders;

    @EJB
    private InformationManager informationManager;

    public Deque<Order> getLastOrders() {
        return lastOrders;
    }

    public void setLastOrders(Deque<Order> lastOrders) {
        this.lastOrders = lastOrders;
    }
}
