import java.io.*;
import java.util.Scanner;

public class Menu {
    //Initialise the cypher algorithms to be used
    private Caesar caesarShift = new Caesar();
    private KeyedCaesar keyedShift = new KeyedCaesar();

    private Vigenere vigenereShift = new Vigenere();

    //String to hold the message the user wants to encrypt
    private String message;

    //String to hold the type of cypher the user wants to use
    private String cypher;

    //Create a scanner to get user input
    private Scanner scan = new Scanner(System.in);


    /**
     * Constructor for the menu class
     */
    public Menu(){

    }


    /**
     * Set up the menu class and begin functionality
     */
    public void initialise(){
        //Run the menu
        runMenu();
    }


    /**
     * Run menu method, showing the two menus + getting and implementing choices
     */
    private void runMenu(){

        //GET THE CYPHER TO USE
        getCypher();

        //ALLOW THE USER TO WORK WITH THE CYPHER
        useCypher();

    }


    /**
     * Show cypher menu method, displaying the options of cypher to the user
     */
    private void showCypherMenu(){
        System.out.println("Please select a cypher to use:");
        System.out.println("---------------------------------------------");
        System.out.println("C: Caesar Cypher");
        System.out.println("K: Keyed Caesar Cypher");
        System.out.println("V: Vigenére Cypher");
        System.out.println("---------------------------------------------");
    }


    /**
     * Show main menu method, displaying options of function to the user
     */
    private void showMainMenu(){
        System.out.println("Please select an option:");
        System.out.println("---------------------------------------------");
        System.out.println("T: Load a new text file (For encryption/decryption)");
        System.out.println("V: View the loaded text file");
        System.out.println("K: Edit the key");
        System.out.println("S: Show the key");
        System.out.println("E: Encrypt");
        System.out.println("D: Decrypt");
        System.out.println("C: Change cypher");
        System.out.println("Q: Quit");
        System.out.println("---------------------------------------------");
    }


    /**
     * Get cypher method, to let the user choose which cypher to use
     */
    private void getCypher(){
        //A string to keep the user's response in
        String response;
        //A boolean to check the input was put in correctly
        Boolean pass = false;



        do {
            //Show the cypher menu
            showCypherMenu();

            //Get the choice of cypher
            response = scan.nextLine().toUpperCase();

            //If it's valid input
            if(response.equals("C") || response.equals("K") || response.equals("V")){
                //Set the cypher to use as the chosen one
                cypher = response;
                //Set pass to true to exit the loop
                pass = true;

                //load the corresponding algorithm
                if(response.equals("C")){
                    caesarShift.initialise();
                }
                else if(response.equals("K")){
                    keyedShift.initialise();
                }
                else{
                    vigenereShift.initialise();
                }
            }
            else{
                //Show an error message to the user
                System.err.println("Invalid choice, please try again");
                System.out.println("");
            }
        }while(!pass);
    }


    /**
     * Use Cypher method, to let the user interact with the chosen cypher
     */
    private void useCypher(){
        //A string to keep the user's response in
        String response;
        //A boolean to check the input was put in correctly
        Boolean end = false;


        do{
            //Show the main menu
            showMainMenu();

            //Get the choice
            response = scan.nextLine().toUpperCase();

            //Deal with the response
            switch(response){
                case "T":
                    System.out.println("Load new text file");
                    getFile();
                    break;

                case "V":
                    System.out.println("View text to work with");
                    showFile();
                    break;

                case "K":
                    System.out.println("Edit the key");
                    editKey();
                    break;

                case "S":
                    System.out.println("Show the key");
                    showKey();
                    break;

                case "E":
                    System.out.println("Encrypt");
                    encrypt();
                    break;

                case "D":
                    System.out.println("Decrypt");
                    decrypt();
                    break;

                case "C":
                    System.out.println("Change cypher");
                    runMenu();
                    break;

                case "Q":
                    System.out.println("Quit");
                    end = true;
                    break;

                default:
                    System.err.println("Invalid choice, please try again");
                    break;
            }

            //Empty space for more aesthetic appearance
            System.out.println("");

        }while(!end);
    }


    /**
     * Edit Key class to change the value of the stored key
     */
    private void editKey(){
        String newStringKey;
        int newIntKey;

        System.out.println("Change key to?");


        //For a caesar cypher
        if(cypher.equals("C")){
            //Give a reminder of the key expected
            System.out.println("(Key must be an integer)");

            //Try to get the desired key
            try {
                //Get the desired integer
                newIntKey = scan.nextInt();

                //Set the integer as the key for the caesar cypher
                caesarShift.setKey(newIntKey);

                //Save the key into the file
                try (FileWriter fw = new FileWriter("caesar-key.txt");
                     BufferedWriter bw = new BufferedWriter(fw);
                     PrintWriter outfile = new PrintWriter(bw);) {

                    outfile.println(newIntKey);
                }

            }
            //Show an error if the key wasn't what was expected
            catch(Exception e){
                System.err.println("Error, please try again");
            }

            //Reload the shifted alphabet
            caesarShift.reloadAlphabet();
        }


        //For a keyed cypher
        else if(cypher.equals("K")){
            //Give a reminder of the key expected
            System.out.println("(First key must be an integer)");

            //Try to get the desired key
            try{
                //Get an integer
                newIntKey = scan.nextInt();

                //Give a reminder of the second key expected
                System.out.println("(Second key must be a word)");

                //Clear scanner
                scan.nextLine();

                //Get the word
                newStringKey = scan.nextLine();

                //Set the integer as the key for the keyed cypher
                keyedShift.setKey(newIntKey);

                //Set the string as the second key for the keyed cypher
                keyedShift.setKeyWord(newStringKey);

                //Save the keys into the file
                try (FileWriter fw = new FileWriter("keyed-caesar-key.txt");
                     BufferedWriter bw = new BufferedWriter(fw);
                     PrintWriter outfile = new PrintWriter(bw);) {

                    outfile.println(newIntKey);
                    outfile.println(newStringKey);
                }

            }
            //Show an error if the key wasn't what was expected
            catch(Exception e){
                System.err.println("Invalid input, please try again");
            }

            //Reload the shifted alphabet
            keyedShift.reloadAlphabet();
        }


        //For a vigenére cypher
        else{
            //Give a reminder of the key expected
            System.out.println("(Key must be a word)");

            //Try to get the desired key
            try{
                //Get a key word
                newStringKey = scan.nextLine();

                //Don't do anything just yet, not implemented
                vigenereShift.setKeyWord(newStringKey);

                //Save the key into the file
                try (FileWriter fw = new FileWriter("vigenere-key.txt");
                     BufferedWriter bw = new BufferedWriter(fw);
                     PrintWriter outfile = new PrintWriter(bw);) {

                    outfile.println("");
                    outfile.println(newStringKey);
                }

            }
            //Show an error if the key wasn't what was expected
            catch(Exception e){
                System.err.println("Invalid input, please try again");
            }
        }

    }



    /**
     * Show key method, to show the user what the key is currently loaded as
     */
    private void showKey(){
        System.out.print("Key currently loaded as: ");

        if(cypher.equals("C")){
            System.out.println(caesarShift.getKey());
        }
        else if(cypher.equals("K")){
            System.out.println(keyedShift.getKey() + ", " + keyedShift.getKeyWord());
        }
        else{
            System.out.println(vigenereShift.getKeyWord());
        }
    }


    /**
     * Encrypt method, to begin the encryption of the user's file
     */
    private void encrypt(){
        //Check that a file is loaded
        if(message != null) {

                if (cypher.equals("C")) {
                    //Encrypt the file
                    caesarShift.encrypt(message);
                } else if (cypher.equals("K")) {
                    //Encrypt the file
                    keyedShift.encrypt(message);
                } else {
                    //Encrypt the file
                    vigenereShift.encrypt(message);
                }

        }
        else {
            System.err.println("Error, no message is loaded");
        }
    }


    /**
     * Get file method, to get the name of the file to be used and start it being read
     */
    private void getFile(){
        Scanner scan = new Scanner(System.in);
        String response;

        System.out.println("Name of file to use:");

        //Get the user's response
        response = scan.nextLine();

        //Set any previous messages to translate to empty
        message = "";

        //Try to read the file
        try{
            readTextFile(response);
        }
        catch(IOException e){
            System.err.println("Error when loading message, please try again");
        }

        //Get the file into correct form
        message = message.replaceAll("\\p{P}", "").replaceAll(" ", "").toUpperCase();

    }

    private void showFile(){
        System.out.println("Message is currently loaded as:");
        System.out.println(message);
    }


    /**
     * Read text file method, to take the name of a file and put all its contents into the file
     * variable
     * @param fileName
     * @throws IOException
     */
    private void readTextFile(String fileName) throws IOException {

        try (FileReader fr = new FileReader(fileName);
             BufferedReader br = new BufferedReader(fr);
             Scanner infile = new Scanner(br)) {

            // Use the delimiter pattern so that we don't have to clear end of line
            // characters after doing a nextInt or nextBoolean
            infile.useDelimiter("\r?\n|\r");

            //Add the next line onto the full file
            message = message + infile.nextLine();
        }
    }


    /**
     * Decrypt method, to begin the decryption of the user's file
     */
    private void decrypt(){
        //Check that a file is loaded
        if(message != null) {
            if (cypher.equals("C")) {
                caesarShift.decrypt(message);
            } else if (cypher.equals("K")) {
                keyedShift.decrypt(message);
            } else {
                vigenereShift.decrypt(message);
            }
        }
        else {
            System.err.println("Error, no file is loaded");
        }
    }



}
