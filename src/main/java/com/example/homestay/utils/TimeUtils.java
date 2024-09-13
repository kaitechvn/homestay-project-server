package com.example.homestay.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

    // General method to get the current timestamp in any ZoneId
    public static String getTimestamp() {
        return Instant.now()
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ISO_DATE_TIME);
    }

    // JWT time
    public static Instant getIssuedAt() {
        return Instant.ofEpochSecond(System.currentTimeMillis()/1000);
    }

    public static Instant getExpiredAt(long expTime) {
        return Instant.ofEpochSecond(System.currentTimeMillis()/1000 + expTime) ;
    }

}
