import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class KeyedCaesar extends Caesar{
    private int key;
    private String keyWord;
    private String fileName = "keyed-caesar-key.txt";



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
     * Get key method, returns the numerical key
     * @return
     */
    public int getKey() { return key;}

    /**
     * Set key method, sets the key as the numerical value k
     * @param k
     */
    public void setKey(int k){key = k;}



    /**
     * Initialise method, to set up the keyed Caesar cypher
     */
    public void initialise(){

        //Load the keys
        try{
            keyWord = loadTextKey(fileName);
            key = super.loadKey(fileName);
        }
        catch(IOException e){
            System.err.println("Error while loading Keyed Caesar Key file");
        }

        //Remove repeats from the word
        keyWord = removeRepeats(keyWord);

        //Create the alphabet to use
        super.shiftedAlphabet = createAlphabet();

    }


    /**
     * Load text key method, to load the key word part of the keyed cypher key
     * @throws IOException
     */
    protected String loadTextKey(String file) throws IOException {

        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr);
             Scanner infile = new Scanner(br)) {

            // Use the delimiter pattern so that we don't have to clear end of line
            // characters after doing a nextInt or nextBoolean
            infile.useDelimiter("\r?\n|\r");

            //Move along from the numerical key found by original Caesar
            infile.nextLine();

            //Get the word
            return infile.nextLine().toUpperCase();
        }
    }


    /**
     * Remove repeats method, to remove the repeating letters from the key word
     */
    protected String removeRepeats(String kW){
        char[] wordArray = kW.toCharArray();
        Boolean repeat = false;
        String noReps = "";

        //Loop through the whole array, counting up the number of repeats
        for(int i = 0; i < wordArray.length; i++){
            //Loop through the array again, checking this letter with all the others except itself
            for(int j = 0; j < wordArray.length; j++){
                //If they're equal and it's not itself
                if((wordArray[i] == wordArray[j]) && i != j){
                    //Indicate that there is a repeat
                    repeat = true;
                    //Break out of the loop for this letter since we know it repeats
                    break;
                }
            }

            //If it doesn't repeat
            if(!repeat){
                noReps = noReps + wordArray[i];
            }

            //If it does, check if the letter already exists in the string
            else{
                //If the string doesn't contain that letter
                if(!noReps.contains(String.valueOf(wordArray[i]))){
                    //Add the letter to the string anyway
                    noReps = noReps + wordArray[i];
                }
            }

            repeat = false;

        }

        //Set keyWord as the version without repeats
        return noReps;

    }


    /**
     * Create alphabet method, to create the new alphabet with the key word at the beginning
     */
    private char[] createAlphabet(){
        //Create a char array of the key word
        char[] wordArray = keyWord.toCharArray();

        //Create a temp array to store the new array in
        char[] tempArray = new char[26];
        char[] doneArray = new char[26];


        //Remove the key word letters from the alphabet
        //For the whole alphabet
        for(int i = 0; i < 26; i++){
            //Go through the key word to find a match
            for(int j = 0; j < wordArray.length; j++){
                //If this alphabet character == a word in the keyword
                if(super.alphabet[i] == wordArray[j]){
                    //Set the character to a default character
                    super.shiftedAlphabet[i] = '.';
                    //Break out of the loop early, move onto next char
                    break;
                }
            }
        }


        //set j to 0 to keep track of where we are in the normal alphabet
        int j = 0;

        //Loop through the whole alphabet, using i for position
        for(int i = 0; i < 26; i++){

            /* While i is less than the length of the key word, put the
            Key word down */
            if(i < wordArray.length){
                tempArray[i] = wordArray[i];
            }


            //If this char isn't in the key word
            else if (super.shiftedAlphabet[j] != '.'){

                //Set it as the next part of the keyed Alphabet
                tempArray[i] = super.alphabet[j];
                //And increment j to the next part of the alphabet

                j++;
            }
            else{
                //Decrement i as nothing was added this loop
                i--;

                j++;
            }

            /* If it's not key word and the letter isn't in the alphabet,
            do nothing but increment the j for the alphabet*/

        }

        //Create an index to store the shift amount
        int index;

        //Shift the alphabet left
        for(int i = 0; i < 26; i++){
            index = i - key;

            //If index has gone negative, wrap around
            if(index < 0){
                index = 26 + index;
            }
            //super.shiftedAlphabet[index] = tempArray[i];
            doneArray[index] = tempArray[i];
        }

        return doneArray;

    }


    private void showAlphabet(){
        String show = "";
        for(int i = 0; i < 26; i++){
            show = show + alphabet[i];
        }

        System.out.println(show);
    }










}
