package com.logiweb.avaji.controllers;

import com.logiweb.avaji.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }


    @GetMapping()
    public String getDrivers(Model model) {
        model.addAttribute("drivers", driverService.readDrivers());
        return "drivers/list";
    }

}
