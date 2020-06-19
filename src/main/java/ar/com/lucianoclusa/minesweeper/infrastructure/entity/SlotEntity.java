package ar.com.lucianoclusa.minesweeper.infrastructure.entity;

import ar.com.lucianoclusa.minesweeper.domain.Slot;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class SlotEntity {
    private int row;
    private int column;
    private String state;
    private boolean isMined;

    public SlotEntity() {
        super();
    }

    public SlotEntity(Slot slot) {
        this.row = slot.getRow();
        this.column = slot.getColumn();
        this.state = slot.getState();
        this.isMined = slot.isMined();
    }

    public Slot toSlot() {
        Slot slot = new Slot(column, row);
        slot.setState(state);
        slot.setMined(isMined);
        return slot;
    }

    @DynamoDBAttribute
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @DynamoDBAttribute
    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @DynamoDBAttribute
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @DynamoDBAttribute
    public boolean isMined() {
        return isMined;
    }

    public void setMined(boolean mined) {
        isMined = mined;
    }
}
