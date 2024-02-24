package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import scheduling_service.Scheduler;
import scheduling_service.TimeBlock;

class SchedulerTest {

    Scheduler schedule;

    @Test
    public void testAddTimeBlock() {
        schedule = new Scheduler();

        assertThrows(IllegalArgumentException.class, () -> {
            schedule.addTimeBlock(new TimeBlock(LocalTime.of(12, 0), LocalTime.of(10, 0)));
        });

        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(10, 0), LocalTime.of(12, 0))));

        assertEquals(schedule.getSize(), 1);

        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(12, 0), LocalTime.of(14, 0)))); // Ensure we can add
                                                                                                    // one directly
                                                                                                    // after.

        assertEquals(schedule.getSize(), 2);

        assertFalse(schedule.addTimeBlock(new TimeBlock(LocalTime.of(13, 0), LocalTime.of(13, 30)))); // Ensure we can't
                                                                                                      // add a block
                                                                                                      // inside another

        assertEquals(schedule.getSize(), 2);

        assertFalse(schedule.addTimeBlock(new TimeBlock(LocalTime.of(6, 0), LocalTime.of(16, 30)))); // Ensure we can't
                                                                                                     // add one
                                                                                                     // surrounding time
                                                                                                     // blocks
    }

    @Test
    public void testAddAdjacentTimeBlockEndToStart() {
        Scheduler schedule = new Scheduler();
        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(10, 0), LocalTime.of(12, 0))));
        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(12, 0), LocalTime.of(14, 0))));
        assertEquals(2, schedule.getSize());
    }

    @Test
    public void testAddBlockInsideExistingBlock() {
        Scheduler schedule = new Scheduler();
        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(9, 0), LocalTime.of(15, 0))));
        assertFalse(schedule.addTimeBlock(new TimeBlock(LocalTime.of(10, 0), LocalTime.of(12, 0))));
        assertEquals(1, schedule.getSize());
    }

    @Test
    public void testAddBlockOverlappingExistingBlockStart() {
        Scheduler schedule = new Scheduler();
        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(10, 0), LocalTime.of(12, 0))));
        assertFalse(schedule.addTimeBlock(new TimeBlock(LocalTime.of(11, 30), LocalTime.of(13, 0))));
        assertEquals(1, schedule.getSize());
    }

    @Test
    public void testAddBlockOverlappingExistingBlockEnd() {
        Scheduler schedule = new Scheduler();
        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(12, 0), LocalTime.of(14, 0))));
        assertFalse(schedule.addTimeBlock(new TimeBlock(LocalTime.of(11, 0), LocalTime.of(12, 30))));
        assertEquals(1, schedule.getSize());
    }

    @Test
    public void testAddBlockThatSurroundsExistingBlock() {
        Scheduler schedule = new Scheduler();
        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(10, 0), LocalTime.of(11, 0))));
        assertFalse(schedule.addTimeBlock(new TimeBlock(LocalTime.of(9, 30), LocalTime.of(11, 30))));
        assertEquals(1, schedule.getSize());
    }

    @Test
    public void testAddNonOverlappingBlockBeforeExistingBlocks() {
        Scheduler schedule = new Scheduler();
        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(12, 0), LocalTime.of(14, 0))));
        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(10, 0), LocalTime.of(11, 30))));
        assertEquals(2, schedule.getSize());
    }

    @Test
    public void testAddNonOverlappingBlockAfterExistingBlocks() {
        Scheduler schedule = new Scheduler();
        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(10, 0), LocalTime.of(11, 30))));
        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(12, 0), LocalTime.of(14, 0))));
        assertEquals(2, schedule.getSize());
    }

    @Test
    public void testAddBlockWithSameStartTimeAsExistingBlockEndTime() {
        Scheduler schedule = new Scheduler();
        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(9, 0), LocalTime.of(10, 0))));
        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(10, 0), LocalTime.of(11, 0))));
        assertEquals(2, schedule.getSize());
    }

    @Test
    public void testAddBlockWithEndTimeEqualToExistingBlockStartTime() {
        Scheduler schedule = new Scheduler();
        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(11, 0), LocalTime.of(12, 0))));
        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(10, 0), LocalTime.of(11, 0))));
        assertEquals(2, schedule.getSize());
    }

    @Test
    public void testSetStartOfTimeBlock() {
        schedule = new Scheduler();

        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(10, 0), LocalTime.of(12, 0)))); // Set up schedule
                                                                                                    // from 10:00 -
                                                                                                    // 12:00

        assertNotNull(schedule.getTimeBlock(LocalTime.of(11, 0))); // ensure it was added

        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(12, 0), LocalTime.of(14, 0)))); // Set up schedule
                                                                                                    // from 1:00 - 2:00

        assertEquals(2, schedule.getSize());

        assertTrue(schedule.setStartOfTimeBlock(LocalTime.of(12, 1), LocalTime.of(13, 0))); // ensure we can change the
                                                                                            // one located at 12 to end
                                                                                            // at 1. find takes
                                                                                            // precedence by end?

    }

    @Test
    public void testSetEndOfTimeBlock() {
        schedule = new Scheduler();

        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(10, 0), LocalTime.of(12, 0)))); // Set up schedule
                                                                                                    // from 10:00 -
                                                                                                    // 12:00

        assertNotNull(schedule.getTimeBlock(LocalTime.of(11, 0))); // ensure it was added

        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(12, 0), LocalTime.of(14, 0)))); // Set up schedule
                                                                                                    // from 1:00 - 2:00

        assertEquals(2, schedule.getSize());

        assertTrue(schedule.setEndOfTimeBlock(LocalTime.of(12, 1), LocalTime.of(13, 0))); // ensure we can change the
                                                                                          // one located at 12 to end
                                                                                          // at 1. find takes
                                                                                          // precedence by end?

    }

    @Test
    public void testSetStartOfTimeOverlapping() {
        schedule = new Scheduler();

        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(10, 0), LocalTime.of(12, 0)))); // Set up schedule

        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(12, 0), LocalTime.of(14, 0)))); // Set up schedule

        assertFalse(schedule.setStartOfTimeBlock(LocalTime.of(12, 5), LocalTime.of(11, 0)));

    }

    @Test
    public void testSetEndOfTimeOverlapping() {
        schedule = new Scheduler();

        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(10, 0), LocalTime.of(12, 0))));
        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(13, 0), LocalTime.of(15, 0))));

        // Attempt to extend the end of the first block into the second block, which
        // should fail due to overlap
        assertFalse(schedule.setEndOfTimeBlock(LocalTime.of(11, 0), LocalTime.of(14, 0)));

        // Confirm the end time of the first block remains unchanged
        assertEquals(LocalTime.of(12, 0), schedule.getTimeBlock(LocalTime.of(11, 0)).getEndingBound());
    }

    @Test
    public void testCheckIfInTime() {
        schedule = new Scheduler();

        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(10, 0), LocalTime.of(12, 0))));
        assertTrue(schedule.checkIfInTime(LocalTime.of(11, 0))); // Check if 11:00 is within any time block
        assertFalse(schedule.checkIfInTime(LocalTime.of(12, 30))); // Check if 12:30 is outside any time block
    }

    @Test
    public void testSetStartPastEnd() {
        schedule = new Scheduler();

        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(10, 0), LocalTime.of(12, 0))));

        // Attempt to set the start time after the existing end time, which should fail
        assertThrows(IllegalArgumentException.class, () -> {
            schedule.setStartOfTimeBlock(LocalTime.of(11, 0), LocalTime.of(13, 0));
        });

        // Confirm the start time remains unchanged
        assertEquals(LocalTime.of(10, 0), schedule.getTimeBlock(LocalTime.of(11, 0)).getStartingBound());
    }

    @Test
    public void testSetEndBeforeStart() {
        schedule = new Scheduler();

        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(14, 0), LocalTime.of(16, 0))));

        // Attempt to set the end time before the existing start time, which should fail
        assertThrows(IllegalArgumentException.class, () -> {
            schedule.setEndOfTimeBlock(LocalTime.of(15, 0), LocalTime.of(13, 0));
        });

        // Confirm the end time remains unchanged
        assertEquals(LocalTime.of(16, 0), schedule.getTimeBlock(LocalTime.of(15, 0)).getEndingBound());
    }

    @Test
    public void testAdjustTimeBlockToAvoidOverlap() {
        schedule = new Scheduler();

        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(10, 0), LocalTime.of(12, 0))));
        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(12, 0), LocalTime.of(14, 0))));

        // Adjust the end time of the first block to just before the start of the second
        // block, which should fail
        assertFalse(schedule.setEndOfTimeBlock(LocalTime.of(10, 5), LocalTime.of(12, 1)));

        // Confirm the end time was adjusted correctly
        assertNotEquals(LocalTime.of(11, 59), schedule.getTimeBlock(LocalTime.of(10, 0)).getEndingBound());
    }

    @Test
    public void testAddBlockInAvailableGap() {
        schedule = new Scheduler();

        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(9, 0), LocalTime.of(10, 0))));
        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(11, 0), LocalTime.of(12, 0))));

        // Add a block in the gap between the two existing blocks
        assertTrue(schedule.addTimeBlock(new TimeBlock(LocalTime.of(10, 15), LocalTime.of(10, 45))));

        assertEquals(3, schedule.getSize());
    }

}
