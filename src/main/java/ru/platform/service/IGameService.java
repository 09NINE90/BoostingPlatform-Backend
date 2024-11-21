package ru.platform.service;

import ru.platform.entity.GameEntity;

import java.util.List;

public interface IGameService {
    List<GameEntity> getAllGames();
}
