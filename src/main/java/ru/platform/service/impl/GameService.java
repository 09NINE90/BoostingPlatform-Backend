package ru.platform.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.platform.entity.CategoryEntity;
import ru.platform.entity.GameEntity;
import ru.platform.repository.CategoryRepository;
import ru.platform.repository.GameRepository;
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
    public void addNewGame(GameEntity request) {
        request.getCategories().forEach(category -> processCategoryHierarchy(category, request));
        repository.save(request);
    }

    private void processCategoryHierarchy(CategoryEntity category, GameEntity game) {
        // Если категория не имеет родителя, это категория верхнего уровня
        if (category.getParent() == null) {
            category.setGame(game);
        }

        // Обрабатываем подкатегории
        if (category.getSubcategories() != null && !category.getSubcategories().isEmpty()) {
            category.getSubcategories().forEach(subcategory -> {
                subcategory.setParent(category); // Устанавливаем текущую категорию как родителя
                processCategoryHierarchy(subcategory, game); // Рекурсивно обрабатываем подкатегории
            });
        }
    }
    public GameResponse getGameWithCategories(String gameId) {
        // Находим игру
        GameEntity game = repository.findById(UUID.fromString(gameId))
                .orElseThrow(() -> new RuntimeException("Game not found"));

        // Преобразуем категории
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
