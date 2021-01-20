package com.logiweb.avaji.controllers;

import com.logiweb.avaji.dtos.AddedDriversDto;
import com.logiweb.avaji.services.api.OrderDetailsService;
import com.logiweb.avaji.exceptions.ShiftSizeExceedException;
import com.logiweb.avaji.services.api.PathDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OrderDetailsController {

    private final OrderDetailsService orderDetailsService;
    private final PathDetailsService pathDetailsService;

    @Autowired
    public OrderDetailsController(OrderDetailsService orderDetailsService, PathDetailsService pathDetailsService) {
        this.orderDetailsService = orderDetailsService;
        this.pathDetailsService = pathDetailsService;
    }


}
