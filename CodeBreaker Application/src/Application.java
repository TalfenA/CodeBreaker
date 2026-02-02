public class Application {
    /**
     * Default constructor for the application class
     */
    private Application(){

    }

    /**
     * Main function
     */
    public static void main(String args[]){
        System.out.println("++++++++++++++System starting++++++++++++++");

        Menu main = new Menu();
        main.initialise();

        System.out.println("++++++++++++++System exit++++++++++++++");
    }
}
