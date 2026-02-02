import java.io.IOException;

public class Vigenere extends KeyedCaesar {
    private String keyWord;
    private String fileName = "vigenere-key.txt";

    private char[] alphabet = new char[26];
    private String done;

    /**
     * Get key word method, returns the key word
     * @return
     */
    public String getKeyWord(){
        return keyWord;
    }


    /**
     * Set key word method, sets the key as the variable kW
     * @param kW
     */
    public void setKeyWord(String kW){
        keyWord = kW;
    }



    /**
     * Initialise method, to set up the Vigenere cypher
     */
    public void initialise(){

        //Load the keys
        try{
            keyWord = super.loadTextKey(fileName);
        }
        catch(IOException e){
            System.err.println("Error while loading Keyed Caesar Key file");
        }

        //Remove repeats from the word
        keyWord = super.removeRepeats(keyWord);

    }

    /**
     * Encrypt method, to begin the encryption of the given file
     * @param file
     */
    public void encrypt(String file){
        //Show the file to encrypt
        System.out.println("Message to encrypt is: " + file);

        //Maybe add some checking and confirmation here if there's time

        //Show the encrypted message
        System.out.println("Encrypted message is: " );
        //Encrypt the message
        done = encryptMessage(file);
        System.out.println(done);

        //Save the message if they want
        try {
            super.saveText(done);
        }
        catch(IOException e){
            System.err.println("Error encountered when saving file");
        }

        done = "";
    }


    private String encryptMessage(String file){
        //Create an array of characters out of the key word
        char[] tempKey = keyWord.toCharArray();

        //Create an array of characters out of the file to encrypt
        char[] fileArray = file.toCharArray();

        //Create an array of size fileArray to store the repeating key word
        char[] keyArray = new char[fileArray.length];

        //Construct the Key Array array, using index to keep track of the repeating word
        int index = 0;
        for(int i = 0; i < fileArray.length; i++){
            //If the index has reached the end of the key length, reset to 0
            if(index == tempKey.length){
                index = 0;
            }
            //Set value
            keyArray[i] = tempKey[index];

            //Increment index
            index++;
        }

        //A variable to store the finished message in
        String done = "";

        //Variables to store the x and y co-ordinates of the cypher table
        int x = 0, y= 0;


        //For every letter in the message to encrypt
        for(int i = 0; i < fileArray.length; i++){

            //Get x co-ordinate using the key word ASCII value
            x = keyArray[i] - 65;

            //Get y co-ordinate the same way, using the message text
            y = fileArray[i] - 65;

            //Set the alphabet as the alphabet shifted the number indicated by x
            alphabet = super.shiftAlphabet(super.alphabet, x);

            //The value needed is at the y index of the shifted alphabet
            done = done + alphabet[y];
        }

        return done;
    }


    public void decrypt(String file){
        //Show the file to decrypt
        System.out.println("Message to decrypt is: " + file);

        //Maybe add some checking and confirmation here if there's time

        //Show the decrypted message
        System.out.println("Decrypted message is: " );
        //Decrypt the message
        System.out.println(decryptMessage(file));

        done = "";
    }


    private String decryptMessage(String file){
        //Create an array of characters out of the key word
        char[] tempKey = keyWord.toCharArray();

        //Create an array of characters out of the file to encrypt
        char[] fileArray = file.toCharArray();

        //Create an array of size fileArray to store the repeating key word
        char[] keyArray = new char[fileArray.length];

        //Construct the Key Array array, using index to keep track of the repeating word
        int index = 0;
        for(int i = 0; i < fileArray.length; i++){
            //If the index has reached the end of the key length, reset to 0
            if(index == tempKey.length){
                index = 0;
            }
            //Set value
            keyArray[i] = tempKey[index];

            //Increment index
            index++;
        }


        //A variable to store the finished message in
        String done = "";

        //A variable to store the shift value in
        int shift;
        //A variable to store the index of the decrypted character (Reset previous index)
        index = 0;

        //For every letter in the message to decrypt
        for(int i = 0; i < fileArray.length; i++){
            //Get the value the alphabet must be shifted by using ASCII
            shift = keyArray[i] - 65;

            //Shift the alphabet
            alphabet = super.shiftAlphabet(super.alphabet, shift);

            //Find the plain text in this new alphabet array
            for(int j = 0; j < alphabet.length; j++){
                if(alphabet[j] == fileArray[i]){
                    index = j;
                }
            }

            //Set the next element of Done variable as the normal alphabet value at given index
            done = done + super.alphabet[index];
        }

        //Return the finished decryption
        return done;
    }


    private void printArray(char[] array){
        for(int i = 0; i < array.length; i++){
            System.out.print(array[i]);
        }
        System.out.println();
    }


}
