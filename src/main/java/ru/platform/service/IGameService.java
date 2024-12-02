package ru.platform.service;

import ru.platform.entity.GameEntity;
import ru.platform.request.GameRequest;
import ru.platform.response.GameResponse;

import java.util.List;

public interface IGameService {
    List<GameEntity> getAllGames();

    void addNewGame(GameRequest request);
    GameResponse getGameWithCategories(String gameId);
}
