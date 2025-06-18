package ru.platform.games.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.platform.exception.PlatformException;
import ru.platform.games.dto.response.GameBySecondIdRsDto;
import ru.platform.games.dto.response.GameMainPageRsDto;
import ru.platform.games.mapper.IGameMapper;
import ru.platform.games.dao.GameEntity;
import ru.platform.games.repository.GameRepository;
import ru.platform.games.service.IGameService;
import ru.platform.monitoring.MonitoringMethodType;
import ru.platform.monitoring.PlatformMonitoring;

import java.util.*;

import static ru.platform.exception.ErrorType.NOT_FOUND_ERROR;

@Service
@RequiredArgsConstructor
public class GameService implements IGameService {

    private final GameRepository repository;
    private final IGameMapper gameMapper;

    @Override
    @PlatformMonitoring(name = MonitoringMethodType.ALL_GAMES)
    public List<GameMainPageRsDto> getAllGames() {
        List<GameEntity> allGames = repository.findAllByIsActiveByOrderByRatingDesc();
        return gameMapper.toGameListRs(allGames);
    }

    @Override
    public GameBySecondIdRsDto getGameBySecondId(String secondId) {
        Optional<GameEntity> game = repository.findBySecondId(secondId);
        if (game.isEmpty()) throw new PlatformException(NOT_FOUND_ERROR);
        return gameMapper.toGameBySecondId(game.get());
    }

}
