import java.awt.*;
import java.util.*;
import java.util.List;

public class WordLadderGUI {
    public WordLadderGUI() {
        StdDraw.setCanvasSize(800, 600);
        StdDraw.enableDoubleBuffering();
    }

    /**
     * Draws the main game screen, including start/target words, current word,
     * user input field, move history, valid neighbors, status message, and buttons.
     *
     * @param startWord      the starting word of the ladder
     * @param endWord        the target word to reach
     * @param currentWord    the player's current word
     * @param history        the list of words guessed so far
     * @param neighbors      the set of valid one-letter-different neighbors
     * @param userInput      the current input typed by the user
     * @param statusMessage  a message indicating game status (valid/invalid move, win, etc.)
     */
    public void drawState(String startWord, String endWord, String currentWord,
                          List<String> history, Set<String> neighbors,
                          String userInput, String statusMessage) {

        // Background
        StdDraw.clear();
        StdDraw.picture(0.5, 0.5, "background.png", 1.0, 1.0);

        // Title
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setFont(new Font("Serif", Font.BOLD, 28));
        StdDraw.text(0.5, 0.95, "WORD LADDER");

        // Start and Target Words
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 18));
        StdDraw.textLeft(0.25, 0.85, "Start Word: " + startWord.toUpperCase());
        StdDraw.textRight(0.75, 0.85, "Target Word: " + endWord.toUpperCase());

        // Current Word Display
        double boxY = 0.78;
        double boxX = 0.35;
        StdDraw.setFont(new Font("Arial", Font.BOLD, 24));
        for (int i = 0; i < currentWord.length(); i++) {
            StdDraw.square(boxX + i * 0.06, boxY, 0.025);
            StdDraw.text(boxX + i * 0.06, boxY, String.valueOf(currentWord.charAt(i)).toUpperCase());
        }

        // Typing Guess Field (4 boxes)
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 16));
        StdDraw.text(0.5, 0.65, "Type your guess below:");

        double inputY = 0.6;
        double inputX = 0.38;
        for (int i = 0; i < 4; i++) {
            StdDraw.square(inputX + i * 0.06, inputY, 0.025);
            if (i < userInput.length()) {
                StdDraw.text(inputX + i * 0.06, inputY, String.valueOf(userInput.charAt(i)).toUpperCase());
            }
        }

        // Move History
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 14));
        StdDraw.textLeft(0.05, 0.85, "Move History:");
        double moveY = 0.81;
        for (int i = 0; i < Math.min(10, history.size()); i++) {
            StdDraw.textLeft(0.05, moveY, history.get(i));
            moveY -= 0.035;
        }

        // Valid Neighbors
        StdDraw.textRight(0.95, 0.85, "Valid Neighbors:");
        double neighborY = 0.81;
        List<String> neighborList = new ArrayList<>(neighbors);
        for (int i = 0; i < Math.min(10, neighborList.size()); i++) {
            StdDraw.textRight(0.95, neighborY, neighborList.get(i));
            neighborY -= 0.035;
        }

        // Status Message
        StdDraw.setFont(new Font("Arial", Font.BOLD, 16));
        StdDraw.setPenColor(new Color(0, 102, 204));
        StdDraw.text(0.5, 0.15, statusMessage);

        // Instructions hint
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 12));
        StdDraw.setPenColor(Color.DARK_GRAY);
        StdDraw.text(0.5, 0.03, "Press '?' for Instructions");

        // BUTTONS
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 14));
        double buttonY = 0.10;
        double buttonWidth = 0.08;
        double buttonHeight = 0.035;

        // Restart Button
        StdDraw.setPenColor(Color.LIGHT_GRAY);
        StdDraw.filledRectangle(0.3, buttonY, buttonWidth, buttonHeight);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.rectangle(0.3, buttonY, buttonWidth, buttonHeight);
        StdDraw.text(0.3, buttonY, "Restart");

        // Give Up Button
        StdDraw.setPenColor(Color.LIGHT_GRAY);
        StdDraw.filledRectangle(0.5, buttonY, buttonWidth, buttonHeight);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.rectangle(0.5, buttonY, buttonWidth, buttonHeight);
        StdDraw.text(0.5, buttonY, "Give Up");

        // Quit Button
        StdDraw.setPenColor(Color.LIGHT_GRAY);
        StdDraw.filledRectangle(0.7, buttonY, buttonWidth, buttonHeight);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.rectangle(0.7, buttonY, buttonWidth, buttonHeight);
        StdDraw.text(0.7, buttonY, "Quit");

        StdDraw.show();
    }

    /**
     * Displays a popup with the shortest path found between the start and end word.
     * Used when the player gives up or requests a solution.
     *
     * @param path the list of words representing the shortest transformation path
     */
    public void drawSolutionPopup(List<String> path) {
        StdDraw.clear();
        StdDraw.setPenColor(new Color(255, 255, 240));
        StdDraw.filledRectangle(0.5, 0.5, 0.45, 0.45);

        StdDraw.setPenColor(Color.BLACK);
        StdDraw.rectangle(0.5, 0.5, 0.45, 0.45);

        StdDraw.setFont(new Font("Arial", Font.BOLD, 20));
        StdDraw.text(0.5, 0.85, "Shortest Path Found!");

        StdDraw.setFont(new Font("Arial", Font.PLAIN, 16));
        double y = 0.75;
        for (String word : path) {
            StdDraw.text(0.5, y, word);
            y -= 0.04;
        }

        StdDraw.setFont(new Font("Arial", Font.PLAIN, 12));
        StdDraw.setPenColor(Color.DARK_GRAY);
        StdDraw.text(0.5, 0.1, "Press any key to exit...");

        StdDraw.show();
    }


    /**
     * Draws a popup overlay for instructions.
     */
    public void drawInstructionsPopup() {
        StdDraw.setPenColor(new Color(0, 0, 0, 150));
        StdDraw.filledRectangle(0.5, 0.5, 0.5, 0.5);

        StdDraw.setPenColor(new Color(255, 255, 240));
        StdDraw.filledRectangle(0.5, 0.5, 0.4, 0.4);

        StdDraw.setPenColor(Color.BLACK);
        StdDraw.rectangle(0.5, 0.5, 0.4, 0.4);

        StdDraw.setFont(new Font("Arial", Font.BOLD, 18));
        StdDraw.text(0.5, 0.8, "📜 HOW TO PLAY 📜");

        StdDraw.setFont(new Font("Arial", Font.PLAIN, 14));
        double y = 0.75;
        double gap = 0.05;


        StdDraw.text(0.5, y -= gap, "Objective: Start ➔ Target by changing 1 letter.");
        StdDraw.text(0.5, y -= gap, "You’ll be given a random start word and target word. ");
        StdDraw.text(0.5, y -= gap, "Your goal is to reach the target word by " +
                "guessing one-letter-different valid words.");
        StdDraw.text(0.5, y -= gap, "No repeating previous words.");
        StdDraw.text(0.5, y -= gap, "At each turn, valid neighbor " +
                "words are displayed to help you.");
        StdDraw.text(0.5, y -= gap, "Press [ENTER] to submit.");
        StdDraw.text(0.5, y -= gap, "Use the buttons for Restart, Give Up, or Quit!");

        StdDraw.setFont(new Font("Arial", Font.PLAIN, 12));
        StdDraw.setPenColor(Color.DARK_GRAY);
        StdDraw.text(0.5, 0.18, "Press any key to close.");

        StdDraw.show();
    }

    /**
     * Displays the victory screen after the player successfully reaches the target word.
     * Shows the full path taken and number of moves.
     *
     * @param startWord the starting word of the game
     * @param endWord   the target word of the game
     * @param history   the list of words used in the path
     */
    public void drawVictoryScreen(String startWord, String endWord, List<String> history) {
        StdDraw.clear();
        StdDraw.picture(0.5, 0.5, "victory_image.png", 1.0, 1.0);

        StdDraw.setFont(new Font("Arial", Font.BOLD, 32));
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(0.5, 0.75, "🎉 Congratulations! 🎉");

        StdDraw.setFont(new Font("Arial", Font.PLAIN, 20));
        StdDraw.text(0.5, 0.7, "You successfully transformed:");
        StdDraw.text(0.5, 0.65, startWord.toUpperCase() + " ➡ " + endWord.toUpperCase());

        StdDraw.text(0.5, 0.55, "Total Moves: " + (history.size() - 1));

        StdDraw.setFont(new Font("Arial", Font.PLAIN, 16));
        double y = 0.45;
        StdDraw.text(0.5, y, "Path:");
        y -= 0.05;

        for (String move : history) {
            StdDraw.text(0.5, y, move);
            y -= 0.035;
        }

        StdDraw.show();
    }
}