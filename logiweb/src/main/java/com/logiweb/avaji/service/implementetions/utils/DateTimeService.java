package com.logiweb.avaji.service.implementetions.utils;

import com.logiweb.avaji.dao.ScheduleDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

@Service
public class DateTimeService {

    private static final Logger logger = LogManager.getLogger(DateTimeService.class);


    private final ScheduleDAO scheduleDAO;

    @Autowired
    public DateTimeService(ScheduleDAO scheduleDAO) {
        this.scheduleDAO = scheduleDAO;
    }

    public long calculateTimeUntilEndOfMonth() {
        return calculateTimeUntilEndOfMonth(Clock.systemDefaultZone());
    }


    /**
     * Calculate time in hours until end of current <code>clock</code> month
     *
     * @param clock
     * @return hours until end of mounth
     */
    public long calculateTimeUntilEndOfMonth(Clock clock) {
        LocalDateTime to = LocalDateTime.now(clock).withHour(0).with(TemporalAdjusters.firstDayOfNextMonth());
        LocalDateTime from = LocalDateTime.now(clock);
        return from.until(to, ChronoUnit.HOURS);
    }

    /**
     * Set worked hours of all drivers to 0 in the end of month
     *
     * @return updated rows
     */
    @Scheduled(cron = "0 0 0 1 */1 *")
    public int refreshWorkedHours() {
        int rowsChanged = scheduleDAO.refreshWorkedHours();

        logger.info("Worked Hours success refreshed on {} rows", rowsChanged);

        return rowsChanged;
    }


}
