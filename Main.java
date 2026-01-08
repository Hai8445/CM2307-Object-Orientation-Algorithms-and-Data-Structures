public class Main {
    public static void main(String[] args){
        RentalSystem system = new RentalSystem();

        Student s1 = new Student("S1", "Craig Saxon", "craigsaxon@cardiff.ac.uk", "C24021993", 1);
        Homeowner h1 = new Homeowner("H1", "Sarah Connor", "sarahconnor@gmail.com", 3, true);
        Property p1 = new Property("P1", "64 Wick Street", 950.00, "sarahconnor@gmail.com");

        system.addUser(s1);
        system.addUser(h1);
        system.addProperty(p1);

        MainMenu menu = new MainMenu(system);
        menu.start();
        

    }
}
