package FitnessManagementSystem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FitnessManagementSystem {
    
    // Define constants
    private static final String[] DAYS = {"Saturday", "Sunday"};
    private static final String[] FITNESS_TYPES = {"Yoga", "Pilates", "Zumba", "Spinning"};
    private static final HashMap<String, Integer> PRICES = new HashMap<>();
    
    // Define variables
    private static ArrayList<HashMap<String, Object>> timetable = new ArrayList<>();
    private static ArrayList<String> bookedLessonIds = new ArrayList<>();
    
    public static void main(String[] args) {
        
        // Initialize prices
        PRICES.put("Yoga", 10);
        PRICES.put("Pilates", 12);
        PRICES.put("Zumba", 8);
        PRICES.put("Spinning", 15);
         
        // Initialize timetable
        for (int i = 0; i < 8; i++) {
            HashMap<String, Object> lesson = new HashMap<>();
            lesson.put("id", "Lesson" + (i + 1));
            lesson.put("day", DAYS[i % 2]);
            lesson.put("time", "10:00");
            lesson.put("type", FITNESS_TYPES[i % 4]);
            lesson.put("capacity", 5);
            lesson.put("price", PRICES.get(FITNESS_TYPES[i % 4]));
            timetable.add(lesson);
        }
      
        // add testcases
        bookedLessonIds.add("Lesson4");
        bookedLessonIds.add("Lesson7");
        bookedLessonIds.add("Lesson6");
        bookedLessonIds.add("Lesson2");
        bookedLessonIds.add("Lesson8");
       
        
        // Run booking system
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nWelcome to the fitness booking system!");
            System.out.println("Please select an option:");
            System.out.println("1. Book a group fitness lesson");
            System.out.println("2. Change/Cancel a booking");
            System.out.println("3. Attend a lesson");
            System.out.println("4. Monthly lesson report");
            System.out.println("5. Monthly champion fitness type report");
            System.out.println("0. Exit");
            int option = scanner.nextInt();
            
            switch (option) {
                case 1:
                    bookLesson(scanner);
                    break;
                case 2:
                    changeOrCancelBooking(scanner);
                    break;
                case 3:
                    attendLesson(scanner);
                    break;
                case 4:
                    monthlyLessonReport();
                    break;
                case 5:
                    monthlyChampionFitnessTypeReport();
                    break;
                case 0:
                    System.out.println("Thank you for using the fitness booking system!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
   
    
    private static void bookLesson(Scanner scanner) {
        
        System.out.println("\nPlease select a way to check the timetable:");
        System.out.println("1. Specify day");
        System.out.println("2. Specify fitness type");
        int option = scanner.nextInt();
        
        ArrayList<HashMap<String, Object>> filteredTimetable = new ArrayList<>();
        switch (option) {
            case 1:
                System.out.println("Please select a day (1 for Saturday, 2 for Sunday):");
                int dayOption = scanner.nextInt();
                String selectedDay = DAYS[dayOption - 1];
                for (HashMap<String, Object> lesson : timetable) {
                    if (lesson.get("day").equals(selectedDay)) {
                        filteredTimetable.add(lesson);
                    }
                }
                break;
            case 2:
                System.out.println("Please select a fitness type (1 for Yoga, 2 for Pilates, 3 for Zumba, 4 for Spinning):");
            int typeOption = scanner.nextInt();
            String selectedType = FITNESS_TYPES[typeOption - 1];
            for (HashMap<String, Object> lesson : timetable) {
                if (lesson.get("type").equals(selectedType)) {
                    filteredTimetable.add(lesson);
                }
            }
            break;
        default:
            System.out.println("Invalid option. Please try again.");
            return;
    }
    
    System.out.println("\nAvailable lessons:");
    System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s\n", "ID", "Day", "Time", "Type", "Capacity", "Price");
    for (HashMap<String, Object> lesson : filteredTimetable) {
        if (!bookedLessonIds.contains((String) lesson.get("id"))) {
            System.out.printf("%-10s %-10s %-10s %-10s %-10s $%-10s\n", 
                lesson.get("id"), lesson.get("day"), lesson.get("time"), lesson.get("type"), lesson.get("capacity"), lesson.get("price"));
        
        }
    }
    
    System.out.println("\nEnter the ID of the lesson you want to book:");
    String selectedLessonId = scanner.next();
    
    for (HashMap<String, Object> lesson : filteredTimetable) {
        if (lesson.get("id").equals(selectedLessonId)) {
            int capacity = (int) lesson.get("capacity");
            if (capacity == 0) {
                System.out.println("Sorry, this lesson is already fully booked.");
                return;
            } else {
               
                lesson.put("capacity", capacity - 1);
                bookedLessonIds.add(selectedLessonId);
                System.out.println("Booking successful! The lesson ID is " + selectedLessonId + ".");
                return;
            }
        }
    }
     System.out.println("Invalid lesson ID. Please try again.");
   }
 
    
     private static void changeOrCancelBooking(Scanner scanner) {
    System.out.println("\nEnter the ID of the lesson you want to change/cancel booking:");
    String selectedLessonId = scanner.next();

    if (bookedLessonIds.contains(selectedLessonId)) {
        for (HashMap<String, Object> lesson : timetable) {
            if (lesson.get("id").equals(selectedLessonId)) {
                int capacity = (int) lesson.get("capacity");
                lesson.put("capacity", capacity + 1);
                bookedLessonIds.remove(selectedLessonId);
                System.out.println("Booking cancelled/changed successfully.");
                return;
            }
        }
    } else 
    {
     System.out.println("Invalid lesson ID. Please try again.");
        }
     }
     
     
     private static void attendLesson(Scanner scanner) {
    System.out.println("\nEnter the ID of the lesson you want to attend:");
    String selectedLessonId = scanner.next();

    if (bookedLessonIds.contains(selectedLessonId)) {
        bookedLessonIds.remove(selectedLessonId);
        System.out.println("You have attended the lesson.");
    } else {
        System.out.println("You have not booked this lesson.");
    }
}
     private static void monthlyLessonReport() {
    System.out.println("\nMonthly lesson report:");
    System.out.printf("%-10s %-10s\n", "Type", "Number of Lessons");
    HashMap<String, Integer> lessonCounts = new HashMap<>();
    for (HashMap<String, Object> lesson : timetable) {
        String type = (String) lesson.get("type");
        int count = lessonCounts.getOrDefault(type, 0);
        lessonCounts.put(type, count + 1);
    }
    for (String type : lessonCounts.keySet()) {
        System.out.printf("%-10s %-10s\n", type, lessonCounts.get(type));
    }
}
     
     private static void monthlyChampionFitnessTypeReport() {
    // Initialize variables
    HashMap<String, Integer> revenueByFitnessType = new HashMap<>();
    int highestRevenue = 0;
    String championFitnessType = "";

    // Calculate revenue earned by each fitness type during the month
    for (HashMap<String, Object> lesson : timetable) {
        if (!bookedLessonIds.contains((String) lesson.get("id"))) {
            String fitnessType = (String) lesson.get("type");
            int price = (int) lesson.get("price");
            int revenue = price * ((int) lesson.get("capacity"));
            revenueByFitnessType.put(fitnessType, revenueByFitnessType.getOrDefault(fitnessType, 0) + revenue);
        }
    }

    // Find the fitness type with the highest revenue
    for (String fitnessType : revenueByFitnessType.keySet()) {
        int revenue = revenueByFitnessType.get(fitnessType);
        if (revenue > highestRevenue) {
            highestRevenue = revenue;
            championFitnessType = fitnessType;
        }
    }

    // Print the champion fitness type report
    System.out.println("\nMonthly champion fitness type report:");
    System.out.println("The fitness type with the highest revenue earned during the month is " + championFitnessType + ".");
    System.out.println("The total revenue earned by this fitness type during the month is $" + highestRevenue + ".");
}
    
}


    
  