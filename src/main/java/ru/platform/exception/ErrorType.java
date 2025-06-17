package ru.platform.exception;

import lombok.Getter;

@Getter
public enum ErrorType {

    AUTHORIZATION_ERROR(
            401,
            "Authorization failed. Invalid credentials or token."
    ),
    ACCESS_DENIED_ERROR(
            403,
            "Access denied. You do not have permission to access this resource."
    ),
    EMAIL_VERIFIED_ERROR(
            400,
            "Email address has not been verified."
    ),
    EMAIL_SEND_ERROR(
            502,
            "Failed to send email. Please try again later."
    ),
    EMAIL_ALREADY_CONFIRMED_ERROR(
            409 ,
            "Email address is already confirmed."
    ),
    USER_EXISTS_ERROR(
            409,
            "User already exists."
    ),
    ORDER_ALREADY_IN_PROGRESS_ERROR(
            409,
            "The order is already in progress."
    ),
    DATE_CONVERSION_ERROR(
            400,
            "Invalid date format or failed date conversion."
    ),
    NOT_FOUND_ERROR(
            404,
            "Requested data not found."
    ),
    TOKEN_EXPIRED_ERROR(
            410,
            "Confirmation token has expired. Please request a new one."
    ),
    NOT_VALID_REQUEST(
            400,
            "Requested data is not valid."
    ),
    ORDER_LIMIT_EXCEEDED_ERROR(
            400,
            "You cannot take more orders. You have reached the maximum number of orders in progress."
    );

    private final int httpStatus;
    private final String message;

    ErrorType(int httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
