package ru.platform.service.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.platform.exception.ErrorType;
import ru.platform.exception.PlatformException;
import ru.platform.games.dto.response.GameBySecondIdRsDto;
import ru.platform.games.dto.response.GameMainPageRsDto;
import ru.platform.games.mapper.IGameMapper;
import ru.platform.games.repository.GameRepository;
import ru.platform.games.service.impl.GameService;
import ru.platform.service.CreatorDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    void getAllGamesSuccess() throws IOException {
        when(repository.findAllByIsActiveByOrderByRatingDesc()).thenReturn(CreatorDto.getListOfGamesEntity());
        when(gameMapper.toGameListRs(any())).thenReturn(CreatorDto.getGameMainPageRsDto());

        List<GameMainPageRsDto> response = gameService.getAllGames();

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(1, response.size()),
                () -> assertEquals("Legend of Eldoria", response.getFirst().getName()),
                () -> assertEquals("LoE", response.getFirst().getSecondId())
        );
    }

    @Test
    @DisplayName("Игры: получение игры по id - успех")
    void getGameBySecondIdSuccess() {
        String secondId = "LoE";

        when(repository.findBySecondId(secondId)).thenReturn(CreatorDto.getGameEntity());
        when(gameMapper.toGameBySecondId(any())).thenReturn(CreatorDto.getGameBySecondIdRsDto());

        GameBySecondIdRsDto response = gameService.getGameWithCategories(secondId);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(UUID.fromString("d26515bf-d06a-4218-8cf2-1a5866c38931"), response.getId()),
                () -> assertEquals("LoE", response.getSecondId())
        );
    }

    @Test
    @DisplayName("Игры: получение игры по id - игра не найдена")
    void getGameBySecondIdNotFoundGame() {
        String secondId = "LoE";

        when(repository.findBySecondId(secondId)).thenReturn(Optional.empty());

        PlatformException exception = assertThrows(PlatformException.class, () -> gameService.getGameWithCategories(secondId));

        assertEquals(ErrorType.NOT_FOUND_ERROR, exception.getErrorType());
    }
}
