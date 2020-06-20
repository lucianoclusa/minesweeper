package ar.com.lucianoclusa.minesweeper.application.model;

import ar.com.lucianoclusa.minesweeper.domain.Slot;
import io.swagger.v3.oas.annotations.media.Schema;

public class SlotResponse {
    @Schema(example = "'C'(closed) | 'F'(flagged) | '?'(questioned) | 'X'(exploded) | 'B'(bomb revealed)")
    private String value;
    @Schema(example = "0")
    private int row;
    @Schema(example = "1")
    private int column;

    public SlotResponse() {
        super();
    }

    SlotResponse(Slot slot) {
        this.row = slot.getRow();
        this.column = slot.getColumn();
        this.value = String.valueOf(slot.getState());
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
