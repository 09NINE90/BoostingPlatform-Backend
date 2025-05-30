package ru.platform.exception;

import lombok.Getter;

@Getter
public class PlatformException extends RuntimeException {

    private final ErrorType errorType;

    public PlatformException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}   
