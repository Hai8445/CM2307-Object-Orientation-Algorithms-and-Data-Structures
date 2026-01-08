public class PropertyFactory {
    public static Property createProperty(String ID, String address, double rent, String ownerEmail) {
        return new Property(ID, address, rent, ownerEmail);
    }
}
