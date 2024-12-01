package ru.platform.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.platform.entity.CategoryEntity;
import ru.platform.entity.GameEntity;
import ru.platform.repository.CategoryRepository;
import ru.platform.repository.GameRepository;
import ru.platform.request.GameRequest;
import ru.platform.response.GameResponse;
import ru.platform.service.IGameService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService implements IGameService {

    private final GameRepository repository;
    private final CategoryRepository repositoryCategories;

    @Override
    public List<GameEntity> getAllGames() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void addNewGame(GameRequest request) {
        GameEntity gameEntity = GameEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .categories(new ArrayList<>()) // Пустой список для избежания ошибок
                .build();

        repository.save(gameEntity);

        request.getCategories().forEach(category -> processCategoryHierarchy(category, gameEntity));
        gameEntity.setCategories(request.getCategories());

        repository.save(gameEntity);
    }


    private void processCategoryHierarchy(CategoryEntity category, GameEntity game) {
        category.setGame(game);

        if (category.getSubcategories() != null && !category.getSubcategories().isEmpty()) {
            category.getSubcategories().forEach(subcategory -> {
                subcategory.setParent(category);
                processCategoryHierarchy(subcategory, game);
            });
        }
    }
    public GameResponse getGameWithCategories(String gameId) {
        GameEntity game = repository.findById(UUID.fromString(gameId))
                .orElseThrow(() -> new RuntimeException("Game not found"));

        List<GameResponse.CategoryDTO> categories = game.getCategories()
                .stream()
                .map(this::toCategoryDTO)
                .collect(Collectors.toList());

        // Создаем DTO
        return new GameResponse(
                game.getId().toString(),
                game.getTitle(),
                game.getDescription(),
                categories
        );
    }

    private GameResponse.CategoryDTO toCategoryDTO(CategoryEntity category) {
        return new GameResponse.CategoryDTO(
                category.getName(),
                category.getSubcategories().stream()
                        .map(this::toCategoryDTO)
                        .collect(Collectors.toList())
        );
    }

}
