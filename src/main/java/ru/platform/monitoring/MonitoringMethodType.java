package ru.platform.monitoring;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MonitoringMethodType {
    AUTHORIZATION_USER(
            "authorization user",
            "Авторизация пользователя: /api/auth/signIn"
    ),
    CREATION_USER(
            "creation user",
            "Создание пользователя: /api/auth/signUp"
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
    );

    private final String name;
    private final String description;

}