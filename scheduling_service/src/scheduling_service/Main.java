package scheduling_service;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {

    public static void main(String[] args) {
       System.out.println("Scheduling Service");
       
       
       
       LocalTime startingBound = LocalTime.of(6, 0); // start at 6
       LocalTime endingBound = LocalTime.of(8, 0); // end at 8
       
       System.out.println(startingBound.compareTo(endingBound)); // should be -1?
       
       System.out.println(endingBound.compareTo(startingBound)); // should be 1?
       
       System.out.println(new TimeBlock(LocalTime.of(10, 0), LocalTime.of(12, 0)).hashCode());
       
       System.out.println(new TimeBlock(LocalTime.of(12, 0), LocalTime.of(14, 0)).hashCode());

    }

}
