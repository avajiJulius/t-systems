package com.logiweb.avaji.controllers;

import com.logiweb.avaji.entities.dto.TruckDto;
import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.services.CountryMapService;
import com.logiweb.avaji.services.TruckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/trucks")
public class TruckController {

    private static final Logger log = LoggerFactory.getLogger(TruckController.class);

    private final TruckService truckService;
    private final CountryMapService mapService;

    @Autowired
    public TruckController(TruckService truckService, CountryMapService mapService) {
        this.truckService = truckService;
        this.mapService = mapService;
    }


    @GetMapping()
    public String getTrucks(Model model) {
        List<Truck> truckList = truckService.readTrucks();

        model.addAttribute("truckList", truckList);
        return "trucks/list";
    }

    @GetMapping("/new")
    public String getTruckForm(@ModelAttribute("truck") TruckDto truckDto, Model model) {

        model.addAttribute("cities", mapService.readAllCities());
        return "trucks/create";
    }

    @PostMapping()
    public String createTruck(@ModelAttribute("truck") TruckDto truckDto) {
        truckService.createTruck(truckDto);
        return "redirect:/trucks";
    }

    @GetMapping("/{id}/edit")
    public String getTruckEditForm(Model model, @PathVariable("id") String id) {
        model.addAttribute("cities", mapService.readAllCities());
        model.addAttribute("truck", truckService.readTruckById(id));
        return "trucks/edit";
    }

    @PatchMapping("/{id}")
    public String editTruck(@PathVariable("id") String id,
                            @ModelAttribute("truck") Truck editTruck) {
        truckService.updateTruck(editTruck);
        return "redirect:/trucks";
    }

    @DeleteMapping("/{id}")
    public String deleteTruck(@PathVariable("id") String id) {
        truckService.deleteTruck(id);
        return "redirect:/trucks";
    }

}
