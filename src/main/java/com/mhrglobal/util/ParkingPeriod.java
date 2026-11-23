package com.mhrglobal.util;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum ParkingPeriod {

    DAY,
    NIGHT,
    WEEKEND;

    public static ParkingPeriod getPeriod(LocalDateTime dateTime) {

        DayOfWeek day = dateTime.getDayOfWeek();

        // Saturday and Sunday are weekends
        if (day == DayOfWeek.SATURDAY ||
            day == DayOfWeek.SUNDAY) {

            return WEEKEND;
        }

        LocalTime time = dateTime.toLocalTime();

        // Daytime: 09:00 AM to 05:00 PM
        if (!time.isBefore(LocalTime.of(9, 0)) &&
             time.isBefore(LocalTime.of(17, 0))) {

            return DAY;
        }

        return NIGHT;
    }
}