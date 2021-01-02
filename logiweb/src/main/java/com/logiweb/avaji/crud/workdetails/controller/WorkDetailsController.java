package com.logiweb.avaji.crud.workdetails.controller;

import com.logiweb.avaji.auth.dao.UserDAO;
import com.logiweb.avaji.crud.workdetails.dto.WorkDetailsDto;
import com.logiweb.avaji.crud.workdetails.service.api.WorkDetailsService;
import com.logiweb.avaji.entities.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        WorkDetailsDto workDetails = workDetailsService.readWorkDetailsByUserId(user.getId());
        model.addAttribute("details", workDetails);
        return "workDetails";
    }
}
