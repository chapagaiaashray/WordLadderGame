import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class DictionaryTest {

    private Dictionary dictionary;

    @BeforeEach
    public void setUp() {
        dictionary = new Dictionary("word_ladder_words.txt");
    }

    @Test
    public void testIsValidWordReturnsTrue() {
        assertTrue(dictionary.isValidWord("able"));
        assertTrue(dictionary.isValidWord("cold"));
        assertTrue(dictionary.isValidWord("dirt"));
    }

    @Test
    public void testIsValidWordIsCaseInsensitive() {
        assertTrue(dictionary.isValidWord("Able"));
        assertTrue(dictionary.isValidWord("COLD"));
    }

    @Test
    public void testIsValidWordReturnsFalse() {
        assertFalse(dictionary.isValidWord("zebra"));
        assertFalse(dictionary.isValidWord("aaaa"));
        assertFalse(dictionary.isValidWord(""));     // empty
        assertFalse(dictionary.isValidWord("fact.")); // punctuation
    }

    @Test
    public void testGetAllWords() {
        Set<String> allWords = dictionary.getAllWords();
        assertNotNull(allWords);
        assertTrue(allWords.size() >= 100);
        assertTrue(allWords.contains("chat"));
        assertFalse(allWords.contains("giraffe"));
    }
}