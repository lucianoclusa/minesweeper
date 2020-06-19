package ar.com.lucianoclusa.minesweeper.application;

import ar.com.lucianoclusa.minesweeper.application.model.GameResponse;
import ar.com.lucianoclusa.minesweeper.domain.service.GameService;
import ar.com.lucianoclusa.minesweeper.domain.Board;
import ar.com.lucianoclusa.minesweeper.domain.Game;
import ar.com.lucianoclusa.minesweeper.application.model.GameRequest;
import ar.com.lucianoclusa.minesweeper.application.model.MoveRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.Assert.notNull;

@RestController
public class GameController{

    private GameService gameManager;

    public GameController(GameService gameManager) {
        this.gameManager = gameManager;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameResponse> createGame(
            @RequestHeader("user-id") String userId,
            @RequestBody GameRequest request
    ) {
        Game game = new Game(new Board(request.getNumberOfRows(), request.getNumberOfColumns(), request.getNumberOfMines()));
        Game savedGame = gameManager.createGame(userId, game);
        return new ResponseEntity<>(new GameResponse(savedGame), HttpStatus.OK);
    }

    @PostMapping("/{gameId}")
    public ResponseEntity<GameResponse> makeMove(
            @RequestHeader("user-id") String userId,
            @PathVariable String gameId,
            @RequestBody MoveRequest request
    ){
        notNull(request.getMovementType(),"Not a valid movement_type (OPEN, FLAG or QUESTION).");
        Game game = gameManager.makeMove(request, userId, gameId);
        return new ResponseEntity<>(new GameResponse(game), HttpStatus.OK);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameResponse> getGame(
            @RequestHeader("user-id") String userId,
            @PathVariable String gameId
    ){
        Game game = gameManager.getGame(userId, gameId);
        return new ResponseEntity<>(new GameResponse(game), HttpStatus.OK);
    }
}
