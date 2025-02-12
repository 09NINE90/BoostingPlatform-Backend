package ru.platform.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.platform.entity.GameEntity;
import ru.platform.request.GameRequest;
import ru.platform.response.GameResponse;

import java.util.List;

public interface IGameService {
    List<GameEntity> getAllGames();
    GameEntity addNewGame(GameRequest request, MultipartFile imageFile, Authentication authentication);
    GameResponse getGameWithCategories(String gameId);
    GameResponse getAllGamesByPage(GameRequest request);
    GameEntity updateGame(GameRequest request) throws Exception;
}
