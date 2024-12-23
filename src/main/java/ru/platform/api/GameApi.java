package ru.platform.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.platform.entity.GameEntity;
import ru.platform.request.GameRequest;
import ru.platform.response.GameResponse;
import ru.platform.service.IGameService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameApi {

    private final IGameService service;

    @PostMapping("/addNewGame")
    @Schema(description = "Добавление игры админом")
    public ResponseEntity<?> addNewGame(
            @RequestPart("file") MultipartFile file,
            @RequestPart("game") GameRequest request,
            Authentication authentication){
        return ResponseEntity.ok(service.addNewGame(request, file, authentication));
    }

    @PutMapping("/editGame")
    public ResponseEntity<?> editNewGame(@RequestBody GameRequest request) {
        try {
            GameEntity updatedGame = service.updateGame(request);
            return ResponseEntity.ok(updatedGame);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error: " + e.getMessage());
        }
    }

    @PostMapping("/getAllGamesByPage")
    public ResponseEntity<GameResponse> getAllGames(@RequestBody GameRequest request) {
        return ResponseEntity.ok(service.getAllGamesByPage(request));
    }

    @GetMapping("/getAllGames")
    public List<GameEntity> getAllGames() {
        return service.getAllGames();
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameResponse> getGameWithCategories(@PathVariable String gameId) {
        return ResponseEntity.ok(service.getGameWithCategories(gameId));
    }

}
