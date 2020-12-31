package com.logiweb.avaji.appointing.controller;

import com.logiweb.avaji.appointing.service.api.AppointingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AppointingController {

    private final AppointingService appointingService;

    @Autowired
    public AppointingController(AppointingService appointingService) {
        this.appointingService = appointingService;
    }

    @GetMapping("/{id}/trucks")
    public String getTrucksForOrder(@PathVariable("id") Integer orderId,
                                    Model model) {
        model.addAttribute("trucks", appointingService.readTrucksForOrder(orderId));
        model.addAttribute("orderId", orderId);
        return "appointing/trucks";
    }

    @PatchMapping("{orderId}/trucks/{truckId}")
    public String addTruckToOrder(@PathVariable("orderId") Integer orderId,
                                  @PathVariable("truckId") String truckId) {
        appointingService.addTruckToOrder(truckId, orderId);
        return "redirect:/orders";
    }


    @GetMapping("/{id}/drivers")
    public String getDriversForOrder(@PathVariable("id") Integer orderId,
                                     Model model) {
        model.addAttribute("drivers", appointingService.readDriverForOrder(orderId));
        model.addAttribute("orderId", orderId);
        return "appointing/drivers";
    }
}
