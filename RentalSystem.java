import java.util.*;

public class RentalSystem {
    private Map<String, User> users = new HashMap<>();
    private List<Property> properties = new ArrayList<>();

    public void addUser(User user) {
        users.put(user.getEmail(), user);
    }

    public void addProperty(Property property) {
        properties.add(property);
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
                p.setStatus(PropertyStatus.RENTED);
                System.out.println("Booking for " + user.getName() + " successful");
                return;
            }
        }
        System.out.println("Error: Property ID not found");
    }
}
