package ar.com.lucianoclusa.minesweeper.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameRequest {
    @JsonProperty("number_of_rows")
    private int numberOfRows;
    @JsonProperty("number_of_columns")
    private int numberOfColumns;
    @JsonProperty("number_of_mines")
    private int numberOfMines;

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public void setNumberOfMines(int numberOfMines) {
        this.numberOfMines = numberOfMines;
    }
}
