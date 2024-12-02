package ru.platform.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public void addNewGame(@RequestBody GameRequest request){
        service.addNewGame(request);
    }

    @PostMapping("/getAllGames")
    public ResponseEntity<GameResponse> getAllGames(@RequestBody GameRequest request){
        return ResponseEntity.ok(service.getAllGamesByPage(request));
    }

    @GetMapping("/getAllGames")
    public List<GameEntity> getAllGames(){
        return service.getAllGames();
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameResponse> getGameWithCategories(@PathVariable String gameId) {
        return ResponseEntity.ok(service.getGameWithCategories(gameId));
    }

}
