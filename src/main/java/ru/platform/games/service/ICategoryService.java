package ru.platform.games.service;

import ru.platform.games.dto.response.CategoryRsDto;

import java.util.List;

public interface ICategoryService {

    List<CategoryRsDto> getCategoriesByGameId(String gameId);
}
