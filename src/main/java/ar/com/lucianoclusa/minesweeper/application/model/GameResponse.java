package ar.com.lucianoclusa.minesweeper.application.model;

import ar.com.lucianoclusa.minesweeper.domain.Game;
import ar.com.lucianoclusa.minesweeper.domain.Slot;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameResponse {
    @JsonProperty
    private String state;
    @JsonProperty
    private int moves;
    @JsonProperty("started_at")
    private LocalDateTime startedAt;
    @JsonProperty("finished_at")
    private LocalDateTime finishedAt;
    @JsonProperty
    private Integer[] slots;

    public GameResponse(){
        super();
    }
    public GameResponse(Game game) {
        this.state  = game.getState().toString();
        this.moves  = game.getMoves();
        this.startedAt  = game.getStartedAt();
        this.finishedAt  = game.getFinishedAt();
        this.slots = game.getBoard().getSlots().stream().map(this::getCharState).toArray(Integer[]::new);
    }

    private Integer getCharState(Slot slot) {
        if (slot.isOpened()){
            if (slot.isExploded()) {
                return SlotState.EXPLODED.value;
            } else {
                return SlotState.CLEARED.value;
            }
        } else {
            if (slot.isFlagged()) {
                return SlotState.FLAGGED.value;
            }
            if (slot.isQuestioned()) {
                return SlotState.QUESTIONED.value;
            }
        }
        return SlotState.CLOSED.value;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }

    enum SlotState {
        CLOSED(0),
        CLEARED(1),
        FLAGGED(2),
        QUESTIONED(3),
        EXPLODED(4);

        private final int value;

        SlotState(int value) {
            this.value = value;
        }
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public Integer[] getSlots() {
        return slots;
    }

    public void setSlots(Integer[] slots) {
        this.slots = slots;
    }
}