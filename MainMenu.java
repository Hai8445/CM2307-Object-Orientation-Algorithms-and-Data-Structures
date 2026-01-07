import java.util.Scanner;
import java.util.List;

public class MainMenu {
   private RentalSystem system;
   private Scanner scanner;
   
   public MainMenu(RentalSystem system) {
    this.system = system;
    this.scanner = new Scanner(System.in);
   }

   public void start() {
    boolean on = true;
    while (on) {
        System.out.println("Student rentals management system");
        System.out.println("1. View Available Rooms");
        System.out.println("2. Book a Room");
        System.out.println("3. Close");
        System.out.println("Select an option");

        String selected = scanner.nextLine();
        switch(selected) {
            case "1":
                handleSearch();
                break;
            case "2":
                handleBooking();
                break;
            case "3":
                on = false;
                break;
            default:
                System.out.println("Invalid option");

        }

    }
   }

   private void handleSearch() {
    System.out.println("Enter your maximum monthly budget: ");
    try {
        double budget = Double.parseDouble(scanner.nextLine());
        List<Property> results = system.filterByBudget(budget);
        if (results.isEmpty()){
            System.out.println("Sorry, no rooms found within that budget");
        } else {
            System.out.println("\nRooms found:");
            results.forEach(System.out::println);
        }
    } catch(NumberFormatException exception) {
        System.out.println("Please enter a valid number");
    }
   }

   private void handleBooking() {
    System.out.print("Enter Student Email: ");
    String email = scanner.nextLine();
    System.out.print("Enter Property ID: ");
    String propertyID = scanner.nextLine();

    try {
        system.bookRoom(email, propertyID);
    } catch (Exception e) {
        System.out.println("Booking failed rip: " + e.getMessage());
    }

   }

}