package ar.com.lucianoclusa.minesweeper.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MoveRequest {
    @JsonProperty
    private int row;
    @JsonProperty
    private int column;
    @JsonProperty("movement_type")
    private MovementType movementType;
    @JsonProperty("user_name")
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
