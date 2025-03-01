package ru.platform.games.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.games.dto.response.GameListRsDto;
import ru.platform.games.dto.response.GameMainPageRsDto;
import ru.platform.games.service.IGameService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/games")
public class GameApi {

    private final IGameService service;

    @GetMapping("/getAllGames")
    @Schema(
            description = "Получть список всех игр, отсортированных по рейтингу"
    )
    public ResponseEntity<GameListRsDto<GameMainPageRsDto>> getAllGames() {
        return ResponseEntity.ok(service.getAllGames());
    }

}
