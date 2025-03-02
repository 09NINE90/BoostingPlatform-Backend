package ru.platform.games.mapper.impl;

import org.springframework.stereotype.Component;
import ru.platform.games.dao.GameEntity;
import ru.platform.games.dto.response.GameListRsDto;
import ru.platform.games.dto.response.GameMainPageRsDto;
import ru.platform.games.mapper.IGameMapper;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameMapper implements IGameMapper {

    @Override
    public GameListRsDto<GameMainPageRsDto> toGameListRs(List<GameEntity> allGames) {
        List<GameMainPageRsDto> games = new ArrayList<>();
        allGames.forEach(e -> {
            games.add(new GameMainPageRsDto(e.getId().toString(), e.getTitle()));
        });
        return new GameListRsDto<>(games);
    }
}
