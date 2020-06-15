package ar.com.lucianoclusa.minesweeper.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SlotTest {

    @Test
    @DisplayName("Given slots at (1,1) and (1,2) When first calls isNeighborOf the second Then return true")
    void testHorizontalNeighborSlots() {
        Slot firstSlot = new Slot(1,1);
        Slot secondSlot = new Slot(1,2);

        boolean isNeighbor = firstSlot.isNeighborOf(secondSlot);

        assertTrue(isNeighbor);
    }

    @Test
    @DisplayName("Given slots at (1,1) and (2,1) When first calls isNeighborOf the second Then return true")
    void testVerticalNeighborSlots() {
        Slot firstSlot = new Slot(1,1);
        Slot secondSlot = new Slot(2,1);

        boolean isNeighbor = firstSlot.isNeighborOf(secondSlot);

        assertTrue(isNeighbor);
    }

    @Test
    @DisplayName("Given slots at (1,1) and (3,1) When first calls isNeighborOf the second Then return false")
    void testNonNeighborSlots() {
        Slot firstSlot = new Slot(1,1);
        Slot secondSlot = new Slot(3,1);

        boolean isNeighbor = firstSlot.isNeighborOf(secondSlot);

        assertFalse(isNeighbor);
    }
}
