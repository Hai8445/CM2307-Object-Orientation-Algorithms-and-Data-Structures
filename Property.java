public class Property {
    private String propertyID;
    private String address;
    private double rent;
    private PropertyStatus propertyStatus;

    public Property(String propertyID, String address, double rent) {
        this.propertyID = propertyID;
        this.address = address; 
        this.rent = rent;
        this.propertyStatus = PropertyStatus.AVAILABLE;
    }

    public double getRent() {return rent; }
    public PropertyStatus getStatus() {return propertyStatus;}
    public void setStatus(PropertyStatus status) {this.propertyStatus = status;}
    public String getPropertyID() {return propertyID;}

    @Override
    public String toString() {
        return String.format("ID: %s | Address : %s | Rent: £%.2f | Status : %s",
        propertyID, address, rent, propertyStatus);
    }

}
