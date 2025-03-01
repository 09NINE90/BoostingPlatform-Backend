package ru.platform.games.service;

import ru.platform.games.dto.response.GameListRsDto;
import ru.platform.games.dto.response.GameMainPageRsDto;

public interface IGameService {

    GameListRsDto<GameMainPageRsDto> getAllGames();

}
