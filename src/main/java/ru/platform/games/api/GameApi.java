package ru.platform.games.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.games.dto.response.GameListRsDto;
import ru.platform.games.dto.response.GameMainPageRsDto;
import ru.platform.games.service.IGameService;

import static ru.platform.LocalConstants.Api.GAME_TAG_DESCRIPTION;
import static ru.platform.LocalConstants.Api.GAME_TAG_NAME;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/games")
@Tag(name = GAME_TAG_NAME, description = GAME_TAG_DESCRIPTION)
public class GameApi {

    private final IGameService service;

    @GetMapping("/getAllGames")
    @Operation(summary = "Получить список всех игр, отсортированных по рейтингу")
    public ResponseEntity<GameListRsDto<GameMainPageRsDto>> getAllGames() {
        return ResponseEntity.ok(service.getAllGames());
    }

}
