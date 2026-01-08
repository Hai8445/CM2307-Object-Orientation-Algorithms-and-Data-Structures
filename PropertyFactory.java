public class PropertyFactory {
    private static int nextPropertyID = 1;
    public static Property createProperty(String ID, String address, double rent, String ownerEmail) {
        String nextID = "P" + nextPropertyID++;
        return new Property(nextID, address, rent, ownerEmail);
    }
}
