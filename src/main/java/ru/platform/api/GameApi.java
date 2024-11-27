package ru.platform.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.platform.entity.GameEntity;
import ru.platform.service.IGameService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameApi {

    private final IGameService service;

    @GetMapping("/getAllGames")
    public List<GameEntity> getAllGames(){
        return service.getAllGames();
    }

    @PostMapping("/addNewGame")
    public ResponceEntity<?> addNewGame(@RequestBody GameEntity request){
        service.addNewGame(request);
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }
}
