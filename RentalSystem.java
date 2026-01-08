import java.util.*;

public class RentalSystem {
    private Map<String, User> users = new HashMap<>();
    private List<Property> properties = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    private static int newBookingID = 1;

    public void addUser(User user) {
        users.put(user.getEmail(), user);
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public User getUserDetails(String email) {
        return users.get(email);
    }

    public List<Property> filterByBudget(double maxBudget) {
        List<Property> availableInBudget = new ArrayList<>();
        for (Property p : properties) {
            if (p.getStatus() == PropertyStatus.AVAILABLE && p.getRent() <= maxBudget) {
                availableInBudget.add(p);
            }
        }
        return availableInBudget;
    }

    public void registerUser(User user) throws Exception {
        if (users.containsKey(user.getEmail())) {
            throw new Exception("User with this email already exists");
        }
        addUser(user);
        System.out.println("Registration successful for: " + user.getName());
    }

    public synchronized void bookRoom(String email, String propertyID) {
        User user = users.get(email);
        if (user == null) {
            System.out.println("User email doesn't exist");
            return;
        }
        if (!(user instanceof Student)) {
            System.out.println("Only students can book properties");
        return;
        }

        for (Property p : properties) {
            if (p.getPropertyID().equals(propertyID)) {
                if (p.getStatus() != PropertyStatus.AVAILABLE) {
                    throw new IllegalStateException("Room is already pending or taken");
                }
                p.setStatus(PropertyStatus.PENDING);
                String bookingID = "B" + newBookingID++;
                bookings.add(new Booking(bookingID, email, propertyID));
                System.out.println("Booking request sent! The owner will be in contact with you shortly");
                return;
            }
        }
        System.out.println("Error: Property ID not found");
    }

    public List<Property> getOwnerProperties(String ownerEmail) {
        List<Property> ownerProperties = new ArrayList<>();
        for (Property p : properties) {
            if (p.getOwnerEmail().equals(ownerEmail)){
                ownerProperties.add(p);
            }
        }
        return ownerProperties;
    }

    public List<Booking> getPendingBookings(String ownerEmail) {
        List<Booking> pending = new ArrayList<>();
        for (Booking b : bookings) {
            Property p = getPropertyByID(b.getPropertyID());
            if (p != null && p.getOwnerEmail().equals(ownerEmail)){
                pending.add(b);
            }
        }
        return pending;
    }

    public synchronized void approveBooking(String bookingID, String ownerEmail) {
        for (Booking b : bookings) {
            if (b.getBookingId().equals(bookingID)) {
                Property p = getPropertyByID(b.getPropertyID());
                if (p != null && p.getOwnerEmail().equals(ownerEmail)) {
                    b.setApproved(true);
                    p.setStatus(PropertyStatus.RENTED);
                    System.out.println("Booking " + bookingID + " approved!");
                    return;
                } else if (!p.getOwnerEmail().equals(ownerEmail)) {
                    System.out.println("You are not the owner of this property!");
                    return;
                }
            }
        }   
        System.out.println("Booking ID unresolved");
    }

    public synchronized void denyBooking(String bookingID, String ownerEmail) {
        for (Booking b : bookings) {
            if (b.getBookingId().equals(bookingID)){
                Property p = getPropertyByID(b.getPropertyID());
                if (p != null && p.getOwnerEmail().equals(ownerEmail)) {
                    bookings.remove(b);
                    p.setStatus(PropertyStatus.AVAILABLE);
                    System.out.println("Booking " + bookingID + " denied");
                    return;
                }
            }
        }
    }

    public void createProperty(String address, double rent, String ownerEmail) {
        Property newProp = PropertyFactory.createProperty(address, rent, ownerEmail);
        addProperty(newProp);
        System.out.println("Property successfully listed with ID " + newProp.getPropertyID());
    }

    public synchronized boolean removePropertyListing(String propertyID, String ownerEmail) {
        for (int i = 0; i < properties.size(); i++) {
            Property p = properties.get(i);
            if (p.getPropertyID().equals(propertyID)) {
                if (p.getOwnerEmail().equals(ownerEmail)) {
                    properties.remove(i);
                    return true; //Deletion successful
                } else {
                    System.out.println("You do not have permission to delete this property!");
                    return false;
                }
            }
        }
        System.out.println("Invalid Property ID");
        return false;
    }

    private Property getPropertyByID(String ID) {
        for (Property p : properties) {
            if (p.getPropertyID().equals(ID)) {
                return p;
            }
        }
        return null;
    }
}
