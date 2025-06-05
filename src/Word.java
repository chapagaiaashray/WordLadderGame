import java.util.*;

public class Word {
    private String word;
    private Dictionary dictionary;

    public Word(String word, Dictionary dictionary) {
        this.word = word.toLowerCase();
        this.dictionary = dictionary;
    }

    public String getWord() {
        return word;
    }

    /**
     * Generates all valid neighboring words that differ by exactly one letter.
     * <p>
     * A neighbor is defined as any word that:
     * <ul>
     *   <li>Has the same length as the current word</li>
     *   <li>Differs by exactly one character</li>
     *   <li>Exists in the dictionary</li>
     * </ul>
     * This method iterates over each character in the word, attempts to replace it
     * with every other letter from 'a' to 'z', and checks if the resulting word is valid.
     * Valid neighbors are returned as {@code Word} objects.
     *
     * @return A set of valid {@code Word} objects that are one letter different
     *         from the current word and exist in the dictionary.
     */
    public Set<Word> getNeighbors() {
        Set<Word> neighbors = new HashSet<>();
        char[] wordChars = word.toCharArray(); // Convert word to char array to manipulate each letter

        // Try changing each letter of the word to any other letter from 'a' to 'z'
        for (int i = 0; i < word.length(); i++) {
            char originalChar = wordChars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != originalChar) {  // Skip the same character
                    wordChars[i] = c;  // Change the letter
                    String newWord = new String(wordChars);
                    // Check if the new word is valid and add to the set of neighbors
                    if (dictionary.isValidWord(newWord)) {
                        neighbors.add(new Word(newWord, dictionary));  // Add valid neighbor
                    }
                }
            }
            wordChars[i] = originalChar;  // Restore original character
        }
        return neighbors;
    }

    /**
     * Checks whether this {@code Word} object is equal to another object.
     * <p>
     * Two {@code Word} objects are considered equal if:
     * <ul>
     *   <li>They are instances of the {@code Word} class</li>
     *   <li>Their internal string representations are equal (case-insensitive, as handled in constructor)</li>
     * </ul>
     * The {@code Dictionary} reference is intentionally ignored in the comparison,
     * so equality depends only on the word content itself.
     *
     * @param obj The object to compare with this {@code Word}.
     * @return {@code true} if the given object is a {@code Word} with the same word value,
     *         {@code false} otherwise.
     */
    public boolean equals (Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Word word1 = (Word) obj;
        return word.equals(word1.word);
    }

    public int hashCode() {
        return word.hashCode();
    }

    public String toString() {
        return word;
    }
}