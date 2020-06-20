package ar.com.lucianoclusa.minesweeper.application;

import ar.com.lucianoclusa.minesweeper.application.model.GameResponse;
import ar.com.lucianoclusa.minesweeper.domain.service.GameService;
import ar.com.lucianoclusa.minesweeper.domain.Board;
import ar.com.lucianoclusa.minesweeper.domain.Game;
import ar.com.lucianoclusa.minesweeper.application.model.GameRequest;
import ar.com.lucianoclusa.minesweeper.application.model.MoveRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.Assert.notNull;

@RestController
public class GameController{

    private GameService gameManager;

    public GameController(GameService gameManager) {
        this.gameManager = gameManager;
    }

    @Operation(summary = "Create a new game")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameResponse> createGame(
            @RequestBody GameRequest request
    ) {
        Game game = new Game(new Board(request.getNumberOfRows(), request.getNumberOfColumns(), request.getNumberOfMines()));
        Game savedGame = gameManager.createGame(request.getUserName(), game);
        return new ResponseEntity<>(new GameResponse(savedGame), HttpStatus.OK);
    }

    @Operation(summary = "Make a move")
    @PostMapping("/{gameId}")
    public ResponseEntity<GameResponse> makeMove(
            @PathVariable String gameId,
            @RequestBody MoveRequest request
    ) {
        notNull(request.getMovementType(),"Not a valid movement_type (OPEN, FLAG or QUESTION).");
        Game game = gameManager.makeMove(request, gameId);
        return new ResponseEntity<>(new GameResponse(game), HttpStatus.OK);
    }

    @Operation(summary = "Create a new game")
    @GetMapping("/{gameId}")
    public ResponseEntity<GameResponse> getGame(
            @PathVariable String gameId
    ){
        Game game = gameManager.getGame(gameId);
        return new ResponseEntity<>(new GameResponse(game), HttpStatus.OK);
    }
}
