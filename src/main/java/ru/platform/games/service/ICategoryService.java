package ru.platform.games.service;

import ru.platform.games.dto.response.CategoryRsDto;

import java.util.List;

/**
 * Сервис для работы с категориями предложений игр.
 */
public interface ICategoryService {

    /**
     * Возвращает список категорий, связанных с указанной игрой.
     */
    List<CategoryRsDto> getCategoriesByGameId(String gameId);
}
