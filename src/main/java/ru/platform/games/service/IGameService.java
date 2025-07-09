package ru.platform.games.service;

import ru.platform.games.dto.response.GameBySecondIdRsDto;
import ru.platform.games.dto.response.GameMainPageRsDto;

import java.util.List;

/**
 * Сервис для работы с игровыми данными.
 */
public interface IGameService {

    /**
     * Возвращает список всех игр для отображения на главной странице.
     */
    List<GameMainPageRsDto> getAllGames();

    /**
     * Возвращает информацию об игре и её категориях по вторичному идентификатору.
     */
    GameBySecondIdRsDto getGameWithCategories(String secondId);
}

