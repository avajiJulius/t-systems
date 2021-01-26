package com.logiweb.avaji.controllers;

import com.logiweb.avaji.dtos.AddedDriversDto;
import com.logiweb.avaji.dtos.WaypointDTO;
import com.logiweb.avaji.dtos.CreateWaypointsDTO;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.entities.models.Order;
import com.logiweb.avaji.exceptions.ShiftSizeExceedException;
import com.logiweb.avaji.services.api.management.CargoService;
import com.logiweb.avaji.services.api.management.TruckService;
import com.logiweb.avaji.services.api.map.CountryMapService;
import com.logiweb.avaji.services.api.management.OrderService;
import com.logiweb.avaji.exceptions.LoadAndUnloadValidateException;
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
    private final TruckService truckService;

    private int quantity = 1;

    public OrderController(OrderService orderService, CargoService cargoService,
                           CountryMapService countryMapService, TruckService truckService) {
        this.orderService = orderService;
        this.cargoService = cargoService;
        this.countryMapService = countryMapService;
        this.truckService = truckService;
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
        for (int i = 0; i < quantity; i++) {
            waypointForm.addWaypointDto(new WaypointDTO());
        }
        model.addAttribute("cities", countryMapService.readAllCities());
        model.addAttribute("cargo", cargoService.readAllFreeCargo());
        model.addAttribute("form", waypointForm);
        return "orders/create";
    }

    @GetMapping("/new/add")
    public String addNewWaypoint() {
        quantity++;
        return "redirect:/orders/new";
    }

    @GetMapping("/new/remove")
    public String removeNewWaypoint(Model model) {
        if(quantity < 2) {
            model.addAttribute("message", "Quantity of Waypoint cannot be null");
            return "redirect:/orders/new";
        }
        quantity--;
        return "redirect:/orders/new";
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('employee:write')")
    public String createOrder(@ModelAttribute(name = "form") CreateWaypointsDTO waypoints,
                              Model model) throws LoadAndUnloadValidateException {

        orderService.createOrderByWaypoints(new Order(), waypoints);

        model.addAttribute("orders", orderService.readAllOrders());
        return "redirect:/orders";
    }

    @GetMapping("/{id}/trucks")
    @PreAuthorize("hasAuthority('employee:read')")
    public String getTrucksForOrder(@PathVariable("id") long orderId,
                                    Model model) {
        model.addAttribute("trucks", orderService.readTrucksForOrder(orderId));
        model.addAttribute("orderId", orderId);
        return "orders/trucks";
    }

    @PatchMapping("{orderId}/trucks/{truckId}")
    @PreAuthorize("hasAuthority('employee:write')")
    public String addTruckToOrder(@PathVariable("orderId") long orderId,
                                  @PathVariable("truckId") String truckId) {
        orderService.addTruckToOrder(truckId, orderId);
        return "redirect:/orders";
    }

    @GetMapping("/{id}/drivers")
    @PreAuthorize("hasAuthority('employee:read')")
    public String getDriversForOrder(@PathVariable("id") long orderId,
                                     Model model) {
        model.addAttribute("drivers", orderService.readDriversForOrder(orderId));
        model.addAttribute("shiftSize", truckService.calculateFreeSpaceInShift(orderId));
        model.addAttribute("orderId", orderId);
        model.addAttribute("driversIds", new AddedDriversDto());
        return "orders/drivers";
    }

    @PatchMapping("{orderId}/drivers")
    @PreAuthorize("hasAuthority('employee:write')")
    public String addDriversToOrder(@PathVariable("orderId") long orderId,
                                    @ModelAttribute("driversIds") AddedDriversDto driversIds) throws ShiftSizeExceedException {
        if(driversIds.getIds().size() > truckService.calculateFreeSpaceInShift(orderId)) {
            throw new ShiftSizeExceedException("Shift size exceed");
        }
        orderService.addDriversToOrder(driversIds.getIds(), orderId);
        return "redirect:/orders";
    }
}
