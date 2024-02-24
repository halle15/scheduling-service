package scheduling_service;

import java.time.LocalTime;
import java.util.Objects;

public class TimeBlock implements Comparable<TimeBlock> {

    LocalTime startingBound;
    LocalTime endingBound;

    public TimeBlock() {
        this.startingBound = LocalTime.MIN;
        this.endingBound = LocalTime.MAX;
    }

    public TimeBlock(LocalTime start, LocalTime end) {
        if(start.compareTo(end) > 0) {
            throw new IllegalArgumentException("The starting time is ahead of the ending time!");
        }
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

    /*
     * 
     * A = Start of this block B = End of this block C = Start of other block D =
     * End of other block
     * 

     */
    public boolean isOverlapping(TimeBlock otherBlock) {
        // Check if this block ends before the other block starts or
        // this block starts after the other block ends.
        // If either condition is true, the blocks do not overlap.

        boolean thisEndsBeforeOtherStarts = this.endingBound.compareTo(otherBlock.getStartingBound()) <= 0;
        boolean thisStartsAfterOtherEnds = this.startingBound.compareTo(otherBlock.getEndingBound()) >= 0;

        // If either condition is true, blocks do not overlap.
        return !(thisEndsBeforeOtherStarts || thisStartsAfterOtherEnds);
    }

    public int getTimeLengthInSeconds() {
        return this.endingBound.toSecondOfDay() - this.startingBound.toSecondOfDay();
    }

    @Override
    public int compareTo(TimeBlock other) {
        // Compare starting bounds first
        int startComparison = this.startingBound.compareTo(other.startingBound);
        if (startComparison != 0) {
            return startComparison;
        }
        // If starting bounds are equal, compare ending bounds
        return this.endingBound.compareTo(other.endingBound);
    }
    
    @Override
    public String toString() {
        return "From " + this.startingBound.toString() + " to " + this.endingBound.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(endingBound, startingBound);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TimeBlock other = (TimeBlock) obj;
        return Objects.equals(endingBound, other.endingBound) && Objects.equals(startingBound, other.startingBound);
    }
    
    

}
