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
    boolean exit = false;
    while (!exit) {
        System.out.println("\n--- Welcome to StudentRentals ---");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Select an option: ");
        
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                if (login()) showMainMenu();
                break;
            case "2":
                handleRegistration();
                break;
            case "3":
                exit = true;
                break;
            default:
                System.out.println("Invalid selection.");
        }
    }
   }

   private boolean login() {
    System.out.println("--- StudentRentals Login ---");

    System.out.print("Enter your Email: ");
    String email = scanner.nextLine();

    System.out.print("Enter Password: ");
    String pass = scanner.nextLine();

    User user = system.getUserDetails(email);

    if (user != null && user.checkPassword(pass)) {
        this.currentUser = user;
        System.out.println(user.getName() + " has successfully logged in with the email " + user.getEmail());
        return true;
    } else {
        System.out.println("Invalid email or password");
        return false;
    }
   }

   private void handleRegistration() {
    System.out.println("\n--- Register New Account ---");
    System.out.println("Are you a: 1. Student  2. Homeowner");
    String type = scanner.nextLine();

    System.out.print("Enter Name: ");
    String name = scanner.nextLine();
    System.out.print("Enter Email: ");
    String email = scanner.nextLine();
    System.out.print("Create Password: ");
    String password = scanner.nextLine();
    
    try {
        User newUser;
        String id = "U" + (System.currentTimeMillis() % 1000); 
        
        if (type.equals("1")) {
            System.out.print("Enter University ID: ");
            String uniID = scanner.nextLine();
            System.out.print("Enter Year of Study: ");
            int year = Integer.parseInt(scanner.nextLine());
            newUser = new Student(id, name, email, password, uniID, year);
        } else {
            System.out.print("How many properties do you own? ");
            int props = Integer.parseInt(scanner.nextLine());
            newUser = new Homeowner(id, name, password, email, props, false);
        }
        
        system.registerUser(newUser);
    } catch (Exception e) {
        System.out.println("Registration Failed: " + e.getMessage());
    }
    }

   private void showMainMenu() {
    boolean on = true;
    while (on) {
        System.out.println("\n--- " + currentUser.getClass().getSimpleName() + " Dashboard ---");
        System.out.println("1. View Available Rooms");

        if (currentUser instanceof Student) {
            System.out.println("2. Book a room");
            System.out.println("3. Logout");

        } else if (currentUser instanceof Homeowner) {
            System.out.println("2. View my listings");
            System.out.println("3. View/Approve Pending Bookings");
            System.out.println("4. Add property listing");
            System.out.println("5. Delete property listing");
            System.out.println("6. Logout");

        }
        System.out.println("Select an option");

        String selected = scanner.nextLine();
        if (currentUser instanceof Student) {
            switch(selected) {
                case "1":
                    handleSearch();
                    break;
                case "2":
                    handleListing();
                    break;
                case "3":
                    handleBooking();
                    break;
                case "4":
                    on = false;
                    break;
                default:
                    System.out.println("Invalid command");
                    break;

            }
        } else if (currentUser instanceof Homeowner) {
            switch(selected) {
                case "1":
                    handleSearch();
                    break;
                case "2":
                    handleListing();
                    break;
                case "3":
                    viewPendingBookings();
                    break;
                case "4":
                    handlePropertyCreation();
                    break;
                case "5":
                    handlePropertyDeletion();
                    break;
                case "6":
                    on = false;
                    break;
                default:
                    System.out.println("Invalid command");
                    break;

            }
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
    List<Property> myListings = system.getOwnerProperties(currentUser.getEmail());
    if (myListings.isEmpty()) {
        System.out.println("You currently have no listings");
    } else {
        myListings.forEach(System.out::println);
    }
   }

   private void viewPendingBookings() {
    List<Booking> pending = system.getPendingBookings(currentUser.getEmail());
    if (pending.isEmpty()) {
        System.out.println("No pending requests");
        return;
    }
    System.out.println("\n--- Current Pending Bookings ---");
    pending.forEach(System.out::println);

    System.out.println("\n> Enter the booking ID of the booking you would like to accept (or press enter to skip):");
    String bookingID = scanner.nextLine();
    if (!bookingID.isEmpty()) {
        system.approveBooking(bookingID, currentUser.getEmail());
    }
   }

   private void handlePropertyCreation() {
    System.out.println("\n--- Insert Property Details Below ---");
    System.out.print("Enter Address: ");
    String address = scanner.nextLine();

    System.out.print("Enter Monthly Rent: £");
    try {
        double rent = Double.parseDouble(scanner.nextLine());
        system.createProperty(address, rent, currentUser.getEmail());
    } catch (NumberFormatException e ) {
        System.out.println("Please enter a valid number");
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