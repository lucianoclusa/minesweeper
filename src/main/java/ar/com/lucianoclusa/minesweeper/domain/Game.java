package ar.com.lucianoclusa.minesweeper.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Game {

    public Game(Board board) {
        this.board = board;
        this.state = GameState.NOT_STARTED;
    }
    public Game(String id, Board board, GameState state) {
        this.id = id;
        this.board = board;
        this.state = state;
    }

    public Game(String id) {
        this.id = id;
        this.board = null;
    }

    private String id;
    private final Board board;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private GameState state;
    private int moves;

    public void initGame() {
        this.startedAt = LocalDateTime.now();
        this.state = GameState.IN_PROGRESS;
        this.moves = 0;
    }

    public void openSlot(int row, int column) {
        if (isNotFinished()) {
            if(state == GameState.NOT_STARTED) {
                initGame();
            }

            Slot slot = board.getSlot(row, column);
            if(canOpenSlot(slot)) {
                this.moves++;

                if(slot.isMined()) {
                    explodeSlot(slot);
                    finishGame(GameState.LOST);
                    return;
                } else {
                    openSelfAndNeighborsRecursively(slot);
                }
                if(board.isCleared()) {
                    finishGame(GameState.WON);
                }
            } else {
                throw new IllegalArgumentException("Can't open opened, flagged or questioned slots");
            }
        } else {
            throw new IllegalArgumentException("Game already " + state);
        }
    }

    private void explodeSlot(Slot slot) {
        slot.setState(Slot.SlotState.EXPLODED.getValue());
    }

    public void flagSlot(int row, int column) {
        board.getSlot(row, column).setState(Slot.SlotState.FLAGGED.getValue());
    }

    public void questionSlot(int row, int column) {
        board.getSlot(row, column).setState(Slot.SlotState.QUESTIONED.getValue());
    }

    public void cleanSlot(int row, int column) {
        Slot slot = board.getSlot(row, column);
        if (!slot.isFlagged() && !slot.isQuestioned()) {
            throw new IllegalArgumentException("Can only clear flagged or questioned slots");
        } else {
            slot.setState(Slot.SlotState.CLOSED.getValue());
        }
    }

    private void finishGame(GameState result) {
        this.state = result;
        board.revealAllSlots();
        finishedAt = LocalDateTime.now();
    }

    private boolean canOpenSlot(Slot slot) {
        return slot.isClosed();
    }

    private void openSelfAndNeighborsRecursively(Slot slot) {
        board.openSlot(slot);
        List<Slot> neighbors = board.getNeighbors(slot);

        if (this.isNotFinished() && hasNonMinedNeighbors(neighbors)) {
            neighbors.stream().filter(this::canOpenSlot)
                    .forEach(
                            this::openSelfAndNeighborsRecursively
                    );
        }
    }

    private boolean hasNonMinedNeighbors(List<Slot> neighbors) {
        return neighbors.stream().noneMatch(Slot::isMined);
    }

    private boolean isNotFinished() {
        return this.state != GameState.WON && this.state != GameState.LOST;
    }

    public Board getBoard() {
        return board;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
