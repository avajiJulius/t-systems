package com.logiweb.avaji.controller;

import com.logiweb.avaji.config.AppConfig;
import com.logiweb.avaji.config.TestPersistenceConfig;
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



import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringJUnitWebConfig({AppConfig.class, TestPersistenceConfig.class})
@ActiveProfiles("integration")
@Transactional
class OrderControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"employee:read"})
    void givenOrdersPageURI_whenMockMVC_thenReturnsOrdersListJSPViewName() throws Exception {
        this.mockMvc.perform(get("/orders")).andDo(print())

                .andExpect(view().name("orders/list"));
    }


}