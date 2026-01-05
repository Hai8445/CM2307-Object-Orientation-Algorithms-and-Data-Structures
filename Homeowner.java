public class Homeowner {
    private int noOfProperties;
    private boolean superHost;

    public Homeowner(String ID, String name, String email, int noOfProperties, boolean superHost){
        super(ID, name, email);
        this.noOfProperties = noOfProperties;
        this.superHost = superHost;
    }

}
