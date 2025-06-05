import java.util.*;

public class Player {

    private Word currentWord;
    private Dictionary dictionary;
    private Set<Word> visitedWords;

    public Player (String startWord, Dictionary dictionary) {
        this.dictionary = dictionary;
        this.currentWord = new Word(startWord, dictionary);
        this.visitedWords = new HashSet<>();
        this.visitedWords.add(this.currentWord);
    }

    public String getCurrentWord() {
        return currentWord.getWord();
    }

    /**
     * Attempts to move to the specified next word, if the move is valid.
     * <p>
     * A move is considered valid if:
     * <ul>
     *   <li>The {@code nextWord} differs from the current word by exactly one letter</li>
     *   <li>The {@code nextWord} exists in the dictionary</li>
     *   <li>The {@code nextWord} has not been visited before</li>
     * </ul>
     * If the move is valid, the current word is updated, and the word is added
     * to the set of visited words.
     *
     * @param nextWord The word to attempt to move to.
     * @return {@code true} if the move was valid and successful, {@code false} otherwise.
     */
    public boolean makeMove(String nextWord) {
        Set<Word> neighbors = currentWord.getNeighbors();

        Word nextWordObj = new Word(nextWord, dictionary);
        if (neighbors.contains(nextWordObj) && !visitedWords.contains(nextWordObj)) {
            this.currentWord = nextWordObj;
            visitedWords.add(nextWordObj);
            return true;
        }
        return false;
    }

    // Method to check if the player has reached the goal word
    public boolean hasReachedGoal(String goalWord) {
        return currentWord.getWord().equals(goalWord);
    }

    // Method to get all the valid neighbors of the current word
    public Set<Word> getValidNeighbors() {
        return currentWord.getNeighbors();
    }
}
