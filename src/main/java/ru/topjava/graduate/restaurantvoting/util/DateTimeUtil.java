package ru.topjava.graduate.restaurantvoting.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;

@UtilityClass
public class DateTimeUtil {

    public static LocalDate getActualDateByDateTime(LocalDateTime localDateTime) {
        return localDateTime.getHour() < 11 ? localDateTime.toLocalDate().minusDays(1) : localDateTime.toLocalDate();
    }

    public static LocalDateTime checkDateTime(LocalDateTime localDateTime) {
        return localDateTime == null ? LocalDateTime.now() : localDateTime;
    }
}
