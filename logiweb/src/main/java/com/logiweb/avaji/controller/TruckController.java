package com.logiweb.avaji.controller;

import com.logiweb.avaji.dtos.TruckDTO;
import com.logiweb.avaji.service.api.map.CountryMapService;
import com.logiweb.avaji.service.api.management.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/trucks")
public class TruckController {

    private final TruckService truckService;
    private final CountryMapService mapService;

    @Autowired
    public TruckController(TruckService truckService, CountryMapService mapService) {
        this.truckService = truckService;
        this.mapService = mapService;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('employee:read')")
    public String getTrucks(Model model) {
        List<TruckDTO> truckList = truckService.readTrucks();

        model.addAttribute("truckList", truckList);
        return "trucks/list";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('employee:write')")
    public String getTruckForm(@ModelAttribute("truck") TruckDTO truckDto, Model model) {

        model.addAttribute("cities", mapService.readAllCities());
        return "trucks/create";
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('employee:write')")
    public String createTruck(@ModelAttribute("truck") @Validated(TruckDTO.Create.class) TruckDTO truckDto,
                              BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("cities", mapService.readAllCities());
            return "trucks/create";
        }

        boolean isCreated = truckService.createTruck(truckDto);
        if(isCreated) {
            attributes.addFlashAttribute("message", "Truck was successfully created");
        } else {
            attributes.addFlashAttribute("error", "Truck not created");
        }
        return "redirect:/trucks";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('employee:write')")
    public String getTruckEditForm(Model model, @PathVariable("id") String id) {
        model.addAttribute("cities", mapService.readAllCities());
        model.addAttribute("truck", truckService.readTruckById(id));
        return "trucks/edit";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('employee:write')")
    public String editTruck(@PathVariable("id") String id,
                            @ModelAttribute("truck") @Validated(TruckDTO.Update.class) TruckDTO editTruck,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("truck", truckService.readTruckById(id));
            model.addAttribute("cities", mapService.readAllCities());
            return "trucks/edit";
        }
        truckService.updateTruck(id, editTruck);
        return "redirect:/trucks";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('employee:write')")
    public String deleteTruck(@PathVariable("id") String id, RedirectAttributes attributes) {
        boolean isDeleted = truckService.deleteTruck(id);
        if(isDeleted) {
            attributes.addFlashAttribute("message", "Success delete truck with ID: " + id);
        } else {
            attributes.addFlashAttribute("error", "Truck not deleted");
        }
        return "redirect:/trucks";

    }

}
