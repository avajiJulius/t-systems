package com.logiweb.avaji.controllers;

import com.logiweb.avaji.entities.dto.WaypointsCreationDto;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import com.logiweb.avaji.services.CargoService;
import com.logiweb.avaji.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        model.addAttribute("cargoList", cargoList);
        return "orders/cargo";
    }

    @GetMapping("/new")
    public String getOrderForm(Model model) {
        WaypointsCreationDto waypointForm = new WaypointsCreationDto();

        for (int i = 1; i <= 3; i++) {
            waypointForm.addWaypoint(new Waypoint());
        }
        model.addAttribute("cargo", cargoService.readAllCargo());
        model.addAttribute("form", waypointForm);
        return "orders/create";
    }

    @PostMapping("")
    public String createOrder(@ModelAttribute(name = "form") WaypointsCreationDto waypoints, Model model) {
        Order order = new Order();
        order.setWaypoints(waypoints.getWaypoints());
        orderService.createOrder(order);

        model.addAttribute("orders", orderService.readAllOrders());
        return "redirect:/orders/home";
    }

}
