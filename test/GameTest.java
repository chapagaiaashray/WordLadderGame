import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.*;

public class GameTest {

    private Game game;
    private Dictionary dictionary;

    @BeforeEach
    public void setUp() {
        dictionary = new Dictionary("word_ladder_words.txt");
        game = new Game("word_ladder_words.txt");
    }

    @Test
    public void testFindShortestPathExists() {
        List<String> path = game.findShortestPath("cold", "cord");
        assertNotNull(path);
        assertEquals("cold", path.get(0));
        assertEquals("cord", path.get(path.size() - 1));
        assertTrue(path.size() <= 2); // cold → cord is 1 step
    }

    @Test
    public void testFindShortestPathReturnsNullIfNoPath() {
        List<String> path = game.findShortestPath("cold", "zzzz");
        assertNull(path); // assuming "zzzz" is not in dictionary
    }

    @Test
    public void testGetWordsOfSameLengthReturnsOnlyFourLetterWords() {
        Set<String> allWords = dictionary.getAllWords();
        List<String> result = game.getWordsOfSameLength(allWords, 4);
        assertNotNull(result);
        assertTrue(result.stream().allMatch(w -> w.length() == 4));
    }

    @Test
    public void testGeneratedPathMatchesDesiredDifficultyLength() {
        // simulate the path generation logic from startGame
        int desiredMoves = 4; // mimic Medium difficulty
        Set<String> allWords = dictionary.getAllWords();
        List<String> candidates = game.getWordsOfSameLength(allWords, 4);

        assertTrue(candidates.size() > desiredMoves + 1, "Not enough words to test.");

        boolean pathBuilt = false;
        for (int trial = 0; trial < 10; trial++) {
            String start = candidates.get(new Random().nextInt(candidates.size()));
            Word current = new Word(start, dictionary);
            Set<String> used = new HashSet<>();
            used.add(start);
            int steps = 0;

            for (int i = 0; i < desiredMoves; i++) {
                Set<Word> neighbors = current.getNeighbors();
                List<Word> options = new ArrayList<>();
                for (Word w : neighbors) {
                    if (!used.contains(w.getWord())) {
                        options.add(w);
                    }
                }
                if (options.isEmpty()) break;

                current = options.get(new Random().nextInt(options.size()));
                used.add(current.getWord());
                steps++;
            }

            if (steps == desiredMoves) {
                pathBuilt = true;
                break;
            }
        }

        assertTrue(pathBuilt, "Failed to construct a path of desired length after multiple attempts.");
    }
}
