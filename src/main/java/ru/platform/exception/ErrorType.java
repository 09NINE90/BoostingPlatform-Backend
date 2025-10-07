package ru.platform.exception;

import lombok.Getter;

@Getter
public enum ErrorType {

    // Аутентификация и авторизация
    AUTHORIZATION_ERROR(
            401,
            "Authorization failed. Invalid credentials or token."
    ),
    ACCESS_DENIED_ERROR(
            403,
            "Access denied. You do not have permission to access this resource."
    ),

    // Работа с email
    EMAIL_VERIFIED_ERROR(
            400,
            "Email address has not been verified."
    ),
    EMAIL_SEND_ERROR(
            502,
            "Failed to send email. Please try again later."
    ),
    EMAIL_ALREADY_CONFIRMED_ERROR(
            409,
            "Email address is already confirmed."
    ),
    TOKEN_EXPIRED_ERROR(
            410,
            "Confirmation token has expired. Please request a new one."
    ),

    // Работа с пользователями
    USER_EXISTS_ERROR(
            409,
            "User already exists."
    ),
    USER_REFERRER_NOT_EXISTS_ERROR(
            409,
            "Referrer not exists."
    ),
    USER_REFERRED_ERROR(
            409,
            "User already referred."
    ),
    USER_REFERRED_YOURSELF_ERROR(
            409,
            "Cannot refer yourself."
    ),

    // Работа с заказами (Orders)
    ORDER_ALREADY_IN_PROGRESS_ERROR(
            409,
            "The order is already in progress."
    ),
    ORDER_LIMIT_EXCEEDED_ERROR(
            400,
            "You cannot take more orders. You have reached the maximum number of orders in progress."
    ),
    INVALID_ORDER_STATUS_FOR_COMPLETION_ERROR(
            400,
            "Order cannot be completed because it's not in IN_PROGRESS status."
    ),
    ORDER_SESSION_IS_ALREADY_ACTIVE(
            400,
            "Order session is already active."
    ),
    ORDER_SESSION_NOT_ACTIVE(
            400,
            "Session is not active."
    ),
    // Финансы / Транзакции
    ZERO_AMOUNT_ERROR(
            400,
            "Amount cannot be zero."
    ),
    WITHDRAWAL_AMOUNT_LESS_THEN_MINIMUM_ERROR(
            400,
            "The entered amount is less than the minimum amount."
    ),
    WITHDRAWAL_AMOUNT_MORE_THEN_BALANCE_ERROR(
            400,
            "The amount entered is more than your balance."
    ),

    // Общие ошибки запроса/данных
    NOT_VALID_REQUEST(
            400,
            "Requested data is not valid."
    ),
    DATE_CONVERSION_ERROR(
            400,
            "Invalid date format or failed date conversion."
    ),
    MISSING_REQUIRED_FIELDS_ERROR(
            400,
            "Required fields are missing."
    ),
    JSON_LOAD_ERROR(
            400,
            "Failed to load object from JSON."
    ),
    NOT_FOUND_ERROR(
            404,
            "Requested data not found."
    ),
    NO_GAME_TAGS_ERROR(
            400,
            "There are no game tags."
    );

    private final int httpStatus;
    private final String message;

    ErrorType(int httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
