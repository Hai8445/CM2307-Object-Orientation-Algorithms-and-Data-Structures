public class PropertyFactory {
    public static Property createProperty(String ID, String address, double rent) {
        return new Property(ID, address, rent);
    }
}
