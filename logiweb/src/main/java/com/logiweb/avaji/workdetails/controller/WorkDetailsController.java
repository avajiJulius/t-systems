package com.logiweb.avaji.workdetails.controller;

import com.logiweb.avaji.workdetails.dto.WorkDetailsDto;
import com.logiweb.avaji.workdetails.service.api.WorkDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/details")
public class WorkDetailsController {

    private final WorkDetailsService workDetailsService;

    @Autowired
    public WorkDetailsController(WorkDetailsService workDetailsService) {
        this.workDetailsService = workDetailsService;
    }

    @GetMapping("/{id}")
    public String getWorkDetails(@PathVariable(name = "id")Integer driverId,
                                 Model model) {
        WorkDetailsDto workDetails = workDetailsService.readWorkDetailsByDriverId(driverId);
        model.addAttribute("details", workDetails);
        return "workDetails";
    }
}
