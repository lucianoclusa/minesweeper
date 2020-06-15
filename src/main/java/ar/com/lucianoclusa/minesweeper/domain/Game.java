package ar.com.lucianoclusa.minesweeper.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Game {

    public Game(Board board, User user) {
        this.board = board;
        this.user = user;
        this.state = GameState.NOT_STARTED;
    }

    private String id;
    private final Board board;
    private final User user;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private GameState state;
    private int moves;

    public void initGame() {
        this.startedAt = LocalDateTime.now();
        this.state = GameState.INIT;
        this.moves = 0;
    }

    public void openSlot(int row, int column) {
        if(state == GameState.NOT_STARTED) {
            initGame();
        }

        Slot slot = board.getSlot(row, column);
        if(canOpenSlot(slot)) {
            this.moves++;

            if(slot.isMined()) {
                finishGame(GameState.LOST);
                return;
            } else {
                openSelfAndNeighborsRecursively(slot);
            }
            if(board.isCleared()) {
                finishGame(GameState.WON);
            }
        }
    }

    private void finishGame(GameState result) {
        this.state = result;
        finishedAt = LocalDateTime.now();
    }

    private boolean canOpenSlot(Slot slot) {
        return this.isNotFinished() && !slot.isCleared() && !slot.isFlagged();
    }

    private void openSelfAndNeighborsRecursively(Slot slot) {
        slot.setCleared(true);
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Board getBoard() {
        return board;
    }

    public User getUser() {
        return user;
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

}
