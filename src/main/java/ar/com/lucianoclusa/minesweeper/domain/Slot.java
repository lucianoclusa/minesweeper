package ar.com.lucianoclusa.minesweeper.domain;

public class Slot {

    private final int row;
    private final int column;

    private boolean isMined;
    private boolean isCleared;
    private boolean isFlagged;
    private boolean isQuestioned;


    Slot(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    boolean isMined() {
        return isMined;
    }

    void setMined() {
        isMined = true;
    }

    boolean isCleared() {
        return isCleared;
    }

    public void setCleared(boolean cleared) {
        isCleared = cleared;
    }

    boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public boolean isQuestioned() {
        return isQuestioned;
    }

    public void setQuestioned(boolean questioned) {
        isQuestioned = questioned;
    }

    boolean isNeighborOf(Slot anotherSlot) {
        return this != anotherSlot && Math.abs(this.column - anotherSlot.column) <= 1 && Math.abs(this.row - anotherSlot.row) <= 1;
    }
}
