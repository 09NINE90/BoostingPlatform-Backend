package ru.platform.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.platform.entity.CategoryEntity;
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

    @Override
    @Transactional
    public void addNewGame(GameEntity request) {
        request.getCategories().forEach(category -> processCategoryHierarchy(category, request));
        repository.save(request);
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
}
