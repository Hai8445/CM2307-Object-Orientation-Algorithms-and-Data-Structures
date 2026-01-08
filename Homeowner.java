public class Homeowner extends User {
    private int noOfProperties;
    private boolean superHost;

    public Homeowner(String ID, String name, String email, String password, int noOfProperties, boolean superHost){
        super(ID, name, email, password);
        this.noOfProperties = noOfProperties;
        this.superHost = superHost;
    }

    public int getNoOfProperties() { return noOfProperties; }
    public boolean isSuperHost() { return superHost; }

}
