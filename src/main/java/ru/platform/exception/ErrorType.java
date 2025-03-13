package ru.platform.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

    UNAUTHORIZED_ERROR(
            "Failed authorization attempt"
    ),
    EMAIL_VERIFIED_ERROR(
            "Email has not been verified"
    ),
    NOT_FOUND_ERROR(
            "Data could not be found in the database table"
    );

    private final String description;
}
