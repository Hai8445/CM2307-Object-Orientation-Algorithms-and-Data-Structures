public abstract class User {
    private String id; 
    private String name; 
    private String email;
    private String password;
    
    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public String getEmail() { return email;}
    public String getName() { return name; }
    public String getID() {return id;}

}


