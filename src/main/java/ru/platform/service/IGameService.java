package ru.platform.service;

import ru.platform.entity.GameEntity;
import ru.platform.request.BaseOrderRequest;

import java.util.List;

public interface IGameService {
    List<GameEntity> getAllGames();

    void addNewGame(GameEntity request);
}
