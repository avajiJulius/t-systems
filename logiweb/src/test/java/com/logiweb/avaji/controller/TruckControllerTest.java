package com.logiweb.avaji.controller;

import com.logiweb.avaji.config.AppConfig;
import com.logiweb.avaji.config.TestPersistenceConfig;
import com.logiweb.avaji.dtos.TruckDTO;
import com.logiweb.avaji.entity.model.City;
import com.logiweb.avaji.entity.model.Truck;
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
class TruckControllerTest {

    private Truck truck = new Truck("AC12345", 0, 1, 30.0, true, false,
            new City(1, "A"), null, null);

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @PersistenceContext
    private EntityManager entityManager;


    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"employee:read"})
    void givenTruckPageURI_whenMockMVC_thenReturnsTrucksListJSPViewName() throws Exception {
        this.mockMvc.perform(get("/trucks")).andDo(print())

                .andExpect(view().name("trucks/list"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"employee:read", "employee:write"})
    void givenTruckDTO_thenReturnSuccessMessage() throws Exception {
        TruckDTO dto = new TruckDTO("AC12345", 21.0, 1, true,
                1, "A", false);
        this.mockMvc.perform(post("/trucks").flashAttr("truck", dto)).andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trucks"))
                .andExpect(flash().attribute("message", "Truck was successfully created"));
    }

}