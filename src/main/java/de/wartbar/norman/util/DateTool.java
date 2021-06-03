package de.wartbar.norman.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateTool {

    /*
    format : "23 01 1997"
     */
    public static long getNumberOfDaysBetween(String inputString1, String inputString2) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");

        long daysBetween = 0;

        LocalDateTime date1 = LocalDateTime.parse(inputString1, dtf);
        LocalDateTime date2 = LocalDateTime.parse(inputString2, dtf);
        daysBetween = Duration.between(date1, date2).toDays();

        return daysBetween;
    }

    public static int getDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }
}
