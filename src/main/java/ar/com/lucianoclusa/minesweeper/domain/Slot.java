package ar.com.lucianoclusa.minesweeper.domain;

import java.util.HashMap;
import java.util.Map;

public class Slot {

    private final int row;
    private final int column;
    private boolean isMined;
    private String state;

    public Slot(int column, int row) {
        this.row = row;
        this.column = column;
        this.state = SlotState.CLOSED.value;
    }

    boolean isNeighborOf(Slot anotherSlot) {
        return this != anotherSlot && Math.abs(this.column - anotherSlot.column) <= 1 && Math.abs(this.row - anotherSlot.row) <= 1;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isFlagged() {
        return SlotState.FLAGGED.value.equals(this.state);
    }

    public boolean isQuestioned() {
        return SlotState.QUESTIONED.value.equals(this.state);
    }

    public boolean isClosed() {
        return SlotState.CLOSED.value.equals(this.state);
    }
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isMined() {
        return isMined;
    }

    public void setMined(boolean isMined) {
        this.isMined = isMined;
    }

    public boolean isOpened() {
        return !isFlagged() && !isQuestioned() && !isClosed();
    }

    public String getState() {
        return state;
    }

    public enum SlotState {
        CLOSED("C"),
        FLAGGED("F"),
        QUESTIONED("?"),
        EXPLODED("X"),
        BOMB_REVEALED("B");

        private final String value;

        SlotState(String value) {
            this.value = value;
        }

        // Reverse-lookup map for getting a day from an abbreviation
        private static final Map<String, SlotState> lookup = new HashMap<>();

        static {
            for (SlotState state : SlotState.values()) {
                lookup.put(state.value, state);
            }
        }

        public static SlotState get(String value) {
            return lookup.get(value);
        }

        public String getValue() {
            return value;
        }
    }
}
