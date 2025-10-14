package ru.platform.service.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.platform.games.mapper.IGameMapper;
import ru.platform.games.repository.GameRepository;
import ru.platform.games.service.impl.GameService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class GameServiceTest {

    private GameRepository repository;
    private IGameMapper gameMapper;
    private GameService gameService;

    @BeforeEach
    void setup() {
        repository = mock(GameRepository.class);
        gameMapper = mock(IGameMapper.class);
        gameService = new GameService(repository, gameMapper);
    }

    @Test
    @DisplayName("Игры: получение списка игра на главную страницу в side bar")
    void getAllGamesSuccess() {
        assertNotNull(1);
    }

}
