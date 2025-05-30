package ru.platform.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.platform.exception.PlatformException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ru.platform.exception.ErrorType.DATE_CONVERSION_ERROR;

@Slf4j
@Component
public class DateUtil {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
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
}
