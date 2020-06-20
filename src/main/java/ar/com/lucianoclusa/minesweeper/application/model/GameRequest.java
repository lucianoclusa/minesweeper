package ar.com.lucianoclusa.minesweeper.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class GameRequest {
    @JsonProperty("number_of_rows")
    @Schema(example = "5")
    private int numberOfRows;
    @JsonProperty("number_of_columns")
    @Schema(example = "5")
    private int numberOfColumns;
    @JsonProperty("number_of_mines")
    @Schema(example = "10")
    private int numberOfMines;
    @JsonProperty("user_name")
    @Schema(example = "testUser")
    private String userName;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
