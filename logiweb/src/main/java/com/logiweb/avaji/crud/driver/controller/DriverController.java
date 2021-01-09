package com.logiweb.avaji.crud.driver.controller;

import com.logiweb.avaji.crud.countrymap.dao.CountryMapDAO;
import com.logiweb.avaji.crud.countrymap.service.api.CountryMapService;
import com.logiweb.avaji.crud.driver.dto.DriverDTO;
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
    private final CountryMapService mapService;

    @Autowired
    public DriverController(DriverService driverService, CountryMapService mapService) {
        this.driverService = driverService;
        this.mapService = mapService;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('employee:read')")
    public String getAllDrivers(Model model) {
        model.addAttribute("drivers", driverService.readAllDrivers());
        return "drivers/list";
    }


    @GetMapping("/new")
    @PreAuthorize("hasAuthority('employee:write')")
    public String getDriverForm(Model model) {
        DriverDTO driver = new DriverDTO();
        model.addAttribute("driver", driver);
        model.addAttribute("cities", mapService.readAllCities());
        return "drivers/create";
    }


    @PostMapping()
    @PreAuthorize("hasAuthority('employee:write')")
    public String createDriver(@ModelAttribute("driver")DriverDTO driver) {
        driverService.createDriver(driver);
        return "redirect:/drivers";
    }

}
