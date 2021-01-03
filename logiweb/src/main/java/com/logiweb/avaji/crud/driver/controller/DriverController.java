package com.logiweb.avaji.crud.driver.controller;

import com.logiweb.avaji.crud.driver.service.api.DriverService;
import com.logiweb.avaji.entities.models.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }



    @GetMapping()
    @PreAuthorize("hasAuthority('employee:read')")
    public String getDrivers(Model model) {
        model.addAttribute("drivers", driverService.readDrivers());
        return "drivers/list";
    }


    @GetMapping("/new")
    @PreAuthorize("hasAuthority('employee:write')")
    public String getDriverForm(Model model) {
        Driver driver = new Driver();
        model.addAttribute("driver", driver);
        return "drivers/create";
    }


    @PostMapping()
    @PreAuthorize("hasAuthority('employee:write')")
    public String createDriver(@ModelAttribute("driver")Driver driver) {
        driverService.createDriver(driver);
        return "redirect:/drivers";
    }

}
