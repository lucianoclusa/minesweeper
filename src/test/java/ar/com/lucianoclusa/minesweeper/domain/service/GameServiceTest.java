package ar.com.lucianoclusa.minesweeper.domain.service;

import ar.com.lucianoclusa.minesweeper.application.model.MoveRequest;
import ar.com.lucianoclusa.minesweeper.application.model.MovementType;
import ar.com.lucianoclusa.minesweeper.domain.Board;
import ar.com.lucianoclusa.minesweeper.domain.Game;
import ar.com.lucianoclusa.minesweeper.domain.GameState;
import ar.com.lucianoclusa.minesweeper.domain.Slot;
import ar.com.lucianoclusa.minesweeper.domain.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsSecondArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class GameServiceTest {

    private GameService gameService;

    @Mock
    private GameRepository gameRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        gameService = new GameService(gameRepository);
    }

    @Test
    @DisplayName("Given a valid game When create game Then return saved game")
    void testCreateGame() {
        String userId = "asd-123";
        when(gameRepository.save(eq(userId), any())).then(returnsSecondArg());
        Game game = new Game(new Board(2, 2, 1));

        Game result = gameService.createGame("asd-123", game);

        assertEquals(game.getBoard().getNumberOfRows(), result.getBoard().getNumberOfRows());
        assertEquals(game.getBoard().getNumberOfColumns(), result.getBoard().getNumberOfColumns());
        assertEquals(game.getBoard().getNumberOfMines(), result.getBoard().getNumberOfMines());
    }

    @Test
    @DisplayName("Given a new slot When open slot Then return saved game with slot opened")
    void testMakeMoveOpenSlot() {
        String userId = "asd-123";
        MoveRequest move = new MoveRequest(1, 1, MovementType.OPEN);
        when(gameRepository.get(eq(userId), any())).thenReturn(new Game(new Board(2, 2, 1)));
        when(gameRepository.update(eq(userId), any())).then(returnsSecondArg());

        Game result = gameService.makeMove(move, "asd-123", "qwe-456");

        Slot slotOpened  = result.getBoard().getSlot(1, 1);
        assertTrue(slotOpened.isOpened());
        assertEquals(1, result.getMoves());
        assertEquals(GameState.IN_PROGRESS, result.getState());
    }

    @Test
    @DisplayName("Given a new slot When flag slot Then return saved game with slot flagged")
    void testMakeMoveFlagSlot() {
        String userId = "asd-123";
        MoveRequest move = new MoveRequest(1, 1, MovementType.FLAG);
        when(gameRepository.get(eq(userId), any())).thenReturn(new Game(new Board(2, 2, 1)));
        when(gameRepository.update(eq(userId), any())).then(returnsSecondArg());

        Game result = gameService.makeMove(move, "asd-123", "qwe-456");

        Slot slotFlagged  = result.getBoard().getSlot(1, 1);
        assertTrue(slotFlagged.isFlagged());
        assertFalse(slotFlagged.isOpened());
        assertEquals(0, result.getMoves());
        assertEquals(GameState.NOT_STARTED, result.getState());
    }

    @Test
    @DisplayName("Given a new slot When question slot Then return saved game with slot question")
    void testMakeMoveQuestionSlot() {
        String userId = "asd-123";
        MoveRequest move = new MoveRequest(1, 1, MovementType.QUESTION);
        when(gameRepository.get(eq(userId), any())).thenReturn(new Game(new Board(2, 2, 1)));
        when(gameRepository.update(eq(userId), any())).then(returnsSecondArg());

        Game result = gameService.makeMove(move, "asd-123", "qwe-456");

        Slot slotQuestioned  = result.getBoard().getSlot(1, 1);
        assertTrue(slotQuestioned.isQuestioned());
        assertFalse(slotQuestioned.isOpened());
        assertEquals(0, result.getMoves());
        assertEquals(GameState.NOT_STARTED, result.getState());
    }
}
