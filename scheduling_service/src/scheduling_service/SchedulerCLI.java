package scheduling_service;

import java.time.LocalTime;
import java.util.Scanner;


/*
 * This class is an example implementation of the library. The library could
 * theoretically be used in multiple implementations, especially in web environments.
 * 
 */
public class SchedulerCLI {

    private static Scheduler scheduler = new Scheduler();

    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nScheduler CLI");
            System.out.println("1. Add Time Block");
            System.out.println("2. Check If Time Is In Any Block");
            System.out.println("3. List All Time Blocks");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    addTimeBlock(scanner);
                    break;
                case 2:
                    checkIfInTime(scanner);
                    break;
                case 3:
                    listTimeBlocks();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addTimeBlock(Scanner scanner) {
        System.out.println("Add a new Time Block:");
        System.out.print("Start Hour: ");
        int startHour = scanner.nextInt();
        System.out.print("Start Minute: ");
        int startMinute = scanner.nextInt();
        System.out.print("End Hour: ");
        int endHour = scanner.nextInt();
        System.out.print("End Minute: ");
        int endMinute = scanner.nextInt();

        TimeBlock newBlock = new TimeBlock(LocalTime.of(startHour, startMinute), LocalTime.of(endHour, endMinute));

        if (scheduler.addTimeBlock(newBlock)) {
            System.out.println("Time block added successfully.");
        } else {
            System.out.println("Failed to add time block due to an overlap.");
        }
    }

    private static void checkIfInTime(Scanner scanner) {
        System.out.println("Check if a Time Is In Any Block:");
        System.out.print("Hour: ");
        int hour = scanner.nextInt();
        System.out.print("Minute: ");
        int minute = scanner.nextInt();

        if (scheduler.checkIfInTime(LocalTime.of(hour, minute))) {
            System.out.println("The time is within an existing block.");
        } else {
            System.out.println("The time is not within any block.");
        }
    }

    private static void listTimeBlocks() {
        System.out.println("Listing all time blocks:");
        for (TimeBlock block : scheduler.toArray()) {
            System.out.println("Block: From " + block.getStartingBound() + " to " + block.getEndingBound());
        }
    }
}
