package com.logiweb.avaji.service.implementetions.utils;

import com.logiweb.avaji.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(TestConfig.class)
@ActiveProfiles("test")
class DateTimeServiceTest {

    @Autowired
    public DateTimeService timeService;

    @Test
    void whenBeforeAndOfMonthRemainsOneHour_thenReturnOne() {
        Clock clock = Clock.fixed(Instant.parse("2019-01-31T23:00:00.00Z"), ZoneId.of("UTC"));

        long result = timeService.calculateTimeUntilEndOfMonth(clock);
        long expected = 1L;

        assertEquals(expected, result);
    }

    @Test
    void whenBeforeAndOfMonthRemainsOneDay_thenReturnTwentyFour() {
        Clock clock = Clock.fixed(Instant.parse("2019-01-31T00:00:00.00Z"), ZoneId.of("UTC"));

        long result = timeService.calculateTimeUntilEndOfMonth(clock);
        long expected = 24L;

        assertEquals(expected, result);
    }


    @Test
    void cronInRefreshWorkedHoursMethodWorkAsNeeded() {
        CronTrigger trigger = new CronTrigger("0 0 0 1 */1 *");
        Calendar today = Calendar.getInstance();
        today.set(2000, Calendar.FEBRUARY, 1);

        final Date now = today.getTime();
        Date result = trigger.nextExecutionTime(
                new TriggerContext() {

                    @Override
                    public Date lastScheduledExecutionTime() {
                        return now;
                    }

                    @Override
                    public Date lastActualExecutionTime() {
                        return now;
                    }

                    @Override
                    public Date lastCompletionTime() {
                        return now;
                    }
                });

        today.set(2000, Calendar.MARCH, 1, 0, 0, 0);
        Date expected = today.getTime();

        assertEquals(expected.toString(), result.toString());
    }
}