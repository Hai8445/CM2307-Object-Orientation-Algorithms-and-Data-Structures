import java.util.Scanner;
import java.util.List;

public class MainMenu {
   private RentalSystem system;
   private Scanner scanner;
   private User currentUser;
   
   public MainMenu(RentalSystem system) {
    this.system = system;
    this.scanner = new Scanner(System.in);
   }

   public void start() {
    if (login()) {
        showMainMenu();
    }
   }

   private boolean login() {
    System.out.println("--- StudentRentals Login ---");
    System.out.print("Enter your Email: ");
    String email = scanner.nextLine();

    User user = system.getUserDetails(email);

    if (user != null) {
        this.currentUser = user;
        System.out.println(user.getName() + " has successfully logged in with the email " + user.getEmail());
        return true;
    } else {
        System.out.println("User not found");
        return false;
    }
   }

   private void showMainMenu() {
    boolean on = true;
    while (on) {
        System.out.println("\n--- " + currentUser.getClass().getSimpleName() + " Dashboard ---");
        System.out.println("1. View Available Rooms");

        if (currentUser instanceof Student) {
            System.out.println("2. Book a room");
        } else if (currentUser instanceof Homeowner) {
            System.out.println("2. View my listings");
            System.out.println("3. Delete a listing");
        }
        System.out.println("3. Logout");
        System.out.println("Select an option");

        String selected = scanner.nextLine();
        switch(selected) {
            case "1":
                handleSearch();
                break;
            case "2":
                if (currentUser instanceof Student) handleBooking();
                else if (currentUser instanceof Homeowner) handleListing();
                break;
            case "3":
                if (currentUser instanceof Student) on = false;
                else if (currentUser instanceof Homeowner) handlePropertyDeletion();
                break;
            case "4":
                if (currentUser instanceof Homeowner) on = false;
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
   private void handleListing() {
    System.out.println("\n--- Your Properties ---");
    List<Property> myListings = system.getOwnerProperties(currentUser.getName());
    if (myListings.isEmpty()) {
        System.out.println("You currently have no listings");
    } else {
        myListings.forEach(System.out::println);
    }
   }

   private void handlePropertyDeletion() {
    System.out.print("Enter the ID of the property you wish to delete: ");
    String propertyID = scanner.nextLine();

    boolean success = system.removePropertyListing(propertyID, currentUser.getEmail());
    if (success) {
        System.out.println("Successfully removed property listing");
    }

   }

}