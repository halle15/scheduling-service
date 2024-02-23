package scheduling_service;

import java.time.LocalTime;

public class TimeBlock {

    LocalTime startingBound;
    LocalTime endingBound;
    
    public TimeBlock() {
        this.startingBound = LocalTime.MIN;
        this.endingBound = LocalTime.MAX;
    }

    public TimeBlock(LocalTime start, LocalTime end) {
        this.startingBound = start;
        this.endingBound = end;
    }

    public LocalTime getStartingBound() {
        return startingBound;
    }
    

    public void setStartingBound(LocalTime startingBound) {
        if (endingBound.compareTo(startingBound) < 0) { // is ending bound is less than (-1) starting bound
            throw new IllegalArgumentException("The starting time is ahead of the ending time!");
        }

        this.startingBound = startingBound;
    }

    public LocalTime getEndingBound() {
        return endingBound;
    }

    public void setEndingBound(LocalTime endingBound) {
        if (startingBound.compareTo(endingBound) > 0) { // if the starting time is more than the ending time
            throw new IllegalArgumentException("The starting time is ahead of the ending time!");
        }

        this.endingBound = endingBound;
    }

    public boolean isInBounds(LocalTime time) {

        if (startingBound.compareTo(time) < 1 && endingBound.compareTo(time) > -1) {
            return true;
        }

        return false;
    }
    
    public int getTimeLengthInSeconds() {
        return this.endingBound.toSecondOfDay() - this.startingBound.toSecondOfDay();
    }

}
