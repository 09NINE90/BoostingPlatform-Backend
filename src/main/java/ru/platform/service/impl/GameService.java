package ru.platform.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.platform.entity.GameEntity;
import ru.platform.repository.GameRepository;
import ru.platform.service.IGameService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService implements IGameService {

    private final GameRepository repository;


    @Override
    public List<GameEntity> getAllGames() {
        return repository.findAll();
    }
}
