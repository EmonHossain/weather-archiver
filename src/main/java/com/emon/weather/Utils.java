package com.emon.weather;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@Slf4j
@Component
public class Utils {
    //convert specific date to milliseconds
    public long formatDateStringToMilli(String val){
        long timeInMilli = 0;
        try {
            DateTimeFormatter sourceFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
            LocalDateTime ldt = LocalDateTime.parse(val,sourceFormat);
            Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
            timeInMilli = instant.toEpochMilli();
        }catch (DateTimeParseException pe){
            log.error(pe.getMessage(),pe);
        }
        return timeInMilli;
    }

    //calculate previous time
    public long getTimeBeforeHoursFromNowInMillis(long hour){
        long currentTime = System.currentTimeMillis();
        long timeRange = hour * 60 * 60 * 1000L;
        long timeDuration = currentTime-timeRange;
        return timeDuration;
    }

    //convert milliseconds to date
    public String millisToDateTime(long millis){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        Date d = new Date(millis);
        return df.format(d);
    }
}
