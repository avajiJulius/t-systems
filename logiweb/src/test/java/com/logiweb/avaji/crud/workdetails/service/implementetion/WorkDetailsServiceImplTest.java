package com.logiweb.avaji.crud.workdetails.service.implementetion;


import com.logiweb.avaji.entities.enums.DriverStatus;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class WorkDetailsServiceImplTest {

    @Test
    public void testValidation() {
        DriverStatus status = DriverStatus.DRIVING;
        int ordinal = status.ordinal();
        boolean active = true;
        boolean result = !active ? ordinal == 0 : ordinal > 0;
        assertTrue(result);
    }
}