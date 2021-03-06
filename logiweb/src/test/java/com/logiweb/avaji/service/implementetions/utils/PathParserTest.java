package com.logiweb.avaji.service.implementetions.utils;

import com.logiweb.avaji.config.TestConfig;
import com.logiweb.avaji.dao.CountryMapDAO;
import com.logiweb.avaji.entity.model.City;
import com.logiweb.avaji.exception.PathParseException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringJUnitConfig(TestConfig.class)
@ActiveProfiles("test")
class PathParserTest {

    @Autowired
    public CountryMapDAO countryMapDAO;

    @Autowired
    public PathParser parser;

    @Test
    void whenParseListThenReturnRightString1() {
        List<Long> path = Stream.of(1L, 2L, 3L, 4L, 5L , 6L).collect(Collectors.toList());
        String result = parser.parseLongListToString(path);
        String expected = "1-2-3-4-5-6-";

        assertEquals(expected, result);
    }


    @Test
    void whenParseListThenReturnRightString2() {
        List<Long> path = Stream.of(11L, 3L, 34L, 4L ,45L, 56L , 6L).collect(Collectors.toList());
        String result = parser.parseLongListToString(path);
        String expected = "11-3-34-4-45-56-6-";

        assertEquals(expected, result);
    }

    @Test
    void whenParseListThenReturnWrongString1() {
        List<Long> path = Stream.of(1L, 2L, 3L, 4L, 5L , 6L).collect(Collectors.toList());
        String result = parser.parseLongListToString(path);
        String expected = "1-2-4-3-5-6-";

        assertNotEquals(expected, result);
    }


    @Test
    void whenParseEmptyListThenReturnEmptyString() {
        List<Long> path = new ArrayList<>();
        String result = parser.parseLongListToString(path);
        String expected = "";

        assertEquals(expected, result);
    }


    @Test
    void whenParseStringThenReturnRightPrettyPath1() {
        String result = parser.toPrettyPath("10-2-4-9-1-2-");
        String expected = "J - B - D - I - A - B";

        assertEquals(expected, result);
    }



    @Test
    void whenParseStringThenReturnRightPrettyPath2() {
        String result = parser.toPrettyPath("1-2-3-4-5-6-");
        String expected = "A - B - C - D - E - F";

        assertEquals(expected, result);
    }

    @Test
    void whenParseStringThenReturnRightList1() {
        String path = "6-4-2-2-1-4-";
        List<Long> result = parser.parseStringToLongList(path);
        List<Long> expected = Stream.of(6L, 4L, 2L, 2L, 1L, 4L).collect(Collectors.toList());

        assertEquals(expected, result);
    }

    @Test
    void whenParseStringThenReturnRightList2() {
        String path = "32-23-33-2-21-11-6-";
        List<Long> result = parser.parseStringToLongList(path);
        List<Long> expected = Stream.of(32L, 23L, 33L, 2L, 21L, 11L, 6L).collect(Collectors.toList());

        assertEquals(expected, result);
    }


    @Test
    void whenParseStringThenReturnWrongList1() {
        String path = "6-4-2-2-1-4-";
        List<Long> result = parser.parseStringToLongList(path);
        List<Long> expected = Stream.of(6L, 4L, 2L, 2L, 2L, 4L).collect(Collectors.toList());

        assertNotEquals(expected, result);
    }
    @Test
    void whenParseEmptyStringThenThrowException() {
        String path = "";

        assertThrows(PathParseException.class, () -> parser.parseStringToLongList(path));
    }

    @Test
    void whenParseUncorrectStringThenThrowException() {
        String path = "32-23-33a-2-21-11-6-";

        assertThrows(PathParseException.class, () -> parser.parseStringToLongList(path));
    }

}