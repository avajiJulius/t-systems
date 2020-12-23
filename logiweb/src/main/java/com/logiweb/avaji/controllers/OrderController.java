package com.logiweb.avaji.controllers;

import com.logiweb.avaji.entities.dto.WaypointsCreationDto;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.entities.models.utils.Waypoint;
import com.logiweb.avaji.services.CargoService;
import com.logiweb.avaji.services.CountryMapService;
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
    private final CountryMapService countryMapService;

    @Autowired
    public OrderController(OrderService orderService, CargoService cargoService,
                           CountryMapService countryMapService) {
        this.orderService = orderService;
        this.cargoService = cargoService;
        this.countryMapService = countryMapService;
    }

    @GetMapping()
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.readAllOrders());
        return "orders/list";
    }

    @GetMapping("/{id}/cargo")
    public String getCargoByOrder(Model model,
                                  @PathVariable(name = "id") Integer orderId) {
        List<Cargo> cargoList = cargoService.readCargoByOrderId(orderId);
        model.addAttribute("cargoList", cargoList);
        return "orders/cargo";
    }

    @GetMapping("/new")
    public String getOrderForm(Model model) {
        WaypointsCreationDto waypointForm = new WaypointsCreationDto();

        for (int i = 1; i <= 4; i++) {
            waypointForm.addWaypoint(new Waypoint());
        }
        model.addAttribute("cities", countryMapService.readAllCities());
        model.addAttribute("cargo", cargoService.readAllCargo());
        model.addAttribute("form", waypointForm);
        return "orders/create";
    }

    @PostMapping("")
    public String createOrder(@ModelAttribute(name = "form") WaypointsCreationDto waypoints,
                              Model model) {
        orderService.createOrderByWaypoints(waypoints.getWaypoints());

        model.addAttribute("orders", orderService.readAllOrders());
        return "redirect:/orders/";
    }

}
