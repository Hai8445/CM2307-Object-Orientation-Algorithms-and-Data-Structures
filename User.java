public abstract class User {
    private String id; 
    private String name; 
    private String email;
    
    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getEmail() { return email;}
    public String getName() { return name; }
    public String getID() {return id;}

}


