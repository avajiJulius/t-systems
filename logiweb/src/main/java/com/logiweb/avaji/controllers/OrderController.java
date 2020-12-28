package com.logiweb.avaji.controllers;

import com.logiweb.avaji.entities.dto.WaypointDto;
import com.logiweb.avaji.entities.dto.WaypointsCreationDto;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.services.api.CargoService;
import com.logiweb.avaji.services.api.CountryMapService;
import com.logiweb.avaji.services.api.OrderService;
import com.logiweb.avaji.services.api.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CargoService cargoService;
    private final TruckService truckService;
    private final CountryMapService countryMapService;

    @Autowired
    public OrderController(OrderService orderService, CargoService cargoService,
                           TruckService truckService, CountryMapService countryMapService) {
        this.orderService = orderService;
        this.cargoService = cargoService;
        this.truckService = truckService;
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
            waypointForm.addWaypointDto(new WaypointDto());
        }
        model.addAttribute("cities", countryMapService.readAllCities());
        model.addAttribute("cargo", cargoService.readAllCargo());
        model.addAttribute("form", waypointForm);
        return "orders/create";
    }

    @PostMapping()
    public String createOrder(@ModelAttribute(name = "form") WaypointsCreationDto waypoints,
                              Model model) {
        orderService.createOrderByWaypoints(new Order(), waypoints.getWaypointsDto());

        model.addAttribute("orders", orderService.readAllOrders());
        return "redirect:/orders";
    }

    @GetMapping("/{id}/trucks")
    public String getTrucksForOrder(@PathVariable("id") Integer orderId,
                                    Model model) {
        model.addAttribute("trucks", truckService.readTrucksForOrder(orderId));
        model.addAttribute("orderId", orderId);
        return "orders/trucks";
    }

    @PatchMapping("{orderId}/trucks/{truckId}")
    public String addTruckToOrder(@PathVariable("orderId") Integer orderId,
                                  @PathVariable("truckId") String truckId) {
        orderService.addTruckToOrder(truckId, orderId);
        return "redirect:/orders";
    }

    @GetMapping("/{id}/drivers")
    public String getDriversForOrder(@PathVariable("id") Integer orderId,
                                    Model model) {
        model.addAttribute("drivers", orderService.readDriverForOrder(orderId));
        model.addAttribute("orderId", orderId);
        return "orders/drivers";
    }


}
