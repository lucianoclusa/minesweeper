package ar.com.lucianoclusa.minesweeper.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameTest {

    @Test
    void testInitGame() {
        Game game = new Game(
                new Board(2,2, 1));

        assertEquals(GameState.NOT_STARTED, game.getState());
        game.initGame();

        assertEquals(0, game.getMoves());
        assertEquals(GameState.IN_PROGRESS, game.getState());
    }

    @Test
    @DisplayName("Given a mine-free board When open first slot Then open all slots and win the game")
    void testOpenNotMinedSlot() {
        Game game = new Game(new Board(2,2, 0));

        game.openSlot(0 ,0);

        assertEquals(GameState.WON, game.getState());
        assertEquals(1, game.getMoves());
        assertTrue(game.getBoard().getSlots().stream().allMatch(Slot::isOpened));
    }

    @Test
    @DisplayName("Given a board full of mines When open first slot Then loose the game")
    void testOpenMinedSlot() {
        Game game = new Game(
                new Board(2,2, 4));

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

        Game game = new Game(board);

        game.openSlot(0 ,0);

        // Slot opened and its neighbors
        assertTrue(game.getBoard().getSlot(0, 0).isOpened());
        assertTrue(game.getBoard().getSlot(0, 1).isOpened());
        assertTrue(game.getBoard().getSlot(1, 0).isOpened());
        assertTrue(game.getBoard().getSlot(1, 1).isOpened());

        // Other slots with possible  mines
        assertFalse(game.getBoard().getSlot(0, 2).isOpened());
        assertFalse(game.getBoard().getSlot(1, 2).isOpened());
        assertFalse(game.getBoard().getSlot(2, 0).isOpened());
        assertFalse(game.getBoard().getSlot(2, 1).isOpened());
        assertFalse(game.getBoard().getSlot(2, 2).isOpened());
    }

    @Test
    @DisplayName("Given a slot with mines around When open that slot Then do not open its neighbors")
    void testOpenSlotButNotTheNeighbors() {
        Board board = new Board(3,3, 0);
        board.putMine(1, 1);
        board.putMine(2, 2);

        Game game = new Game(board);

        game.openSlot(0 ,0);

        //Slot opened
        assertTrue(game.getBoard().getSlot(0, 0).isOpened());

        //Other slots with possible  mines
        assertFalse(game.getBoard().getSlot(0, 1).isOpened());
        assertFalse(game.getBoard().getSlot(1, 0).isOpened());
        assertFalse(game.getBoard().getSlot(1, 1).isOpened());
        assertFalse(game.getBoard().getSlot(0, 2).isOpened());
        assertFalse(game.getBoard().getSlot(1, 2).isOpened());
        assertFalse(game.getBoard().getSlot(2, 0).isOpened());
        assertFalse(game.getBoard().getSlot(2, 1).isOpened());
        assertFalse(game.getBoard().getSlot(2, 2).isOpened());
    }

    @Test
    @DisplayName("Given a game WON When open a slot Then throw exception")
    void testOpenSlotOnWonGame() {
        Game game = new Game(new Board(2,2, 2));
        game.setState(GameState.WON);

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> game.openSlot(1 ,1),
                "Code didn't throw IllegalArgumentException"
        );
        assertTrue(thrown.getMessage().contains("Game already WON"));
    }

    @Test
    @DisplayName("Given a game LOST When open a slot Then throw exception")
    void testOpenSlotOnLostGame() {
        Game game = new Game(new Board(2,2, 2));
        game.setState(GameState.LOST);

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> game.openSlot(1 ,1),
                "Code didn't throw IllegalArgumentException"
        );
        assertTrue(thrown.getMessage().contains("Game already LOST"));
    }

    @Test
    @DisplayName("Given a flagged slot When it is cleaned Then un-flag that slot")
    void testCleanFlaggedSlot() {
        Game game = new Game(new Board(2,2, 2));
        game.flagSlot(1,1);

        game.cleanSlot(1 ,1);

        assertTrue(game.getBoard().getSlot(1, 1).isClosed());
        assertFalse(game.getBoard().getSlot(1, 1).isFlagged());
    }

    @Test
    @DisplayName("Given a questioned slot When it is cleaned Then un-question that slot")
    void testCleanQuestionedSlot() {
        Game game = new Game(new Board(2,2, 2));
        game.questionSlot(1,1);

        game.cleanSlot(1 ,1);

        assertTrue(game.getBoard().getSlot(1, 1).isClosed());
        assertFalse(game.getBoard().getSlot(1, 1).isFlagged());
    }

    @Test
    @DisplayName("Given a new closed slot When it is cleaned Then throw exception")
    void testCleanCleanedSlot() {
        Game game = new Game(new Board(2,2, 2));

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> game.cleanSlot(1 ,1),
                "Code didn't throw IllegalArgumentException"
        );
        assertTrue(thrown.getMessage().contains("Can only clear flagged or questioned slots"));
    }
}
