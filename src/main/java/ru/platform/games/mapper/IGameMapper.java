package ru.platform.games.mapper;

import ru.platform.games.dao.GameEntity;
import ru.platform.games.dto.response.GameListRsDto;
import ru.platform.games.dto.response.GameMainPageRsDto;

import java.util.List;

public interface IGameMapper {
    GameListRsDto<GameMainPageRsDto> toGameListRs(List<GameEntity> allGames);
}
