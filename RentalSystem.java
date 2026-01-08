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
                System.out.println("Booking for " + user.getName() + " successful");
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

    public void createProperty(String address, double rent, String ownerEmail) {
        String newPropertyID = "P" + (properties.size() + 1);
        Property newProp = PropertyFactory.createProperty(newPropertyID, address, rent, ownerEmail);

        addProperty(newProp);
        System.out.println("Property successfully listed with ID " + newPropertyID);
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
}
