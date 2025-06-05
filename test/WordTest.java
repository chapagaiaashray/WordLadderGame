import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class WordTest {

    private Dictionary dictionary;

    @BeforeEach
    public void setUp() {
        dictionary = new Dictionary("word_ladder_words.txt");
    }

    @Test
    public void testGetWordReturnsLowercase() {
        Word w = new Word("COLD", dictionary);
        assertEquals("cold", w.getWord());
    }

    @Test
    public void testGetNeighborsReturnsValidOneLetterWords() {
        Word word = new Word("cold", dictionary);
        Set<Word> neighbors = word.getNeighbors();

        assertTrue(neighbors.contains(new Word("cord", dictionary)));
        assertTrue(neighbors.contains(new Word("bold", dictionary)));
        assertTrue(neighbors.contains(new Word("gold", dictionary)));

        // Ensure it does not return the same word
        assertFalse(neighbors.contains(new Word("cold", dictionary)));
    }

    @Test
    public void testEqualsAndHashCode() {
        Word w1 = new Word("cool", dictionary);
        Word w2 = new Word("COOL", dictionary);
        Word w3 = new Word("fool", dictionary);

        assertEquals(w1, w2);
        assertNotEquals(w1, w3);

        assertEquals(w1.hashCode(), w2.hashCode());
        assertNotEquals(w1.hashCode(), w3.hashCode());
    }

    @Test
    public void testToStringReturnsWord() {
        Word word = new Word("Back", dictionary);
        assertEquals("back", word.toString());
    }
}
