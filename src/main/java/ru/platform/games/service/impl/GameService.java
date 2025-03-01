package ru.platform.games.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.platform.games.dto.response.GameListRsDto;
import ru.platform.games.dto.response.GameMainPageRsDto;
import ru.platform.games.mapper.IGameMapper;
import ru.platform.games.dao.GameEntity;
import ru.platform.games.repository.GameRepository;
import ru.platform.games.service.IGameService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GameService implements IGameService {

    private final GameRepository repository;
    private final IGameMapper gameMapper;

    @Override
    public GameListRsDto<GameMainPageRsDto> getAllGames() {
        List<GameEntity> allGames = repository.findAllByOrderByRatingDesc();
        return gameMapper.toGameListRs(allGames);
    }

}
