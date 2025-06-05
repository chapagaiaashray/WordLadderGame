import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.*;

public class PlayerTest {

    private Dictionary dictionary;

    @BeforeEach
    public void setUp() {
        dictionary = new Dictionary("word_ladder_words.txt");
    }

    @Test
    public void testInitialCurrentWord() {
        Player player = new Player("cold", dictionary);
        assertEquals("cold", player.getCurrentWord());
    }

    @Test
    public void testMakeValidMove() {
        Player player = new Player("cold", dictionary);

        // Assume "cord" is one letter different and in the dictionary
        boolean moved = player.makeMove("cord");
        assertTrue(moved);
        assertEquals("cord", player.getCurrentWord());
    }

    @Test
    public void testMakeInvalidMove() {
        Player player = new Player("cold", dictionary);

        // Assume "core" is NOT one letter away from "cold"
        boolean moved = player.makeMove("core");
        assertFalse(moved);
        assertEquals("cold", player.getCurrentWord());
    }

    @Test
    public void testRepeatedMoveIsNotAllowed() {
        Player player = new Player("cold", dictionary);

        assertTrue(player.makeMove("cord"));  // valid first move
        assertFalse(player.makeMove("cold")); // cold already visited
    }

    @Test
    public void testHasReachedGoal() {
        Player player = new Player("cold", dictionary);
        assertFalse(player.hasReachedGoal("cord"));

        player.makeMove("cord");
        assertTrue(player.hasReachedGoal("cord"));
    }

    @Test
    public void testGetValidNeighbors() {
        Player player = new Player("cold", dictionary);
        Set<Word> neighbors = player.getValidNeighbors();

        assertTrue(neighbors.stream().anyMatch
                (w -> w.getWord().equals("cord")));
    }
}
