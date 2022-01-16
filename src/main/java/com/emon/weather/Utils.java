package com.emon.weather;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@Component
public class Utils {
    public long formatDateStringToMilli(String val){
        long timeInMilli = 0;
        try {
            DateTimeFormatter sourceFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
            LocalDateTime ldt = LocalDateTime.parse(val,sourceFormat);
            Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
            timeInMilli = instant.toEpochMilli();
            System.out.println(timeInMilli);
        }catch (DateTimeParseException pe){
            pe.printStackTrace();
        }
        return timeInMilli;
    }

    public long getTimeBeforeHoursFromNowInMillis(long hour){
        long currentTime = System.currentTimeMillis();
        long timeRange = hour * 60 * 60 * 1000L;
        long timeDuration = currentTime-timeRange;
        return timeDuration;
    }

    public String millisToDateTime(long millis){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        Date d = new Date(millis);
        return df.format(d);
    }
}
