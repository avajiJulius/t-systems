package com.logiweb.avaji.controllers;

import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.services.CargoService;
import com.logiweb.avaji.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CargoService cargoService;

    @Autowired
    public OrderController(OrderService orderService, CargoService cargoService) {
        this.orderService = orderService;
        this.cargoService = cargoService;
    }

    @GetMapping("")
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.readAllOrders();
        model.addAttribute("orders", orders);
        return "orders/home";
    }

    @GetMapping("/{id}/cargo")
    public String getCargoByOrder(Model model,
                                  @PathVariable(name = "id") Long orderId) {
        List<Cargo> cargoList = cargoService.readCargoByOrderId(orderId);
//        List<Cargo> cargoList = orderService.readCargoByOrderId(orderId);
        model.addAttribute("cargoList", cargoList);
        return "orders/cargo";
    }

}
