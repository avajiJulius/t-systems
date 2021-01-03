package com.logiweb.avaji.crud.truck.controller;

import com.logiweb.avaji.crud.truck.dto.TruckDto;
import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.crud.countrymap.service.api.CountryMapService;
import com.logiweb.avaji.crud.truck.service.api.TruckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/trucks")
public class    TruckController {

    private static final Logger log = LoggerFactory.getLogger(TruckController.class);

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
        List<Truck> truckList = truckService.readTrucks();

        model.addAttribute("truckList", truckList);
        return "trucks/list";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('employee:write')")
    public String getTruckForm(@ModelAttribute("truck") TruckDto truckDto, Model model) {

        model.addAttribute("cities", mapService.readAllCities());
        return "trucks/create";
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('employee:write')")
    public String createTruck(@ModelAttribute("truck") @Validated(TruckDto.Create.class) TruckDto truckDto,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "trucks/create";
        }
        truckService.createTruck(truckDto);
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
                            @ModelAttribute("truck") @Validated(TruckDto.Update.class) TruckDto editTruck,
                            BindingResult result) {
        if (result.hasErrors()) {
            return "trucks/edit";
        }
        truckService.updateTruck(id, editTruck);
        return "redirect:/trucks";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('employee:write')")
    public String deleteTruck(@PathVariable("id") String id) {
        truckService.deleteTruck(id);
        return "redirect:/trucks";
    }

}
