package com.logiweb.avaji.service.implementetions.map;

import com.logiweb.avaji.config.TestConfig;
import com.logiweb.avaji.dao.CountryMapDAO;
import com.logiweb.avaji.service.api.map.CountryMapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(TestConfig.class)
@ActiveProfiles("test")
class CountryMapServiceImplTest {

    private CountryMapService countryMapService;

    @Autowired
    private CountryMapDAO countryMapDAO;

    @BeforeEach
    void setUp() {
        countryMapService = new CountryMapServiceImpl(countryMapDAO);
    }

    @Test
    void readCityNameByCode() {
        String a = countryMapService.readCityNameByCode(1);
        String b = countryMapService.readCityNameByCode(2);
        String c = countryMapService.readCityNameByCode(3);
        String d = countryMapService.readCityNameByCode(4);

        assertEquals("A", a);
        assertEquals("B", b);
        assertEquals("C", c);
        assertEquals("D", d);
    }
}