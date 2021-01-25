package com.logiweb.avaji.controllers;

import com.logiweb.avaji.daos.UserDAO;
import com.logiweb.avaji.dtos.CargoChangeDTO;
import com.logiweb.avaji.dtos.ShiftDetailsDTO;
import com.logiweb.avaji.services.api.profile.OrderDetailsService;
import com.logiweb.avaji.services.api.profile.ShiftDetailsService;
import com.logiweb.avaji.entities.enums.DriverStatus;
import com.logiweb.avaji.entities.models.User;
import com.logiweb.avaji.exceptions.ShiftValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final ShiftDetailsService shiftDetailsService;
    private final OrderDetailsService orderDetailsService;
    private final UserDAO userDAO;

    @Autowired
    public ProfileController(ShiftDetailsService shiftDetailsService, OrderDetailsService orderDetailsService,
                             UserDAO userDAO) {
        this.shiftDetailsService = shiftDetailsService;
        this.orderDetailsService = orderDetailsService;
        this.userDAO = userDAO;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('driver:read')")
    public String getProfile(Principal principal,
                                 Model model) {
        User user = userDAO.findUserByEmail(principal.getName());
        model.addAttribute("driverStatus", DriverStatus.values());
        model.addAttribute("orderDetails", orderDetailsService.readOrderDetails(user.getId()));
        model.addAttribute("shiftDetails", shiftDetailsService.readShiftDetails(user.getId()));
        model.addAttribute("cargoIds", new CargoChangeDTO());
        return "profile";
    }

    @PatchMapping("/shift")
    @PreAuthorize("hasAuthority('driver:write')")
    public String updateShiftDetails(@ModelAttribute("shiftDetails") ShiftDetailsDTO shiftDetails,
                                     Principal principal) throws ShiftValidationException {
        User user = userDAO.findUserByEmail(principal.getName());
        shiftDetails.setId(user.getId());
        shiftDetailsService.updateShiftDetails(shiftDetails);
        return "redirect:/profile";
    }


    @PatchMapping("/cargo")
    @PreAuthorize("hasAuthority('driver:write')")
    public String updateCargoStatus(@ModelAttribute("cargoIds") CargoChangeDTO cargoIds,
                                    Principal principal) throws ShiftValidationException {
        User user = userDAO.findUserByEmail(principal.getName());
        orderDetailsService.updateOrderByCargoStatus(user.getId(), cargoIds.getIds());
        return "redirect:/profile";
    }

    @GetMapping("/{id}/changeCity")
    @PreAuthorize("hasAuthority('driver:write')")
    public String updateRemainingPath(@PathVariable("id") long orderId,
                                      Principal principal) {
        User user = userDAO.findUserByEmail(principal.getName());
        orderDetailsService.changeCity(orderId, user.getId());
        return "redirect:/profile";
    }
}
