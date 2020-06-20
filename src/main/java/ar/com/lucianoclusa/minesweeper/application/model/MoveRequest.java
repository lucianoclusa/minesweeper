package ar.com.lucianoclusa.minesweeper.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class MoveRequest {
    @JsonProperty
    @Schema(example = "0")
    private int row;
    @JsonProperty
    @Schema(example = "1")
    private int column;
    @JsonProperty("movement_type")
    @Schema(example = "OPEN | FLAG | QUESTION | CLEAR")
    private MovementType movementType;
    @JsonProperty("user_name")
    @Schema(example = "testUser")
    private String userName;

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public MoveRequest(int row, int column, MovementType movementType) {
        this.row = row;
        this.column = column;
        this.movementType = movementType;
    }

    public MoveRequest() {
        super();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
