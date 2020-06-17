package ar.com.lucianoclusa.minesweeper.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Game {

    public Game(Board board) {
        this.board = board;
        this.state = GameState.NOT_STARTED;
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
        if(state == GameState.NOT_STARTED) {
            initGame();
        }

        Slot slot = board.getSlot(row, column);
        if(canOpenSlot(slot)) {
            this.moves++;

            if(slot.isMined()) {
                slot.open();
                slot.setExploded(true);
                finishGame(GameState.LOST);
                return;
            } else {
                openSelfAndNeighborsRecursively(slot);
            }
            if(board.isCleared()) {
                finishGame(GameState.WON);
            }
        } else {
            throw new IllegalArgumentException("Can't open flagged or questioned  slots");
        }
    }

    public void flagSlot(int row, int column) {
        board.getSlot(row, column).setQuestioned(false);
        board.getSlot(row, column).setFlagged(true);
    }

    public void questionSlot(int row, int column) {
        board.getSlot(row, column).setFlagged(false);
        board.getSlot(row, column).setQuestioned(true);
    }

    public void unFlagSlot(int row, int column) {
        board.getSlot(row, column).setFlagged(false);
    }

    public void unQuestionSlot(int row, int column) {
        board.getSlot(row, column).setQuestioned(false);
    }

    private void finishGame(GameState result) {
        this.state = result;
        board.openAllSlots();
        finishedAt = LocalDateTime.now();
    }

    private boolean canOpenSlot(Slot slot) {
        return this.isNotFinished() && !slot.isOpened() && !slot.isFlagged();
    }

    private void openSelfAndNeighborsRecursively(Slot slot) {
        slot.open();
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
}
