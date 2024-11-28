package ru.platform.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.entity.GameEntity;
import ru.platform.response.GameResponse;
import ru.platform.service.IGameService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameApi {

    private final IGameService service;

    @GetMapping("/getAllGames")
    public List<GameEntity> getAllGames(){
        List<GameEntity> games = service.getAllGames();
        try {
            System.out.println(new ObjectMapper().writeValueAsString(games));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return service.getAllGames();
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameResponse> getGameWithCategories(@PathVariable String gameId) {
        return ResponseEntity.ok(service.getGameWithCategories(gameId));
    }


    @PostMapping("/addNewGame")
    public void addNewGame(@RequestBody GameEntity request){
        service.addNewGame(request);
    }
}
