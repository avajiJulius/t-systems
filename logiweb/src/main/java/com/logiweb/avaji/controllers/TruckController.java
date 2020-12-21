package com.logiweb.avaji.controllers;

import com.logiweb.avaji.entities.models.CountryMap;
import com.logiweb.avaji.entities.models.Truck;
import com.logiweb.avaji.entities.models.utils.City;
import com.logiweb.avaji.services.CountryMapService;
import com.logiweb.avaji.services.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/trucks")
public class TruckController {

    private final TruckService truckService;

    @Autowired
    public TruckController(TruckService truckService) {
        this.truckService = truckService;
    }

    @GetMapping("")
    public String getTrucks(Model model) {
        List<Truck> truckList = truckService.readTrucks();

        model.addAttribute("truckList", truckList);
        return "trucks/list";
    }

    @GetMapping("/new")
    public String getTruckForm(Model model,
                               @RequestParam(name = "capacity") double capacity,
                               @RequestParam(name = "currentCity") String currentCity) {
//        City city = countryMapService.readCityByName(currentCity);
        Truck truck = new Truck();
        truck.setCapacity(capacity);

        model.addAttribute("truck", truck);

        return "trucks/create";
    }

    @PostMapping("")
    public String createTruck(@ModelAttribute("truck") Truck truck) {
        truckService.createTruck(truck);
        return "redirect:/trucks/";
    }

    @GetMapping("/{id}/edit")
    public String getTruckEditForm(Model model, @PathVariable("id") String id) {
        model.addAttribute("truck", truckService.readTruckById(id));

        return "trucks/update";
    }

    @PatchMapping("/{id}")
    public String editTruck(@PathVariable("id") String id,
                            @ModelAttribute Truck editTruck) {
        truckService.updateTruck(editTruck);

        return "redirect:/trucks/";
    }

    @DeleteMapping("/{id}")
    public String deleteTruck(@PathVariable("id") String id) {
        truckService.deleteTruck(id);

        return "redirect:/trucks/";
    }

}
