package com.logiweb.avaji.controller;

import com.logiweb.avaji.config.AppConfig;
import com.logiweb.avaji.exceptionhandler.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringJUnitWebConfig(AppConfig.class)
class ExceptionHandlerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    @WithAnonymousUser
    void givenURI_whenMockMVC_thenReturnsException403JSPViewName() throws Exception {
        this.mockMvc.perform(get("/orders")).andDo(print())
                .andExpect(view().name("exceptions/403"));
        this.mockMvc.perform(get("/drivers")).andDo(print())
                .andExpect(view().name("exceptions/403"));
        this.mockMvc.perform(get("/trucks")).andDo(print())
                .andExpect(view().name("exceptions/403"));
        this.mockMvc.perform(get("/profile")).andDo(print())
                .andExpect(view().name("exceptions/403"));

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"employee:read", "employee:write"})
    void givenURIWithNotRightValues_whenMockMVC_thenReturnsException500JSPViewName() throws Exception {
        this.mockMvc.perform(get("/trucks/12")).andDo(print())
                .andExpect(view().name("exceptions/500"));
    }
}
