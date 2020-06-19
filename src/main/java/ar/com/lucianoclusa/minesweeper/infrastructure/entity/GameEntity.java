package ar.com.lucianoclusa.minesweeper.infrastructure.entity;

import ar.com.lucianoclusa.minesweeper.domain.Board;
import ar.com.lucianoclusa.minesweeper.domain.Game;
import ar.com.lucianoclusa.minesweeper.domain.GameState;
import ar.com.lucianoclusa.minesweeper.domain.Slot;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@DynamoDBTable(tableName = "games")
public class GameEntity {
    private String id;
    private String userId;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private String state;
    private int moves;
    private List<SlotEntity> slots;
    private int rows;
    private int columns;
    private int mines;

    public GameEntity(){
        super();
    }

    public GameEntity(Game game, String userId) {
        this.id = game.getId();
        this.userId = userId;
        this.moves = game.getMoves();
        this.state = game.getState().name();
        this.finishedAt = game.getFinishedAt();
        this.startedAt = game.getStartedAt();
        this.rows = game.getBoard().getNumberOfRows();
        this.columns = game.getBoard().getNumberOfColumns();
        this.mines = game.getBoard().getNumberOfMines();
        this.slots = game.getBoard().getSlots().stream().map(SlotEntity::new).collect(Collectors.toList());
    }

    public Game toGame() {
        List<Slot> gameSlots = slots.stream().map(SlotEntity::toSlot).collect(Collectors.toList());

        Board board = new Board(rows, columns, mines, gameSlots);
        return new Game(id, board, GameState.valueOf(state));
    }

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBTypeConverted( converter = LocalDateTimeConverter.class )
    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    @DynamoDBTypeConverted( converter = LocalDateTimeConverter.class )
    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }

    @DynamoDBAttribute
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @DynamoDBAttribute
    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    @DynamoDBAttribute
    public List<SlotEntity> getSlots() {
        return slots;
    }

    public void setSlots(List<SlotEntity> slots) {
        this.slots = slots;
    }

    @DynamoDBAttribute
    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @DynamoDBAttribute
    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    @DynamoDBAttribute
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    static public class LocalDateTimeConverter implements DynamoDBTypeConverter<String, LocalDateTime> {

        @Override
        public String convert( final LocalDateTime time ) {

            return time.toString();
        }

        @Override
        public LocalDateTime unconvert( final String stringValue ) {

            return LocalDateTime.parse(stringValue);
        }
    }

}
