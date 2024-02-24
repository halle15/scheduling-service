package scheduling_service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.ArrayList;

public class Scheduler {
    ArrayList<TimeBlock> clock;

    public Scheduler() {
        this.clock = new ArrayList<TimeBlock>();
    }

    public Scheduler(List<TimeBlock> blocks) {

        this.clock = new ArrayList<TimeBlock>();

        for (TimeBlock t : blocks) { // o(n) :(
            clock.add(t);
        }

    }

    public Scheduler(TimeBlock... blocks) {

        this.clock = new ArrayList<TimeBlock>();

        for (TimeBlock t : blocks) { // o(n) :(
            clock.add(t);
        }

    }

    public ArrayList<TimeBlock> toArray() {
        return clock;
    }

    public int getSize() {
        return clock.size();
    }

    public boolean addTimeBlock(TimeBlock block) {
        if (!checkIfOverlapping(block)) {
            return clock.add(block);
        }

        return false;

    }

    public boolean checkIfOverlapping(TimeBlock block) {
        if (clock.isEmpty()) {
            return false;
        }

        for (TimeBlock t : clock) {
            if (block.isOverlapping(t)) {
                return true;
            }
        }

        return false;
    }

    public boolean setStartOfTimeBlock(LocalTime timeToFind, LocalTime modifiedTime) {
        TimeBlock foundBlock = getTimeBlock(timeToFind);

        if (foundBlock.getEndingBound().compareTo(modifiedTime) < 0) {
            throw new IllegalArgumentException("The ending bound can not be ahead of the starting bound!");
        }
        
        for(TimeBlock t : clock) {
            if(t.equals(foundBlock)) {
                continue;
            }
            
            if(t.isInBounds(modifiedTime)){
                return false;
            }
        }

        foundBlock.setStartingBound(modifiedTime);

        return true;
    }

    public boolean setEndOfTimeBlock(LocalTime timeToFind, LocalTime modifiedTime) {
        TimeBlock foundBlock = getTimeBlock(timeToFind);

        if (foundBlock.getStartingBound().compareTo(modifiedTime) > 0) {
            throw new IllegalArgumentException("The ending bound can not be before of the starting bound!");
        }
        
        for(TimeBlock t : clock) {
            if(t.equals(foundBlock)) {
                continue;
            }
            
            if(t.isInBounds(modifiedTime)){
                return false;
            }
        }

        foundBlock.setEndingBound(modifiedTime);

        return true;

    }

    public LocalTime getStartOfTimeBlock(LocalTime timeToFind) {
        return getTimeBlock(timeToFind).getStartingBound();
    }

    public LocalTime getEndOfTimeBlock(LocalTime timeToFind) {
        return getTimeBlock(timeToFind).getEndingBound();
    }

    public TimeBlock getTimeBlock(LocalTime time) {

        for (TimeBlock block : clock) { // o(n) :(
            if (block.isInBounds(time)) {
                return block;
            }
        }

        return null;
    }

    public boolean checkIfInTime(LocalTime time) {

        if (clock.isEmpty()) {
            return false;
        }
        
        
        for (TimeBlock block : clock) { // o(n) :(
            if (block.isInBounds(time)) {
                return true;
            }
        }

        return false;
    }

}
