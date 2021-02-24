package com.logiweb.avaji.controller;

import com.logiweb.avaji.dao.UserDAO;
import com.logiweb.avaji.dtos.CargoChangeDTO;
import com.logiweb.avaji.dtos.OrderDetailsDTO;
import com.logiweb.avaji.dtos.ShiftDetailsDTO;
import com.logiweb.avaji.service.api.profile.OrderDetailsService;
import com.logiweb.avaji.service.api.profile.ShiftDetailsService;
import com.logiweb.avaji.entity.enums.DriverStatus;
import com.logiweb.avaji.entity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private static final String MAIN_REDIRECT = "redirect:/profile";

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
                                     Principal principal) {

        User user = userDAO.findUserByEmail(principal.getName());

        shiftDetailsService.changeShiftDetails(user.getId(), shiftDetails.getDriverStatus());

        return MAIN_REDIRECT;
    }

    @PatchMapping("/cargo")
    @PreAuthorize("hasAuthority('driver:write')")
    public String updateCargoStatus(@ModelAttribute("cargoIds") CargoChangeDTO cargoIds,
                                    Principal principal) {

        User user = userDAO.findUserByEmail(principal.getName());

        orderDetailsService.updateOrderByCargoStatus(user.getId(), cargoIds.getIds());

        return MAIN_REDIRECT;
    }

    @GetMapping("/{id}/changeCity")
    @PreAuthorize("hasAuthority('driver:write')")
    public String updateRemainingPath(@PathVariable("id") long orderId, Principal principal,
                                      RedirectAttributes attributes) {

        User user = userDAO.findUserByEmail(principal.getName());
        OrderDetailsDTO orderDetails = orderDetailsService.readOrderDetails(user.getId());

        if (!orderDetails.getLoadCargo().isEmpty() || !orderDetails.getUnloadCargo().isEmpty()) {
            attributes.addFlashAttribute("message", "Complete load/unload actions");

            return MAIN_REDIRECT;
        }

        orderDetailsService.changeCity(user.getId(), orderId);

        return MAIN_REDIRECT;
    }

}
