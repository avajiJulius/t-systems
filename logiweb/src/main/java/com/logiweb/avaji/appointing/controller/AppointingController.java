package com.logiweb.avaji.appointing.controller;

import com.logiweb.avaji.appointing.service.api.AppointingService;
import com.logiweb.avaji.exceptions.ShiftSizeExceedException;
import com.logiweb.avaji.orderdetails.service.api.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AppointingController {

    private final AppointingService appointingService;
    private final OrderDetailsService orderDetailsService;

    @Autowired
    public AppointingController(AppointingService appointingService, OrderDetailsService orderDetailsService) {
        this.appointingService = appointingService;
        this.orderDetailsService = orderDetailsService;
    }

    @GetMapping("/{id}/trucks")
    public String getTrucksForOrder(@PathVariable("id") long orderId,
                                    Model model) {
        model.addAttribute("trucks", appointingService.readTrucksForOrder(orderId));
        model.addAttribute("orderId", orderId);
        return "appointing/trucks";
    }

    @PatchMapping("{orderId}/trucks/{truckId}")
    public String addTruckToOrder(@PathVariable("orderId") long orderId,
                                  @PathVariable("truckId") String truckId) {
        appointingService.addTruckToOrder(truckId, orderId);
        return "redirect:/orders";
    }


    @GetMapping("/{id}/drivers")
    public String getDriversForOrder(@PathVariable("id") long orderId,
                                     Model model) {
        model.addAttribute("drivers", appointingService.readDriverForOrder(orderId));
        model.addAttribute("shiftSize", orderDetailsService.calculateFreeSpaceInShift(orderId));
        model.addAttribute("orderId", orderId);
        return "appointing/drivers";
    }

    @PatchMapping("{orderId}/drivers")
    public String addDriversToOrder(@PathVariable("orderId") long orderId,
                                  @ModelAttribute List<Long> driversIds) throws ShiftSizeExceedException {
        if(driversIds.size() > orderDetailsService.calculateFreeSpaceInShift(orderId)) {
            throw new ShiftSizeExceedException();
        }
        appointingService.addDriversToOrder(driversIds, orderId);
        return "redirect:/orders";
    }
}
