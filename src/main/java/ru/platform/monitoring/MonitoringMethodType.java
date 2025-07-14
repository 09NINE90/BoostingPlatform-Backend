package ru.platform.monitoring;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MonitoringMethodType {
    AUTHORIZATION_USER(
            "authorization user",
            "Авторизация пользователя: /api/auth/login"
    ),
    REGISTRATION_USER(
            "registration user",
            "Создание пользователя: /api/auth/register"
    ),
    VERIFY_EMAIL(
            "verify email",
            "Подтверждение email при регистрации: /api/auth/verify-email"
    ),
    REFRESH_TOKEN(
            "refresh token",
            "Обновление токена доступа: /api/auth/refresh-token"
    ),
    PASSWORD_RESET_REQUEST(
            "password reset request",
            "Запрос восстановления пароля: /api/auth/password-reset/request"
    ),
    PASSWORD_RESET_VALIDATE(
            "password reset validate",
            "Подтверждение восстановления пароля: /api/auth/password-reset/validate"
    ),
    PASSWORD_CHANGE(
            "password change",
            "Смена пароля пользователя: /api/auth/password-change"
    ),
    SEND_REGISTRATION_MAIL(
            "send registration mail",
            "Отправка письма для подтверждения регистрации пользователя"
    ),
    SEND_RECOVERY_PASSWORD_MAIL(
            "send recovery password mail",
            "Отправка письма для восстановления пароля"
    ),
    ALL_GAMES(
            "all games",
            "Получить список всех игр, отсортированных по рейтингу: /api/games/getAllGames"
    ),
    CATEGORIES_BY_GAME_ID(
            "categories by game id",
            "Получение категорий игры по идентификатору игры: /api/games/getCategoriesByGameId/{gameId}"
    ),
    OFFERS_WITH_FILTERS(
            "offers with filters",
            "Получение предложений с сортировкой, фильтрами и пагинацией: /api/offer/getOffersByRequest"
    ),
    OFFERS_BY_GAME_ID(
            "offers by game id",
            "Получение предложений по идентификатору игры: /api/offer/getOffersByGameId/{gameId}"
    ),
    OFFER_OPTIONS(
            "offer option",
            "Получение опций предложения по его идентификатору: /api/offer/option/byOfferId/{offerId}"
    ),
    ADD_OFFER_TO_CART(
            "add offer to cart",
            "Добавление предложения в корзину"
    ),
    CREATE_ORDER(
            "create order",
            "Создание заказа из корзины"
    ),
    GET_DASHBOARD_FILTERS(
            "get dashboard filters",
            "Получение фильтров для дашборда бустера"
    ),
    GET_ORDERS_FILTERS_BY_BOOSTER(
            "get order filters by booster",
            "Получение фильтров для заказов во вкладке \"мои заказы\""
    ),
    GET_DASHBOARD_DATA(
            "get dashboard data",
            "Получение данных для дашборда бустера"
    ),
    GET_USER_ORDERS_BY_STATUS(
            "get user orders by status",
            "Получение заказов пользователя с фильтром по статусу"
    ),
    BOOSTER_ORDER_ACCEPT(
            "booster order accept",
            "Принятие заказа бустером в работу: /api/order/booster/accept/{orderId}"
    ),
    BOOSTER_ORDER_COMPLETE(
            "booster order complete",
            "Завершение выполнения заказа бустером: /api/order/booster/complete/{orderId}"
    ),
    BOOSTER_ORDER_HISTORY(
            "booster order history",
            "Получение истории заказов бустера: /api/order/booster/history"
    ),
    BOOSTER_FINANCE_WITHDRAWAL(
            "booster_finance_withdrawal",
            "Запрос на вывод средств бустера: /api/boosters/finance/withdrawals"
    ),
    BOOSTER_FINANCE_SEND_TIP(
            "booster finance send tip",
            "Отправка чаевых бустеру: /api/boosters/finance/tips"
    ),
    BOOSTER_FINANCE_BALANCE_HISTORY(
            "booster finance balance history",
            "Получение истории баланса бустера: /api/boosters/finance/balance/history"
    ),
    BOOSTER_FINANCE_ORDER_TIPS(
            "booster finance order tips",
            "Получение истории чаевых по заказу: /api/boosters/finance/orders/{orderId}/tips"
    ),
    BOOSTER_FINANCE_PROCESS_RECORDS(
            "booster finance process records",
            "Периодическая обработка финансовых записей бустеров (scheduled task)"
    ),
    BOOSTER_PROFILE_GET(
            "booster profile get",
            "Получение полного профиля бустера: /api/boosters/me/profile"
    ),
    BOOSTER_MINI_PROFILE_GET(
            "booster mini profile get",
            "Получение краткого профиля бустера: /api/boosters/{boosterId}/mini-profile"
    ),
    BOOSTER_BECOME_REQUEST(
            "booster become request",
            "Отправка заявки на становление бустером: /api/boosters/become/request"
    ),
    CUSTOMER_PROFILE_GET(
            "customer profile get",
            "Получение профиля заказчика: /api/customers/me/profile"
    ),
    USER_CHANGE_NICKNAME(
            "user change nickname",
            "Изменение никнейма пользователя: /api/users/me/nickname"
    ),
    USER_CHANGE_DESCRIPTION(
            "user change description",
            "Изменение описания профиля: /api/users/me/description"
    );

    private final String name;
    private final String description;

}