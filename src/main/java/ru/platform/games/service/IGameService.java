package ru.platform.games.service;

import ru.platform.games.dto.response.GameBySecondIdRsDto;
import ru.platform.games.dto.response.GameMainPageRsDto;

import java.util.List;

public interface IGameService {

    List<GameMainPageRsDto> getAllGames();
    GameBySecondIdRsDto getGameBySecondId(String secondId);
}
