package com.logiweb.avaji.crud.order.controller;

import com.logiweb.avaji.crud.order.dto.WaypointDTO;
import com.logiweb.avaji.crud.order.dto.CreateWaypointsDTO;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.crud.cargo.service.api.CargoService;
import com.logiweb.avaji.crud.countrymap.service.api.CountryMapService;
import com.logiweb.avaji.crud.order.service.api.OrderService;
import com.logiweb.avaji.exceptions.CityValidateException;
import com.logiweb.avaji.exceptions.LoadAndUnloadValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("hasAuthority('employee:read')")
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.readAllOrders());
        return "orders/list";
    }

    @GetMapping("/{id}/cargo")
    @PreAuthorize("hasAuthority('employee:read')")
    public String getCargoByOrder(Model model,
                                  @PathVariable(name = "id") long orderId) {
        List<Cargo> cargoList = cargoService.readCargoByOrderId(orderId);
        model.addAttribute("cargoList", cargoList);
        return "orders/cargo";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('employee:write')")
    public String getOrderForm(Model model) {
        CreateWaypointsDTO waypointForm = new CreateWaypointsDTO();
        for (int i = 1; i <= 4; i++) {
            waypointForm.addWaypointDto(new WaypointDTO());
        }
        model.addAttribute("cities", countryMapService.readAllCities());
        model.addAttribute("cargo", cargoService.readAllCargo());
        model.addAttribute("form", waypointForm);
        return "orders/create";
    }


    @PostMapping()
    @PreAuthorize("hasAuthority('employee:write')")
    public String createOrder(@ModelAttribute(name = "form") CreateWaypointsDTO waypoints,
                              Model model) throws CityValidateException, LoadAndUnloadValidateException {

        orderService.createOrderByWaypoints(new Order(), waypoints);

        model.addAttribute("orders", orderService.readAllOrders());
        return "redirect:/orders";
    }





}
