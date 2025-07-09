package ru.platform.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.platform.exception.PlatformException;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static ru.platform.exception.ErrorType.DATE_CONVERSION_ERROR;

@Slf4j
@Component
public class DateTimeUtils {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private final static DateTimeFormatter STRING_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final static String LOG_PREFIX = "DateUtil: {}. {}";

    public static LocalDateTime getDateTimeFromString(String date) {
        try {
            return LocalDateTime.parse(date, formatter);
        } catch (Exception e) {
            log.error(LOG_PREFIX, DATE_CONVERSION_ERROR, "Could not parse string to date");
            throw new PlatformException(DATE_CONVERSION_ERROR);
        }
    }

    public static String getStringFromDateTime(LocalDateTime dateTime) {
        try {
            return dateTime.format(formatter);
        } catch (Exception e) {
            log.error(LOG_PREFIX, DATE_CONVERSION_ERROR, "Could not parse date to string");
            throw new PlatformException(DATE_CONVERSION_ERROR);
        }
    }

    /**
     * Возвращает строку дату и время с UTC time zone
     */
    public static String offsetDateTimeToStringUTC(OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) return null;
        OffsetDateTime utcDateTime = offsetDateTime.withOffsetSameInstant(ZoneOffset.UTC);
        return utcDateTime.format(STRING_DATE_TIME_FORMAT);
    }

    /**
     * Возвращает дату и время в offsetDateTime с UTC time zone
     */
    public static OffsetDateTime offsetDateTimeUTC(OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) return null;
        return offsetDateTime.withOffsetSameInstant(ZoneOffset.UTC);
    }

    // Методы возвращают OffsetDateTime "текущее время минус указанный интервал"

    // Возвращает текущее время минус 1 минуту
    public static OffsetDateTime getRecordOlderThan1Minute() {
        return OffsetDateTime.now().minusMinutes(1);
    }

    // Возвращает текущее время минус 5 минут
    public static OffsetDateTime getRecordOlderThan5Minutes() {
        return OffsetDateTime.now().minusMinutes(5);
    }

    // Возвращает текущее время минус 10 минут
    public static OffsetDateTime getRecordOlderThan10Minutes() {
        return OffsetDateTime.now().minusMinutes(10);
    }

    // Возвращает текущее время минус 1 час
    public static OffsetDateTime getRecordOlderThan1Hour() {
        return OffsetDateTime.now().minusHours(1);
    }

    // Возвращает текущее время минус 1 день
    public static OffsetDateTime getRecordOlderThan1Day() {
        return OffsetDateTime.now().minusDays(1);
    }

    // Возвращает текущее время минус 2 недели
    public static OffsetDateTime getRecordOlderThan2Weeks() {
        return OffsetDateTime.now().minusWeeks(2);
    }
}
