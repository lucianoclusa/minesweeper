package ar.com.lucianoclusa.minesweeper.domain.service;

import ar.com.lucianoclusa.minesweeper.application.model.MoveRequest;
import ar.com.lucianoclusa.minesweeper.application.model.MovementType;
import ar.com.lucianoclusa.minesweeper.domain.Board;
import ar.com.lucianoclusa.minesweeper.domain.Game;
import ar.com.lucianoclusa.minesweeper.domain.GameState;
import ar.com.lucianoclusa.minesweeper.domain.Slot;
import ar.com.lucianoclusa.minesweeper.domain.User;
import ar.com.lucianoclusa.minesweeper.domain.repository.GameRepository;
import ar.com.lucianoclusa.minesweeper.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class GameServiceTest {

    private GameService gameService;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        gameService = new GameService(gameRepository, userRepository);
        when(gameRepository.save(any())).then(returnsFirstArg());
        when(gameRepository.update(any())).then(returnsFirstArg());
    }
    private String gameId = "qwe-456";
    private User user = new User("asd-123", "testUser", Collections.singletonList(gameId));

    @Test
    @DisplayName("Given a valid game When create game Then return saved game")
    void testCreateGame() {
        user.setUserGameIds(null);
        when(userRepository.getByUserName(any())).thenReturn(user);
        Game game = new Game(new Board(2, 2, 1));

        Game result = gameService.createGame("testUser", game);

        assertEquals(game.getBoard().getNumberOfRows(), result.getBoard().getNumberOfRows());
        assertEquals(game.getBoard().getNumberOfColumns(), result.getBoard().getNumberOfColumns());
        assertEquals(game.getBoard().getNumberOfMines(), result.getBoard().getNumberOfMines());
    }

    @Test
    @DisplayName("Given a new slot When open slot Then return saved game with slot opened")
    void testMakeMoveOpenSlot() {
        MoveRequest move = new MoveRequest(1, 1, MovementType.OPEN);
        mockRepositoryGetGame(gameId, new Game(new Board(2, 2, 0)));
        when(userRepository.getByUserName(any())).thenReturn(user);

        Game result = gameService.makeMove(move,  gameId);

        Slot slotOpened  = result.getBoard().getSlot(1, 1);
        assertTrue(slotOpened.isOpened());
        assertEquals(1, result.getMoves());
    }

    @Test
    @DisplayName("Given a new slot When flag slot Then return saved game with slot flagged")
    void testMakeMoveFlagSlot() {
        MoveRequest move = new MoveRequest(1, 1, MovementType.FLAG);
        mockRepositoryGetGame(gameId, new Game(new Board(2, 2, 1)));
        when(userRepository.getByUserName(any())).thenReturn(user);

        Game result = gameService.makeMove(move, gameId);

        Slot slotFlagged  = result.getBoard().getSlot(1, 1);
        assertTrue(slotFlagged.isFlagged());
        assertFalse(slotFlagged.isOpened());
        assertEquals(0, result.getMoves());
        assertEquals(GameState.NOT_STARTED, result.getState());
    }

    @Test
    @DisplayName("Given a new slot When question slot Then return saved game with slot question")
    void testMakeMoveQuestionSlot() {
        MoveRequest move = new MoveRequest(1, 1, MovementType.QUESTION);
        mockRepositoryGetGame(gameId, new Game(new Board(2, 2, 1)));
        when(userRepository.getByUserName(any())).thenReturn(user);

        Game result = gameService.makeMove(move, gameId);

        Slot slotQuestioned  = result.getBoard().getSlot(1, 1);
        assertTrue(slotQuestioned.isQuestioned());
        assertFalse(slotQuestioned.isOpened());
        assertEquals(0, result.getMoves());
        assertEquals(GameState.NOT_STARTED, result.getState());
    }

    @Test
    @DisplayName("Given a flagged slot When clean slot Then return saved game with slot closed")
    void testMakeMoveCleanSlot() {
        MoveRequest move = new MoveRequest(1, 1, MovementType.CLEAN);
        Game game = new Game(new Board(2, 2, 1));
        game.flagSlot(1, 1);
        mockRepositoryGetGame(gameId, game);
        when(userRepository.getByUserName(any())).thenReturn(user);

        Game result = gameService.makeMove(move, gameId);

        Slot slotQuestioned  = result.getBoard().getSlot(1, 1);
        assertTrue(slotQuestioned.isClosed());
        assertFalse(slotQuestioned.isFlagged());
        assertFalse(slotQuestioned.isOpened());
        assertEquals(0, result.getMoves());
        assertEquals(GameState.NOT_STARTED, result.getState());
    }

    @Test
    @DisplayName("Given a a game of testUser When anotherUser tries to play Then throw exception")
    void testOtherUserPlays() {
        MoveRequest move = new MoveRequest(1, 1, MovementType.QUESTION);
        mockRepositoryGetGame(gameId, new Game(new Board(2, 2, 1)));
        User otherUser = new User("asd-123", "testUser", Collections.singletonList(gameId));
        when(userRepository.getByUserName(user.getUserId())).thenReturn(otherUser);

        assertThrows(
                UserNotValidForGameException.class,
                () -> gameService.makeMove(move, gameId),
                "Code didn't throw IllegalArgumentException"
        );
    }

    private void mockRepositoryGetGame(String gameId, Game game) {
        when(gameRepository.get(gameId)).thenReturn(game);
    }
}
