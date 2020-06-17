package ar.com.lucianoclusa.minesweeper.domain;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Board {

    public static final int MAX_COLUMN_SIZE = 1000;
    public static final int MAX_ROW_SIZE = 1000;

    private final int numberOfRows;
    private final int numberOfColumns;
    private int numberOfMines;
    private List<Slot> slots;

    /**
     * Board constructor for a new game
     * @param numberOfRows number of rows the board has
     * @param numberOfColumns number of columns the board has
     * @param numberOfMines number of mines to be randomly set on board
     */
    public Board(int numberOfRows, int numberOfColumns, int numberOfMines) {
        validateBoardSize(numberOfRows, numberOfColumns);
        this.numberOfRows  = numberOfRows;
        this.numberOfColumns  = numberOfColumns;
        this.numberOfMines  = numberOfMines;

        int boardSize = numberOfColumns * numberOfRows;
        Assert.isTrue(numberOfMines <= boardSize, "Number of mines must be less or equal than " + boardSize);
        createEmptyBoard();
        setRandomMines();
    }

    private void validateBoardSize(int numberOfRows, int numberOfColumns) {
        Assert.isTrue(numberOfRows > 0, "Rows must be greater than 0");
        Assert.isTrue(numberOfColumns > 0, "Columns must be greater than 0");
        Assert.isTrue(numberOfRows <= MAX_ROW_SIZE, "Rows must be less or equal than " + MAX_ROW_SIZE);
        Assert.isTrue(numberOfColumns <= MAX_COLUMN_SIZE, "Columns must be less or equal than " + MAX_COLUMN_SIZE);
    }

    void putMine(int row, int col) {
        Slot slot = getSlot(row, col);
        if(!slot.isMined()) {
            slot.setMined();
            numberOfMines++;
        }
    }

    public Slot getSlot(int row, int col) {
        Assert.isTrue(isValidCoordinates(row, col), "Coordinates are out of range");
        return this.slots.get(row * this.numberOfColumns + col);
    }

    private void createEmptyBoard() {
        slots = new ArrayList<>();
        for (int row=0; row<numberOfRows; row++) {
            for (int column=0; column<numberOfColumns; column++) {
                slots.add(new Slot(row, column));
            }
        }
    }

    private void setRandomMines() {
        int minesSetted = 0;
        while(minesSetted < numberOfMines) {
            int randomIndex = (int) (Math.random() * numberOfMines);
            if(!slots.get(randomIndex).isMined()) {
                slots.get(randomIndex).setMined();
                minesSetted++;
            }
        }
        slots.stream().limit(numberOfMines).forEach(Slot::setMined);

    }

    private boolean isValidCoordinates(int row, int col) {
        return row >= 0 && col >= 0 && row < this.numberOfRows && col < this.numberOfColumns;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    boolean isCleared() {
        return slots.stream().noneMatch((slot -> !slot.isOpened() && !slot.isMined()));
    }

    List<Slot> getNeighbors(Slot slot) {
        return slots.stream().filter((otherSlot -> otherSlot.isNeighborOf(slot))).collect(Collectors.toList());
    }

    public List<Slot> getSlots() {
        return slots;
    }

    void openAllSlots() {
        slots.forEach(Slot::open);
    }
}
