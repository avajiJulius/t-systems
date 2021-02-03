package com.logiweb.avaji.controller;

import com.logiweb.avaji.service.api.map.CountryMapService;
import com.logiweb.avaji.dtos.DriverDTO;
import com.logiweb.avaji.service.api.management.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/drivers")
public class DriverController {

    private final int PAGE_SIZE = 5;

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
        return getDriversPage(1, model);
    }

    @GetMapping("/page/{number}")
    @PreAuthorize("hasAuthority('employee:read')")
    public String getDriversPage(@PathVariable("number") int pageNumber,
                                 Model model) {
        long totalNumber = driverService.getDriversTotalNumber();
        int totalPages = (int) Math.ceil((double) totalNumber / PAGE_SIZE);
        model.addAttribute("drivers", driverService.readDriversPage(pageNumber, PAGE_SIZE));
        model.addAttribute("totalItems", totalNumber);
        model.addAttribute("totalPages",  totalPages);
        model.addAttribute("currentPage", pageNumber);
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
                               BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("driver", new DriverDTO());
            model.addAttribute("cities", mapService.readAllCities());
            return "drivers/create";
        }

        boolean isCreated = driverService.createDriver(driver);
        if(isCreated) {
            attributes.addFlashAttribute("message", "Driver was successfully created");
        } else {
            attributes.addFlashAttribute("error", "Driver not created");
        }
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
    public String deleteDriver(@PathVariable("id") long id,  RedirectAttributes attributes) {
        boolean isDeleted = driverService.deleteDriver(id);
        if(isDeleted) {
            attributes.addFlashAttribute("message", "Success delete truck with ID: " + id);
        } else {
            attributes.addFlashAttribute("error", "Truck not deleted");
        }
        return "redirect:/drivers";
    }
}
