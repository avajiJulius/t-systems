package com.logiweb.avaji.orderdetails.controller;

import com.logiweb.avaji.orderdetails.dto.AddedDriversDto;
import com.logiweb.avaji.orderdetails.service.api.OrderDetailsService;
import com.logiweb.avaji.exceptions.ShiftSizeExceedException;
import com.logiweb.avaji.orderdetails.service.api.ShiftDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class OrderDetailsController {

    private final OrderDetailsService orderDetailsService;
    private final ShiftDetailsService shiftDetailsService;

    @Autowired
    public OrderDetailsController(OrderDetailsService orderDetailsService, ShiftDetailsService shiftDetailsService) {
        this.orderDetailsService = orderDetailsService;
        this.shiftDetailsService = shiftDetailsService;
    }

    @GetMapping("/{id}/trucks")
    @PreAuthorize("hasAuthority('employee:read')")
    public String getTrucksForOrder(@PathVariable("id") long orderId,
                                    Model model) {
        model.addAttribute("trucks", orderDetailsService.readTrucksForOrder(orderId));
        model.addAttribute("orderId", orderId);
        return "orderDetails/trucks";
    }

    @PatchMapping("{orderId}/trucks/{truckId}")
    @PreAuthorize("hasAuthority('employee:write')")
    public String addTruckToOrder(@PathVariable("orderId") long orderId,
                                  @PathVariable("truckId") String truckId) {
        orderDetailsService.addTruckToOrder(truckId, orderId);
        return "redirect:/orders";
    }


    @GetMapping("/{id}/drivers")
    @PreAuthorize("hasAuthority('employee:read')")
    public String getDriversForOrder(@PathVariable("id") long orderId,
                                     Model model) {
        model.addAttribute("drivers", orderDetailsService.readDriverForOrder(orderId));
        model.addAttribute("shiftSize", shiftDetailsService.calculateFreeSpaceInShift(orderId));
        model.addAttribute("orderId", orderId);
        model.addAttribute("driversIds", new AddedDriversDto());
        return "orderDetails/drivers";
    }


    @PatchMapping("{orderId}/drivers")
    @PreAuthorize("hasAuthority('employee:write')")
    public String addDriversToOrder(@PathVariable("orderId") long orderId,
                                  @ModelAttribute("driversIds") AddedDriversDto driversIds) throws ShiftSizeExceedException {
        if(driversIds.getIds().size() > shiftDetailsService.calculateFreeSpaceInShift(orderId)) {
            throw new ShiftSizeExceedException();
        }
        orderDetailsService.addDriversToOrder(driversIds.getIds(), orderId);
        return "redirect:/orders";
    }
}
