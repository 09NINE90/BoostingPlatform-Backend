package ru.platform.games.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.platform.games.dto.response.CategoryRsDto;
import ru.platform.games.service.ICategoryService;

import java.util.List;

import static ru.platform.LocalConstants.Api.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/games")
@Tag(name = CATEGORY_TAG_NAME, description = CATEGORY_TAG_DESCRIPTION)
public class GameCategoryApi {

    private final ICategoryService service;

    @GetMapping("/{gameId}/categories")
    @Operation(summary = "Получение категорий игры по идентификатору игры")
    public ResponseEntity<List<CategoryRsDto>> getCategoriesByGameId(@PathVariable String gameId) {
        return ResponseEntity.ok(service.getCategoriesByGameId(gameId));
    }

}
