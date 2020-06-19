package ar.com.lucianoclusa.minesweeper.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static ar.com.lucianoclusa.minesweeper.domain.Board.MAX_COLUMN_SIZE;
import static ar.com.lucianoclusa.minesweeper.domain.Board.MAX_ROW_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {

    @Test
    @DisplayName("Given 0 rowNumber When creating a board Then throw exception with correct message")
    void testMinRowNumber() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Board(0, 10, 5),
                "Code didn't throw IllegalArgumentException"
        );
        assertTrue(thrown.getMessage().contains("Rows must be greater than 0"));
    }

    @Test
    @DisplayName("Given 0 columnNumber When creating a board Then throw exception with correct message")
    void testMinColumnNumber() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Board(10, 0, 5),
                "Code didn't throw IllegalArgumentException"
        );
        assertTrue(thrown.getMessage().contains("Columns must be greater than 0"));
    }

    @Test
    @DisplayName("Given " + (MAX_ROW_SIZE + 1) + " rowNumber When creating a board Then throw exception with correct message")
    void testMaxRowNumber() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Board(MAX_ROW_SIZE + 1, 10, 5),
                "Code didn't throw IllegalArgumentException"
        );
        assertEquals("Rows must be less or equal than " + MAX_ROW_SIZE, thrown.getMessage());
    }

    @Test
    @DisplayName("Given " + (MAX_COLUMN_SIZE + 1) + " rowNumber When creating a board Then throw exception with correct message")
    void testMaxColumnNumber() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Board(10, MAX_COLUMN_SIZE + 1, 5),
                "Code didn't throw IllegalArgumentException"
        );
        assertEquals("Columns must be less or equal than " + MAX_ROW_SIZE, thrown.getMessage());
    }

    @Test
    @DisplayName("Given mineCount bigger than board When creating a board Then throw exception with correct message")
    void testMaxMineCountNumber() {
        int columns = 10;
        int rows = 10;
        int mines = columns * rows + 1;

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Board(rows, columns, mines),
                "Code didn't throw IllegalArgumentException"
        );

        assertEquals("Number of mines must be less or equal than " + rows * columns, thrown.getMessage());
    }

    @Test
    @DisplayName("Given correct params When creating a board Then return created board")
    void testBoardCreation() {
        int columns = 10;
        int rows = 10;
        int mines = 10;

        Board board = new Board(rows, columns, mines);

        assertEquals(rows, board.getNumberOfRows());
        assertEquals(columns, board.getNumberOfColumns());
        assertEquals(mines, board.getNumberOfMines());
        System.out.print(board);
    }

    @Test
    @DisplayName("Given a new board When isOpened is called Then return false")
    void testBoardNotCleared() {
        int columns = 10;
        int rows = 10;
        int mines = 10;

        Board board = new Board(rows, columns, mines);

        assertFalse(board.isCleared());
    }

    @Test
    @DisplayName("Given a board with all non mined slots cleared When isOpened is called Then return true")
    void testNonMinedBoardCleared() {
        Board board = new Board(2, 2, 1);
        board.putMine(0,0);
        board.openSlot(board.getSlot(0,1));
        board.openSlot(board.getSlot(1,0));
        board.openSlot(board.getSlot(1,1));

        assertTrue(board.isCleared());
    }

    @Test
    @DisplayName("Given a board with non mined slots cleared and one mine not cleared When isOpened is called Then return true")
    void testMinedBoardCleared() {
        Board board = new Board(2, 2, 0);
        board.putMine(0,0);
        board.getSlots().stream().filter(slot -> !slot.isMined()).forEach(board::openSlot);

        assertTrue(board.isCleared());
    }

    @Test
    @DisplayName("Given a board with one non mined slots not cleared When isOpened is called Then return false")
    void testMinedBoardNotCleared() {
        Board board = new Board(2, 2, 0);
        board.putMine(0,0);
        board.putMine(0,1);
        board.openSlot(board.getSlot(1,0));


        assertFalse(board.isCleared());
    }

    @Test
    @DisplayName("Given a 2x2 board When geSlot at row and col 3 is called Then throw exception with correct message")
    void testGetOutOfRangeSlot() {
        Board board = new Board(2, 2, 1);

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> board.getSlot(2, 2),
                "Code didn't throw IllegalArgumentException"
        );
        assertEquals("Coordinates are out of range", thrown.getMessage());
    }

    @Test
    @DisplayName("Given a board full of mined slots When geSlot is called Then return a mined slot")
    void testGetMinedSlot() {
        Board board = new Board(2, 2, 4);

        Slot slot = board.getSlot(0, 0);

        assertFalse(slot.isOpened());
        assertFalse(slot.isFlagged());
        assertFalse(slot.isQuestioned());
        assertTrue(slot.isMined());
        assertEquals(0, slot.getColumn());
        assertEquals(0, slot.getRow());
    }

    @Test
    @DisplayName("Given a board with no mines When geSlot is called Then return a slot not mined")
    void testGetNotMinedSlot() {
        Board board = new Board(2, 2, 0);

        Slot slot = board.getSlot(1, 1);

        assertFalse(slot.isOpened());
        assertFalse(slot.isFlagged());
        assertFalse(slot.isQuestioned());
        assertFalse(slot.isMined());
        assertEquals(1, slot.getColumn());
        assertEquals(1, slot.getRow());
    }

    @Test
    @DisplayName("Given a new board when openAll is called Then all slots shuold  be opened")
    void testOpenAll() {
        Board board = new Board(2, 2, 1);

        board.revealAllSlots();

        assertTrue(board.getSlots().stream().allMatch(Slot::isOpened));
    }
}
