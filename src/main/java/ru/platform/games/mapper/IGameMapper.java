package ru.platform.games.mapper;

import ru.platform.games.dao.GameEntity;
import ru.platform.games.dto.response.GameBySecondIdRsDto;
import ru.platform.games.dto.response.GameItemRsDto;
import ru.platform.games.dto.response.GameMainPageRsDto;

import java.util.List;

public interface IGameMapper {
    List<GameMainPageRsDto> toGameListRs(List<GameEntity> allGames);
    GameBySecondIdRsDto toGameBySecondId(GameEntity gameEntity);
    GameItemRsDto toGameItemRsDto(GameEntity entity);
}
