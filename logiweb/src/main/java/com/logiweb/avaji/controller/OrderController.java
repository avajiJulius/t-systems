package com.logiweb.avaji.controller;

import com.logiweb.avaji.dtos.AddedDriversDto;
import com.logiweb.avaji.dtos.WaypointDTO;
import com.logiweb.avaji.dtos.CreateWaypointsDTO;
import com.logiweb.avaji.entity.model.Cargo;
import com.logiweb.avaji.entity.model.Order;
import com.logiweb.avaji.exception.ShiftSizeExceedException;
import com.logiweb.avaji.service.api.management.CargoService;
import com.logiweb.avaji.service.api.management.TruckService;
import com.logiweb.avaji.service.api.map.CountryMapService;
import com.logiweb.avaji.service.api.management.OrderService;
import com.logiweb.avaji.exception.LoadAndUnloadValidateException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private static final int PAGE_SIZE = 5;
    private static final String MAIN_REDIRECT = "redirect:/orders";

    private final OrderService orderService;
    private final CargoService cargoService;
    private final CountryMapService countryMapService;
    private final TruckService truckService;


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
        return getOrdersPage(1, model);
    }

    @GetMapping("/page/{number}")
    @PreAuthorize("hasAuthority('employee:read')")
    public String getOrdersPage(@PathVariable("number") int pageNumber,
                                 Model model) {

        long totalNumber = orderService.getOrdersTotalNumbers();
        int totalPages = (int) Math.ceil((double) totalNumber / PAGE_SIZE);

        model.addAttribute("orders", orderService.readOrdersPage(pageNumber, PAGE_SIZE));
        model.addAttribute("totalItems", totalNumber);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", pageNumber);

        return "orders/list";
    }

    @GetMapping("/past")
    @PreAuthorize("hasAuthority('employee:read')")
    public String getAllPastOrders(Model model) {
        return getPastOrdersPage(1, model);
    }

    @GetMapping("/past/page/{number}")
    @PreAuthorize("hasAuthority('employee:read')")
    public String getPastOrdersPage(@PathVariable("number") int pageNumber,
                                Model model) {

        long totalNumber = orderService.getPastOrdersTotalNumbers();
        int totalPages = (int) Math.ceil((double) totalNumber / PAGE_SIZE);

        model.addAttribute("orders", orderService.readPastOrdersPage(pageNumber, PAGE_SIZE));
        model.addAttribute("totalItems", totalNumber);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", pageNumber);

        return "orders/history";
    }

    @GetMapping("/{id}/cargo")
    @PreAuthorize("hasAuthority('employee:read')")
    public String getCargoByOrder(Model model,
                                  @PathVariable(name = "id") long orderId) {

        List<Cargo> cargoList = cargoService.readCargoByOrderId(orderId);

        model.addAttribute("cargoList", cargoList);

        return "orders/cargo";
    }


    @GetMapping("/new/{quantity}")
    @PreAuthorize("hasAuthority('employee:write')")
    public String getOrderForm(@PathVariable(name = "quantity") Integer quantity,
                               Model model) {
        CreateWaypointsDTO waypointForm = new CreateWaypointsDTO();

        for (int i = 0; i < quantity; i++) {
            waypointForm.addWaypointDto(new WaypointDTO());
        }

        model.addAttribute("cities", countryMapService.readAllCities());
        model.addAttribute("cargo", cargoService.readAllFreeCargo());
        model.addAttribute("form", waypointForm);

        return "orders/create";
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('employee:write')")
    public String createOrder(@ModelAttribute(name = "form") @Validated CreateWaypointsDTO waypoints,
                              BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("cities", countryMapService.readAllCities());
            model.addAttribute("cargo", cargoService.readAllFreeCargo());
            model.addAttribute("form", waypoints);

            return "orders/create";
        }

        orderService.createOrderByWaypoints(new Order(), waypoints);

        return MAIN_REDIRECT;
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

        return MAIN_REDIRECT;
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

        return MAIN_REDIRECT;
    }
}
