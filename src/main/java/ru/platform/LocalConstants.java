package ru.platform;

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

    public static class Variables {
        public static final int DEFAULT_PAGE_SIZE = 20;
        public static final int DEFAULT_PAGE_NUMBER = 0;
        public static final int TEN_MINUTES = 1000 * 60 * 10;
        public static final int TEN_HOURS = 1000 * 60 * 60 * 10;
        public static final int TWENTY_FOUR_HOURS = 1000 * 60 * 60 * 24;
        public static final String EMPTY_STRING = "";

        public static final String DEFAULT_USER_MAIL = "user@mail.com";
        public static final String DEFAULT_USER_PASSWORD = "userPASSWORD123";
        public static final String DEFAULT_USER_ROLE = "ROLE_CUSTOMER";
        public static final String DEFAULT_USER_NICKNAME = "userNickname";
        public static final String DEFAULT_USER_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFt";
        public static final String DEFAULT_UUID = "70b82203-0f41-47e1-b11c-0fb96f8f9204";
        public static final String DEFAULT_SECOND_UUID = "ABCD-123";
        public static final String DEFAULT_IMAGE_LINK = "https://bb82cdcce70076216efdbdfb864e275d.jpg";

        public static final double BOOSTER_LEVEL_1_PERCENT = 0.5;
        public static final double BOOSTER_LEVEL_2_PERCENT = 0.55;
        public static final double BOOSTER_LEVEL_3_PERCENT = 0.6;

        public static final int BOOSTER_LIMIT_ORDERS_IN_WORK = 3;
    }

    public static class Message {
        public static final String CONFIRMATION_CODE_MASSAGE = "Confirmation code sent to email: ";
        public static final String SUCCESS_PASSWORD_RECOVERY = "Success password recovery";
    }
}
