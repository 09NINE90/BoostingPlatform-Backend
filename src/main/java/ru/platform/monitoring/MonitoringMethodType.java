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
    OFFERS_WITH_FILTERS(
            "offers with filters",
            "Получение предложений с сортировкой, фильтрами и пагинацией: /api/offer/getOffersByRequest"
    ),
    OFFERS_BY_GAME_ID(
            "offers by game id",
            "Получение предложений по идентификатору игры: /api/offer//getOffersByGameId/{gameId}"
    );

    private final String name;
    private final String description;

}