package ru.platform.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.platform.user.service.IAuthService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String LOG_PREFIX = "Exception in {} [line {}] — {}: {}";

    @ExceptionHandler(PlatformException.class)
    public ResponseEntity<Map<String, Object>> handleApiException(PlatformException ex) {
        ErrorType errorType = ex.getErrorType();

        StackTraceElement origin = ex.getStackTrace()[0];

        String className = origin.getClassName();
        int lineNumber = origin.getLineNumber();

        log.error(LOG_PREFIX, className, lineNumber, errorType.name(), errorType.getMessage());

        Map<String, Object> body = new HashMap<>();
        body.put("status", errorType.getHttpStatus());
        body.put("error", errorType.name());
        body.put("message", errorType.getMessage());
        body.put("timestamp", LocalDateTime.now());

        return ResponseEntity
                .status(errorType.getHttpStatus())
                .body(body);
    }

}

