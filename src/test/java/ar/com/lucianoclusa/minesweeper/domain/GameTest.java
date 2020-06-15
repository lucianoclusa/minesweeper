package ar.com.lucianoclusa.minesweeper.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameTest {

    private User testUser = new User("1", "TestUser");
    @Test
    void testInitGame() {
        Game game = new Game(
                new Board(2,2, 1),
                testUser
        );

        assertEquals(GameState.NOT_STARTED, game.getState());
        game.initGame();

        assertEquals(0, game.getMoves());
        assertEquals(GameState.INIT, game.getState());
    }

    @Test
    @DisplayName("Given a mine-free board When open first slot Then open all slots and win the game")
    void testOpenNotMinedSlot() {
        Game game = new Game(
                new Board(2,2, 0),
                testUser
        );

        game.openSlot(0 ,0);

        assertEquals(GameState.WON, game.getState());
        assertEquals(1, game.getMoves());
        assertTrue(game.getBoard().getSlots().stream().allMatch(Slot::isCleared));
    }

    @Test
    @DisplayName("Given a board full of mines When open first slot Then loose the game")
    void testOpenMinedSlot() {
        Game game = new Game(
                new Board(2,2, 4),
                testUser
        );

        game.openSlot(0 ,0);

        assertEquals(GameState.LOST, game.getState());
        assertEquals(1, game.getMoves());
    }

    @Test
    @DisplayName("Given a slot with no mines around When open that slot Then open its neighbors")
    void testOpenSlotAndNeighbors() {
        Board board = new Board(3,3, 0);
        board.putMine(0, 2);
        board.putMine(2, 0);
        board.putMine(2, 2);

        Game game = new Game(board, testUser);

        game.openSlot(0 ,0);

        // Slot opened and its neighbors
        assertTrue(game.getBoard().getSlot(0, 0).isCleared());
        assertTrue(game.getBoard().getSlot(0, 1).isCleared());
        assertTrue(game.getBoard().getSlot(1, 0).isCleared());
        assertTrue(game.getBoard().getSlot(1, 1).isCleared());

        // Other slots with possible  mines
        assertFalse(game.getBoard().getSlot(0, 2).isCleared());
        assertFalse(game.getBoard().getSlot(1, 2).isCleared());
        assertFalse(game.getBoard().getSlot(2, 0).isCleared());
        assertFalse(game.getBoard().getSlot(2, 1).isCleared());
        assertFalse(game.getBoard().getSlot(2, 2).isCleared());
    }

    @Test
    @DisplayName("Given a slot with mines around When open that slot Then do not open its neighbors")
    void testOpenSlotButNotTheNeighbors() {
        Board board = new Board(3,3, 0);
        board.putMine(1, 1);
        board.putMine(2, 2);

        Game game = new Game(board, testUser);

        game.openSlot(0 ,0);

        //Slot opened
        assertTrue(game.getBoard().getSlot(0, 0).isCleared());

        //Other slots with possible  mines
        assertFalse(game.getBoard().getSlot(0, 1).isCleared());
        assertFalse(game.getBoard().getSlot(1, 0).isCleared());
        assertFalse(game.getBoard().getSlot(1, 1).isCleared());
        assertFalse(game.getBoard().getSlot(0, 2).isCleared());
        assertFalse(game.getBoard().getSlot(1, 2).isCleared());
        assertFalse(game.getBoard().getSlot(2, 0).isCleared());
        assertFalse(game.getBoard().getSlot(2, 1).isCleared());
        assertFalse(game.getBoard().getSlot(2, 2).isCleared());
    }
}
