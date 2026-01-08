public class Main {
    public static void main(String[] args){
        RentalSystem system = new RentalSystem();

        MainMenu menu = new MainMenu(system);
        menu.start();
        

    }
}
