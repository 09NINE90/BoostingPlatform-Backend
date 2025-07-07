package ru.platform;

import java.math.BigDecimal;

public class LocalConstants {

    public static class Api {
        public static final String OFFER_TAG_NAME = "Предложения";
        public static final String OFFER_TAG_DESCRIPTION = "Получение предложений для клиентов";
        public static final String ORDER_TAG_NAME = "Заказы";
        public static final String ORDER_TAG_DESCRIPTION = "Работа с заказами";
        public static final String AUTH_TAG_NAME = "Пользователь";
        public static final String AUTH_TAG_DESCRIPTION = "Аутентификация, авторизация, подтверждение регистрации и восстеновление пароля";
        public static final String USER_TAG_NAME = "Пользователь";
        public static final String USER_TAG_DESCRIPTION = "Получение и обновление данных пароля";
        public static final String GAME_TAG_NAME = "Игры";
        public static final String GAME_TAG_DESCRIPTION = "Получение игр для клментов";
        public static final String CATEGORY_TAG_NAME = "Категории игр";
        public static final String CATEGORY_TAG_DESCRIPTION = "Получение категорий игр";
        public static final String CAROUSEL_TAG_NAME = "Карусель на главной странице";
        public static final String CAROUSEL_TAG_DESCRIPTION = "Получение объектов карусели";
    }

    public static class DateTimeConstants {
        // Минуты
        public static final int ONE_MINUTE = 1000 * 60;
        public static final int FIVE_MINUTES = ONE_MINUTE * 5;
        public static final int TEN_MINUTES = ONE_MINUTE * 10;
        public static final int THIRTY_MINUTES = ONE_MINUTE * 30;

        // Часы
        public static final int ONE_HOUR = 1000 * 60 * 60;
        public static final int THREE_HOURS = ONE_HOUR * 3;
        public static final int SIX_HOURS = ONE_HOUR * 6;
        public static final int TEN_HOURS = ONE_HOUR * 10;
        public static final int TWELVE_HOURS = ONE_HOUR * 12;

        // Сутки
        public static final int TWENTY_FOUR_HOURS = ONE_HOUR * 24;
    }

    public static class CustomerSettings {

        public static final int COUNT_ORDERS_FOR_VANGUARD_STATUS = 40;
        public static final int COUNT_ORDERS_FOR_IMMORTAL_STATUS = 100;

        public static final BigDecimal DISCOUNT_PERCENTAGE_FOR_EXPLORER_STATUS = BigDecimal.valueOf(0.01);
        public static final BigDecimal DISCOUNT_PERCENTAGE_FOR_VANGUARD_STATUS = BigDecimal.valueOf(0.05);
        public static final BigDecimal DISCOUNT_PERCENTAGE_FOR_IMMORTAL_STATUS = BigDecimal.valueOf(0.1);
    }

    public static class BoosterSettings {
        public static final double BOOSTER_ROOKIE_PERCENT = 0.45;
        public static final double BOOSTER_VETERAN_PERCENT = 0.5;
        public static final double BOOSTER_ELITE_PERCENT = 0.55;
        public static final double BOOSTER_LEGEND_PERCENT = 0.6;

        public static final int VETERAN_MIN_ORDERS = 10;
        public static final int ELITE_MIN_ORDERS = 50;
        public static final int LEGEND_MIN_ORDERS = 200;

        public static final BigDecimal BOOSTER_VETERAN_TOTAL_INCOME = BigDecimal.valueOf(500);
        public static final BigDecimal BOOSTER_ELITE_TOTAL_INCOME = BigDecimal.valueOf(2_000);
        public static final BigDecimal BOOSTER_LEGEND_TOTAL_INCOME = BigDecimal.valueOf(5_000);

        public static final int BOOSTER_LIMIT_ORDERS_IN_WORK = 3;
        public static final BigDecimal MINIMUM_WITHDRAWAL_AMOUNT = BigDecimal.valueOf(50);

    }

    public static class Variables {
        public static final int DEFAULT_PAGE_SIZE = 20;
        public static final int DEFAULT_PAGE_NUMBER = 0;

        public static final String EMPTY_STRING = "";

        public static final String DEFAULT_USER_MAIL = "user@mail.com";
        public static final String DEFAULT_USER_PASSWORD = "userPASSWORD123";
        public static final String DEFAULT_USER_ROLE = "ROLE_CUSTOMER";
        public static final String DEFAULT_USER_NICKNAME = "userNickname";
        public static final String DEFAULT_USER_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFt";
        public static final String DEFAULT_UUID = "70b82203-0f41-47e1-b11c-0fb96f8f9204";
        public static final String DEFAULT_SECOND_UUID = "ABCD-123";
        public static final String DEFAULT_IMAGE_LINK = "https://bb82cdcce70076216efdbdfb864e275d.jpg";

    }

    public static class Message {
        public static final String CONFIRMATION_CODE_MASSAGE = "Confirmation code sent to email: ";
        public static final String SUCCESS_PASSWORD_RECOVERY = "Success password recovery";
    }
}
