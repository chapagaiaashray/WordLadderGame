import java.io.*;
import java.util.*;

public class Dictionary {

    private Set<String> words;

    public Dictionary (String filename){
        words = new HashSet<>();
        loadWordsFromFile(filename);
    }


    /**
     * Loads words from a file and adds them to the dictionary.
     * Words are trimmed and converted to lowercase before storage.
     *
     * @param filename the name of the file to read from
     */
    private void loadWordsFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String word;
            while ((word = br.readLine()) != null) {
                words.add(word.trim().toLowerCase());  // Add word to the set after trimming and converting to lowercase
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    /**
     * Checks whether a given word exists in the dictionary.
     *
     * @param word the word to check
     * @return true if the word is valid, false otherwise
     */
    public boolean isValidWord(String word) {
        return words.contains(word.toLowerCase());
    }

    /**
     * Retrieves the full set of words loaded into the dictionary.
     *
     * @return a Set of all valid dictionary words
     */
    public Set<String> getAllWords() {
        return words;
    }
}