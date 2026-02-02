import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.*;

public class Caesar {
    private int key;

    protected char[] alphabet = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T',
    'U','V','W','X','Y','Z'};

    protected char[] shiftedAlphabet = new char[26];

    private String done = "";
    private String fileName = "caesar-key.txt";


    /**
     * Get key method, returns the key
     * @return key
     */
    public int getKey(){
        return key;
    }


    /**
     * Set key method, sets the key as the variable k
     * @param k
     */
    public void setKey(int k){
        key = k;
    }


    /**
     * Initialise method, loads the caesar cypher for use
     */
    public void initialise(){
        try {
            key = loadKey(fileName);
        }
        catch (IOException e){
            System.err.println("Error while loading Caesar Key file");
        }

        shiftedAlphabet = shiftAlphabet(alphabet, key);

    }


    /**
     * Load key method, takes the value stored in Caesar-Key text file
     * and stores it in variable key
     * @throws IOException
     */
    protected int loadKey(String fileName) throws IOException {

        try (FileReader fr = new FileReader(fileName);
             BufferedReader br = new BufferedReader(fr);
             Scanner infile = new Scanner(br)) {

            // Use the delimiter pattern so that we don't have to clear end of line
            // characters after doing a nextInt or nextBoolean
            infile.useDelimiter("\r?\n|\r");

            return infile.nextInt();
        }
    }


    /**
     * Encrypt method, to encrypt the user's file found in the menu method.
     * Public, can be called from the Menu
     */
    public void encrypt(String file) {
        //Show the file to encrypt
        System.out.println("Message to encrypt is: " + file);

        //Maybe add some checking and confirmation here if there's time

        //Encrypt the message
        shiftMessage(file);

        //Show the encrypted message
        System.out.println("Encrypted message is: " + done);

        //Save the file if the user wants
        try {
            saveText(done);
        }
        catch(IOException e){
            System.err.println("Error encountered when saving file");
        }

        done = "";

    }


    protected void saveText(String file) throws IOException{
        //Ask user if they want to save
        System.out.println("");
        System.out.println("Save cypher text to file? (Y/N)");

        //Boolean pass to validate input
        Boolean pass = false;
        Scanner scan = new Scanner(System.in);
        String response;

        do {
            //Get the response
            response = scan.nextLine().toUpperCase();

            if(response.equals("Y") || response.equals("N")){
                pass = true;
            }
            else{
                System.err.println("Error, invalid choice, please try again");
            }

        }while(!pass);


        if(response.equals("Y")){
            //If they do want to save the text to a file
            System.out.println("Name of file to save to?");

            String name = scan.nextLine();

            try (FileWriter fw = new FileWriter(name);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter outfile = new PrintWriter(bw);) {

                outfile.println(file);
            }

            System.out.println("File saved successfully");
        }

    }


    /**
     * Shift message method, to take the message to encrypt and shift it according to the key
     */
    //FUCK KNOWS WHATS GOING ON HERE, THIS NEEDS FIXING FOR KEYED CAESAR CYPHER SPECIFICALLY
    private void shiftMessage(String file){
        //Create an array out of the message
        char[] fileArray = file.toCharArray();

        //Create a way to store the index
        int index = 0;

        //Repeat for the whole message
        for(int i = 0; i < fileArray.length; i++){

            //Find the letter
            for(int j = 0; j < alphabet.length; j++){
                if(alphabet[j] == fileArray[i]){
                    index = j;
                    break;
                }
            }

            //Get the corresponding item from the shifted array and
            //create the completed message in the done variable
            done = done + shiftedAlphabet[index];
        }
    }


    /**
     * Encrypt method, to encrypt the user's file found in the menu method.
     * Public, can be called from the Menu
     */
    public void decrypt(String file){
        //Show the file to encrypt
        System.out.println("Message to decrypt is: " + file);

        //Maybe add some checking and confirmation here if there's time

        //Encrypt the message
        unshiftMessage(file);

        //Show the encrypted message
        System.out.println("Decrypted message is: " + done);

        done = "";
    }


    /**
     * Unshift method, to take the encrypted message and shift it according to the key
     */
    private void unshiftMessage(String file){
        //Create an array out of the message
        char[] fileArray = file.toCharArray();

        //Create a way to store the index
        int index = 0;

        //Repeat for the whole message
        for(int i = 0; i < fileArray.length; i++){

            //Find the letter in the shifted alphabet
            for(int j = 0; j < alphabet.length; j++){
                if(shiftedAlphabet[j] == fileArray[i]){
                    index = j;
                    break;
                }
            }

            //Get the corresponding shifted item
            //Create the completed message in the done variable
            done = done + alphabet[index];
        }


    }


    /**
     * Shift alphabet method, to shift the alphabet by the given key
     */
    public char[] shiftAlphabet(char[] alpha, int k){
        //Create an index to create the indexes
        int index;

        //For the whole alphabet
        for(int i = 0; i < 26; i ++){
            index = i + k;

            //If the index has gone beyond z
            if(index > 25){
                index = index - 26;
            }

            shiftedAlphabet[i] = alpha[index];
        }

        return shiftedAlphabet;
    }

    public void reloadAlphabet(){
        shiftedAlphabet = shiftAlphabet(alphabet, key);
    }


    private void printArray(char[] array){
        for(int i = 0; i < 26; i++){
            System.out.print(array[i]);
        }
        System.out.println();
    }


}
