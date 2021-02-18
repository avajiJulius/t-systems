package com.logiweb.avaji.service.implementetions.path;

import com.logiweb.avaji.config.TestConfig;
import com.logiweb.avaji.entity.model.Path;
import com.logiweb.avaji.dtos.WaypointDTO;
import com.logiweb.avaji.exception.SuboptimalPathException;
import com.logiweb.avaji.service.api.path.PathDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(TestConfig.class)
@ActiveProfiles("test")
class PathDetailsServiceTest {

    @Autowired
    private PathDetailsService pathDetailsService;

    private List<WaypointDTO> waypoints1 = Stream.of(
            new WaypointDTO(1,5,1,11),
            new WaypointDTO(1,2,2,22),
            new WaypointDTO(3,4,3,33)).collect(Collectors.toList());

    private List<WaypointDTO> waypoints2 = Stream.of(
            new WaypointDTO(4,4,1,11),
            new WaypointDTO(3,3,2,22),
            new WaypointDTO(7,7,3,33)).collect(Collectors.toList());


    private List<WaypointDTO> waypoints3 =  Stream.of(
            new WaypointDTO(1,5,1,11),
            new WaypointDTO(3,7,2,22),
            new WaypointDTO(2,5,3,33),
            new WaypointDTO(7,1,4,44),
            new WaypointDTO(4,1,5,55),
            new WaypointDTO(5,2,6,66)).collect(Collectors.toList());


    private List<WaypointDTO> waypoints4 = Stream.of(
            new WaypointDTO(1,7,1,11)).collect(Collectors.toList());


    private List<WaypointDTO> waypoints5 = Stream.of(
            new WaypointDTO(6,4,1,11)).collect(Collectors.toList());


    @Test
    void returnSamePathOfWaypointsAsExpected1() {
        Path path = pathDetailsService.getPath(waypoints1);
        Path result = new Path.Builder().withPath(
                Stream.of(1L,2L,3L,5L,4L).collect(Collectors.toList())
        ).build();

        assertEquals(result.getPath(), path.getPath());
    }


    @Test
    void returnSamePathOfWaypointsAsExpected2() {
        Path path = pathDetailsService.getPath(waypoints2);
        Path result = new Path.Builder().withPath(
                Stream.of(4L,2L,3L,5L,7L).collect(Collectors.toList())
        ).build();

        assertEquals(result.getPath(), path.getPath());
    }
    @Test
    void returnSamePathOfWaypointsAsExpected3() {
        Path path = pathDetailsService.getPath(waypoints3);
        Path result = new Path.Builder().withPath(
                Stream.of(1L,2L,5L,2L,3L,2L,4L,2L,1L,3L,5L,7L,6L,1L).collect(Collectors.toList())
        ).build();

        assertEquals(result.getPath(), path.getPath());
    }


    @Test
    void returnSamePathOfWaypointsAsExpected4() {
        Path path = pathDetailsService.getPath(waypoints4);
        Path result = new Path.Builder().withPath(
                Stream.of(1L,3L,5L,7L).collect(Collectors.toList())
        ).build();

        assertEquals(result.getPath(), path.getPath());
    }

    @Test
    void returnSamePathOfWaypointsAsExpected5() {
        Path path = pathDetailsService.getPath(waypoints5);
        Path result = new Path.Builder().withPath(
                Stream.of(6L,5L,4L).collect(Collectors.toList())
        ).build();

        assertEquals(result.getPath(), path.getPath());
    }

    @Test
    void returnDistanceAsExpected1() {
        double distance = pathDetailsService.getPath(waypoints1).getDistance();
        double result = 7;

        assertEquals(result, distance);
    }

    @Test
    void returnDistanceAsExpected2() {
        double distance = pathDetailsService.getPath(waypoints2).getDistance();
        double result = 8;

        assertEquals(result, distance);
    }

    @Test
    void returnDistanceAsExpected3() {
        double distance = pathDetailsService.getPath(waypoints3).getDistance();
        double result = 29;

        assertEquals(result, distance);
    }

    @Test
    void returnDistanceAsExpected4() {
        double distance = pathDetailsService.getPath(waypoints4).getDistance();
        double result = 7;

        assertEquals(result, distance);
    }

    @Test
    void returnDistanceAsExpected5() {
        double distance = pathDetailsService.getPath(waypoints5).getDistance();
        double result = 5;

        assertEquals(result, distance);
    }

    @Test
    void returnShiftHoursAsExpected1() {
        Path path = pathDetailsService.getPath(waypoints1);
        double shiftHours = pathDetailsService.getShiftHours(path.getPath());
        double result = 7;

        assertEquals(result, shiftHours);
    }

    @Test
    void returnShiftHoursAsExpected2() {
        Path path = pathDetailsService.getPath(waypoints2);
        double shiftHours = pathDetailsService.getShiftHours(path.getPath());
        double result = 8;

        assertEquals(result, shiftHours);
    }

    @Test
    void returnShiftHoursAsExpected3() {
        Path path = pathDetailsService.getPath(waypoints3);
        double shiftHours = pathDetailsService.getShiftHours(path.getPath());
        double result = 29;

        assertEquals(result, shiftHours);
    }

    @Test
    void returnShiftHoursAsExpected4() {
        Path path = pathDetailsService.getPath(waypoints4);
        double shiftHours = pathDetailsService.getShiftHours(path.getPath());
        double result = 7;

        assertEquals(result, shiftHours);
    }

    @Test
    void returnShiftHoursAsExpected5() {
        Path path = pathDetailsService.getPath(waypoints5);
        double shiftHours = pathDetailsService.getShiftHours(path.getPath());
        double result = 5;

        assertEquals(result, shiftHours);
    }

    @Test
    void returnMaxCapacityAsExpected1() {
        Path path = pathDetailsService.getPath(waypoints1);
        double maxCapacity = pathDetailsService.getMaxCapacityInTons(path.getPath(), waypoints1);
        double result = 0.044;

        assertEquals(result, maxCapacity);
    }

    @Test
    void whenGiveWaypoints2ThenThrowSuboptimalPathException() {
        Path path = pathDetailsService.getPath(waypoints2);

        assertThrows(SuboptimalPathException.class, () -> pathDetailsService.getMaxCapacityInTons(path.getPath(), waypoints2));
    }

    @Test
    void whenGiveWaypoints3ThenThrowSuboptimalPathException() {
        Path path = pathDetailsService.getPath(waypoints3);

        assertThrows(SuboptimalPathException.class, () -> pathDetailsService.getMaxCapacityInTons(path.getPath(), waypoints3));
    }

    @Test
    void returnMaxCapacityAsExpected4() {
        Path path = pathDetailsService.getPath(waypoints4);
        double maxCapacity = pathDetailsService.getMaxCapacityInTons(path.getPath(), waypoints4);
        double result = 0.011;

        assertEquals(result, maxCapacity);
    }

    @Test
    void returnMaxCapacityAsExpected5() {
        Path path = pathDetailsService.getPath(waypoints5);
        double maxCapacity = pathDetailsService.getMaxCapacityInTons(path.getPath(), waypoints5);
        double result = 0.011;

        assertEquals(result, maxCapacity);
    }

}