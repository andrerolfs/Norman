package de.wartbar.norman.util;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTool {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
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

    public static boolean equalsDate(Date date1, Date date2) {
        return sdf.format(date1).equals(sdf.format(date2));
    }
}
