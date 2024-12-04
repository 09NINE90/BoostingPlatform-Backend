package ru.platform.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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

    @PutMapping("/editGame")
    public ResponseEntity<?> editNewGame(@RequestBody GameRequest request) {
        try {
            GameEntity updatedGame = service.updateGame(request);
            return ResponseEntity.ok(updatedGame);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error: " + e.getMessage());
        }
    }


    @PostMapping("/getAllGames")
    public ResponseEntity<GameResponse> getAllGames(@RequestBody GameRequest request){
        return ResponseEntity.ok(service.getAllGamesByPage(request));
    }

    @GetMapping("/getAddGameForm")
    public ModelAndView getMainPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("add-game-form");
        return modelAndView;
    }

    @GetMapping("/getEditGameForm/{gameId}")
    public ModelAndView getEditGameForm(@PathVariable String gameId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("edit-game-form");
        modelAndView.addObject(gameId);
        return modelAndView;
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
