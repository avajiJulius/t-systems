package com.logiweb.avaji.controller;

import com.logiweb.avaji.config.AppConfig;
import com.logiweb.avaji.config.TestPersistenceConfig;
import com.logiweb.avaji.dtos.DriverDTO;
import com.logiweb.avaji.dtos.TruckDTO;
import com.logiweb.avaji.entity.model.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig({AppConfig.class, TestPersistenceConfig.class})
@ActiveProfiles("integration")
@Transactional
class DriverControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        entityManager.persist(new City("A"));
        entityManager.flush();
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"employee:read"})
    void givenDriverPageURI_whenMockMVC_thenReturnsDriversListJSPViewName() throws Exception {
        this.mockMvc.perform(get("/drivers")).andDo(print())

                .andExpect(view().name("drivers/list"));
    }



    @Test
    @WithMockUser(username = "admin", authorities = {"employee:read", "employee:write"})
    void givenDriverDTO_thenReturnSuccessMessage() throws Exception {
        DriverDTO dto = new DriverDTO("test@gmail.com", "driver", "Test", "One", 1);
        this.mockMvc.perform(post("/drivers").flashAttr("driver", dto)).andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/drivers"))
                .andExpect(flash().attribute("message", "Driver was successfully created"));
    }
}