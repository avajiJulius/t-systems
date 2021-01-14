package com.logiweb.avaji.crud.workdetails.controller;

import com.logiweb.avaji.auth.dao.UserDAO;
import com.logiweb.avaji.crud.workdetails.dto.CargoChangeDTO;
import com.logiweb.avaji.crud.workdetails.dto.ShiftDetailsDto;
import com.logiweb.avaji.crud.workdetails.dto.WorkDetailsDTO;
import com.logiweb.avaji.crud.workdetails.service.api.WorkDetailsService;
import com.logiweb.avaji.entities.enums.DriverStatus;
import com.logiweb.avaji.entities.models.User;
import com.logiweb.avaji.exceptions.DriverStatusNotFoundException;
import com.logiweb.avaji.exceptions.ShiftValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class WorkDetailsController {

    private final WorkDetailsService workDetailsService;
    private final UserDAO userDAO;

    @Autowired
    public WorkDetailsController(WorkDetailsService workDetailsService, UserDAO userDAO) {
        this.workDetailsService = workDetailsService;
        this.userDAO = userDAO;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('driver:read')")
    public String getWorkDetails(Principal principal,
                                 Model model) {
        User user = userDAO.findUserByEmail(principal.getName());
        WorkDetailsDTO details = workDetailsService.readWorkDetailsById(user.getId());
        model.addAttribute("driverStatus", DriverStatus.values());
        model.addAttribute("details", details);
        model.addAttribute("shiftDetails", new ShiftDetailsDto(details.isShiftActive(), details.getDriverStatus().name()));
        model.addAttribute("cargoIds", new CargoChangeDTO());
        return "workDetails";
    }

    @PatchMapping("/shift")
    @PreAuthorize("hasAuthority('driver:write')")
    public String updateShiftDetails(@ModelAttribute("shiftDetails") ShiftDetailsDto shiftDetails,
                                     Principal principal) throws ShiftValidationException, DriverStatusNotFoundException {
        User user = userDAO.findUserByEmail(principal.getName());

        workDetailsService.updateShiftDetails(user.getId(), shiftDetails);
        return "redirect:/profile";
    }

    @PatchMapping("/cargo")
    @PreAuthorize("hasAuthority('driver:write')")
    public String updateCargoStatus(@ModelAttribute("cargoIds") CargoChangeDTO cargoIds){

        workDetailsService.updateCargoStatus(cargoIds.getIds());
        return "redirect:/profile";
    }
}
