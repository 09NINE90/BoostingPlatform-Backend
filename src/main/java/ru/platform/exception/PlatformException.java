package ru.platform.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PlatformException extends RuntimeException {

    private final ErrorType errorType;
    private final HttpStatus httpStatus;

    public PlatformException(ErrorType errorType) {
        this(errorType, HttpStatus.OK, null);
    }

    public PlatformException(ErrorType errorType, Throwable cause) {
        this(errorType, HttpStatus.OK, cause);
    }

    public PlatformException(ErrorType errorType, HttpStatus httpStatus) {
        this(errorType, httpStatus, null);
    }

    public PlatformException(ErrorType errorType, HttpStatus httpStatus, Throwable cause) {
        super(errorType.getDescription(), cause);
        this.errorType = errorType;
        this.httpStatus = httpStatus;
    }
}   
