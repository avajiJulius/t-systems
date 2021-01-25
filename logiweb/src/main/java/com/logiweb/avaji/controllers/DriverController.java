package com.logiweb.avaji.controllers;

import com.logiweb.avaji.services.api.CountryMapService;
import com.logiweb.avaji.dtos.DriverDTO;
import com.logiweb.avaji.services.api.DriverService;
import com.logiweb.avaji.services.implementetions.DriverServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/drivers")
public class DriverController {

    private static final Logger logger = LogManager.getLogger(DriverController.class);

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
    public String createDriver(@ModelAttribute("driver") @Validated(DriverDTO.Create.class) DriverDTO driver,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("driver", new DriverDTO());
            model.addAttribute("cities", mapService.readAllCities());
            return "drivers/create";
        }
        driverService.createDriver(driver);
        return "redirect:/drivers";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('employee:write')")
    public String getDriverEditForm(Model model, @PathVariable("id") long id) {
        model.addAttribute("cities", mapService.readAllCities());
        model.addAttribute("driver", driverService.readDriverById(id));
        return "drivers/edit";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('employee:write')")
    public String editDriver(@PathVariable("id") long id,
                            @ModelAttribute("driver") @Validated(DriverDTO.Update.class) DriverDTO editDriver,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("driver", driverService.readDriverById(id));
            model.addAttribute("cities", mapService.readAllCities());
            return "drivers/edit";
        }
        driverService.updateDriver(id, editDriver);
        return "redirect:/drivers";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('employee:write')")
    public String deleteDriver(@PathVariable("id") long id) {
        driverService.deleteDriver(id);
        return "redirect:/drivers";
    }
}
