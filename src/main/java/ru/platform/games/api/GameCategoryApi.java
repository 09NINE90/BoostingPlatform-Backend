package ru.platform.games.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.platform.games.dto.response.CategoryRsDto;
import ru.platform.games.service.ICategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/games")
public class GameCategoryApi {

    private final ICategoryService service;

    @GetMapping("/getCategoriesByGameId/{gameId}")
    public ResponseEntity<List<CategoryRsDto>> getCategoriesByGameId(@PathVariable String gameId) {
        return ResponseEntity.ok(service.getCategoriesByGameId(gameId));
    }

}
