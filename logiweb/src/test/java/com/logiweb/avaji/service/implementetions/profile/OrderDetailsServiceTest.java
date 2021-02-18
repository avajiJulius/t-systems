package com.logiweb.avaji.service.implementetions.profile;

import com.logiweb.avaji.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(TestConfig.class)
@ActiveProfiles("test")
class OrderDetailsServiceTest {

    @Autowired
    public OrderDetailsServiceImpl orderDetailsService;

    @Test
    void whenAllCargoStatusAreDeliveredReturnTrue() {
        boolean result = orderDetailsService.orderIsComplete(4);

        assertTrue(result);
    }

    @Test
    void whenNotAllCargoStatusAreDeliveredReturnFalse() {
        boolean result = orderDetailsService.orderIsComplete(3);

        assertFalse(result);
    }


}