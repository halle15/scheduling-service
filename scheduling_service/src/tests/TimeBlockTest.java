package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import scheduling_service.TimeBlock;

class TimeBlockTest {

    TimeBlock block = new TimeBlock();

    @Test
    void TestSetStartingBound() {
        assertEquals(this.block.getStartingBound(), LocalTime.MIN);

        this.block.setStartingBound(LocalTime.of(12, 0, 0));

        this.block.setEndingBound(LocalTime.of(13, 0, 0));

        assertEquals(this.block.getStartingBound(), LocalTime.NOON); // good to see localtime of 12, 0, 0 easily equals
                                                                     // noon

        assertThrows(IllegalArgumentException.class, () -> {
            this.block.setStartingBound(LocalTime.of(14, 0, 0));
        });

        this.block.setStartingBound(LocalTime.of(13, 0));

        assertEquals(this.block.getStartingBound(), LocalTime.of(13, 0));// Ensure we can set the starting bound to be
                                                                         // the same time as the ending bound.
    }

    @Test
    void TestSetEndingBound() {
        this.block.setStartingBound(LocalTime.of(11, 0, 0));

        this.block.setEndingBound(LocalTime.of(12, 0, 0)); // Set the time block to be from 11:00 - 12:00

        assertEquals(this.block.getEndingBound(), LocalTime.NOON); // Ensure the ending block is equal to noon

        assertThrows(IllegalArgumentException.class, () -> {
            this.block.setEndingBound(LocalTime.of(10, 0, 0)); // Use the lambda function to check if this will throw an
                                                               // exception safely
        });

        this.block.setEndingBound(LocalTime.of(11, 0)); // Ensure we can set the ending bound to be the same time as
                                                        // starting bound.

        assertEquals(this.block.getEndingBound(), LocalTime.of(11, 0));
    }

    @ParameterizedTest
    @MethodSource("timeInRangeProvider")
    void testIsInBounds(LocalTime testTime) {
        this.block.setStartingBound(LocalTime.NOON);
        this.block.setEndingBound(LocalTime.of(16, 0));

        assertTrue(this.block.isInBounds(testTime), "Time should be within bounds");

    }

    @ParameterizedTest
    @MethodSource("timeOutOfRangeProvider")
    void testIsNotInBounds(LocalTime testTime) {
        this.block.setStartingBound(LocalTime.NOON);
        this.block.setEndingBound(LocalTime.of(16, 0));

        assertFalse(this.block.isInBounds(testTime), "Time should be out of bounds");
    }

    static Stream<LocalTime> timeInRangeProvider() {
        return Stream.iterate(LocalTime.NOON, time -> time.isBefore(LocalTime.of(16, 0)), time -> time.plusMinutes(10));
    }

    static Stream<LocalTime> timeOutOfRangeProvider() {
        Stream<LocalTime> beforeNoon = Stream.iterate(LocalTime.MIN, time -> time.plusMinutes(10))
                .limit(LocalTime.NOON.toSecondOfDay() / 600); // Generates times before NOON
        Stream<LocalTime> afterFour = Stream.iterate(LocalTime.of(16, 0).plusMinutes(10), time -> time.plusMinutes(10))
                .limit((LocalTime.MAX.toSecondOfDay() - LocalTime.of(16, 0).toSecondOfDay()) / 600); // Generates times
                                                                                                     // after 4 PM
        return Stream.concat(beforeNoon, afterFour);
    }

    @Test
    void TestTimeLengthInSeconds() {
        this.block.setStartingBound(LocalTime.of(8, 0));
        this.block.setEndingBound(LocalTime.of(16, 0));

        assertEquals(this.block.getTimeLengthInSeconds(), 28800); // 8 hrs * 60 min * 60 sec = 28,800 sec

        this.block.setEndingBound(LocalTime.of(8, 0));

        assertEquals(this.block.getTimeLengthInSeconds(), 0);

        this.block.setEndingBound(LocalTime.of(8, 0, 1));

        assertEquals(this.block.getTimeLengthInSeconds(), 1);

        this.block.setEndingBound(LocalTime.of(8, 59, 59));

        assertEquals(this.block.getTimeLengthInSeconds(), 3599);
    }
}
