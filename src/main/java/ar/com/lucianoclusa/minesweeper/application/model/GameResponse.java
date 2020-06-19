package ar.com.lucianoclusa.minesweeper.application.model;

import ar.com.lucianoclusa.minesweeper.domain.Game;
import ar.com.lucianoclusa.minesweeper.domain.Slot;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameResponse {
    @JsonProperty
    private String id;
    @JsonProperty
    private String state;
    @JsonProperty
    private int moves;
    @JsonProperty("started_at")
    private LocalDateTime startedAt;
    @JsonProperty("finished_at")
    private LocalDateTime finishedAt;
    @JsonProperty
    private List<SlotResponse> slots;

    public GameResponse(){
        super();
    }
    public GameResponse(Game game) {
        this.id  = game.getId();
        this.state  = game.getState().toString();
        this.moves  = game.getMoves();
        this.startedAt  = game.getStartedAt();
        this.finishedAt  = game.getFinishedAt();
        this.slots = game.getBoard().getSlots().stream().map(SlotResponse::new).collect(Collectors.toList());
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
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

    public List<SlotResponse> getSlots() {
        return slots;
    }

    public void setSlots(List<SlotResponse> slots) {
        this.slots = slots;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}