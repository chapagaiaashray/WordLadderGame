import java.awt.*;
import java.util.*;
import java.util.List;

import javax.sound.sampled.*;
import java.io.File;

public class Game {
    private String startWord;
    private String endWord;
    private String currentWord;

    private Player player;
    private Dictionary dictionary;
    private ArrayList<String> moveHistory;

    private WordLadderGUI gui;
    private String statusMessage = "";

    public Game(String dictionaryFile) {
        this.dictionary = new Dictionary(dictionaryFile);
        this.moveHistory = new ArrayList<>();
        this.gui = new WordLadderGUI();
    }

    /**
     * Plays a sound file using Java's audio system.
     *
     * @param filename            The name of the .wav file to play (must be in the working directory).
     * @param waitUntilFinished  If true, the method blocks until the sound has finished playing.
     */
    private void playSound(String filename, boolean waitUntilFinished) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));
            clip.start();

            if (waitUntilFinished) {
                while (clip.isRunning()) {
                    Thread.sleep(50);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Animates the transformation of one word into another by visually updating
     * the letters that change one at a time.
     *
     * @param oldWord The current word before the change.
     * @param newWord The word after a valid move is made.
     */
    private void animateWordChange(String oldWord, String newWord) {
        char[] oldChars = oldWord.toCharArray();
        char[] newChars = newWord.toCharArray();

        String userInput = "";
        Set<Word> neighborSet = new Word(currentWord, dictionary).getNeighbors();
        Set<String> neighborWords = new HashSet<>();
        for (Word w : neighborSet) {
            neighborWords.add(w.getWord());
        }

        for (int i = 0; i < oldChars.length; i++) {
            if (oldChars[i] != newChars[i]) {
                oldChars[i] = newChars[i];
                String updatedWord = new String(oldChars);
                gui.drawState(startWord, endWord, updatedWord,
                        moveHistory, neighborWords, userInput, statusMessage);
                StdDraw.pause(200);
            }
        }
    }

    /**
     * Processes the player's guess input during gameplay.
     * Handles special commands like "quit", "restart", and "give up", and
     * checks if the guessed word is a valid move. If valid, updates the game state,
     * animates the transition, and plays feedback sounds.
     *
     * @param guess The word guessed or command entered by the player.
     */
    private void processGuess(String guess) {
        guess = guess.trim().toLowerCase();

        // Handle button commands first
        if (guess.equals("quit")) {
            System.exit(0);
        }

        if (guess.equals("restart")) {
            int difficulty = showDifficultySelection();
            startGame(difficulty);  // restart newgame
            return;      // important to return
        }

        if (guess.equals("give up")) {
            List<String> solution = findShortestPath(startWord, endWord);
            if (solution == null) {
                statusMessage = "No path found.";
            } else {
                gui.drawSolutionPopup(solution);  // show the shortest path
            }

            // Wait until player presses a key to exit
            while (!StdDraw.hasNextKeyTyped()) {
                // just wait...
            }
            StdDraw.nextKeyTyped(); // consume key
            System.exit(0);
        }

        // Otherwise, treat as normal word guess
        if (player.makeMove(guess)) {
            animateWordChange(currentWord, guess);
            currentWord = guess;
            moveHistory.add(guess);

            if (isGameOver()) {
                // No valid move sound when game is won
                statusMessage = "🎉 You completed the Word Ladder!";
                // Victory sound will be handled separately after game over
            } else {
                statusMessage = "✅ Valid move!";
                playSound("valid.wav", false);
            }
        } else {
            statusMessage = "❌ Invalid move! Try again.";
            playSound("invalid.wav", false);
        }
    }

    /**
     * Displays the difficulty selection screen with options for Easy, Medium, and Hard modes.
     * Plays background music while the user decides. Upon a valid button click, stops the
     * background music, plays a transition sound, and returns the number of moves corresponding
     * to the selected difficulty level.
     *
     * @return an integer representing the number of moves for the selected difficulty:
     *         1–2 for Easy, 3–5 for Medium, or 6–8 for Hard.
     */
    private int showDifficultySelection() {
        try {
            Thread.sleep(300); // Small pause before reloading music
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Clip bgMusic = null;
        try {
            bgMusic = AudioSystem.getClip();
            bgMusic.open(AudioSystem.getAudioInputStream(new File("lobby.wav")));
            bgMusic.loop(Clip.LOOP_CONTINUOUSLY); // Background music while choosing
        } catch (Exception e) {
            e.printStackTrace();
        }

        StdDraw.clear();
        StdDraw.picture(0.5, 0.5, "background.png", 1.0, 1.0);

        StdDraw.setFont(new Font("Arial", Font.BOLD, 26));
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(0.5, 0.8, "Word Ladder Game");
        StdDraw.text(0.5, 0.70, "Select Difficulty");

        // Easy Button
        StdDraw.setPenColor(Color.LIGHT_GRAY);
        StdDraw.filledRectangle(0.5, 0.6, 0.15, 0.05);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.rectangle(0.5, 0.6, 0.15, 0.05);
        StdDraw.text(0.5, 0.6, "Easy Mode");

        // Medium Button
        StdDraw.setPenColor(Color.LIGHT_GRAY);
        StdDraw.filledRectangle(0.5, 0.45, 0.15, 0.05);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.rectangle(0.5, 0.45, 0.15, 0.05);
        StdDraw.text(0.5, 0.45, "Medium Mode");

        // Hard Button
        StdDraw.setPenColor(Color.LIGHT_GRAY);
        StdDraw.filledRectangle(0.5, 0.3, 0.15, 0.05);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.rectangle(0.5, 0.3, 0.15, 0.05);
        StdDraw.text(0.5, 0.3, "Hard Mode");

        StdDraw.show();

        // Wait for a valid mouse click
        while (true) {
            if (StdDraw.isMousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();

                if (insideButton(x, y, 0.5, 0.6, 0.15, 0.05)) {
                    if (bgMusic != null) {
                        bgMusic.stop();
                        bgMusic.close();
                    }
                    playSound("starting.wav", false);
                    return new Random().nextInt(2) + 1; // Easy: 1–2 moves
                } else if (insideButton(x, y, 0.5, 0.45, 0.15, 0.05)) {
                    if (bgMusic != null) {
                        bgMusic.stop();
                        bgMusic.close();
                    }
                    playSound("starting.wav", false);
                    return new Random().nextInt(3) + 3; // Medium: 3–5 moves
                } else if (insideButton(x, y, 0.5, 0.3, 0.15, 0.05)) {
                    if (bgMusic != null) {
                        bgMusic.stop();
                        bgMusic.close();
                    }
                    playSound("starting.wav", false);
                    return new Random().nextInt(3) + 6; // Hard: 6–8 moves
                }
                // else: clicked outside any button => do nothing
            }
        }
    }

    /**
     * Starts a new game with a word ladder path of the specified length.
     * Displays the instructions popup, then attempts to randomly generate
     * a valid start and end word pair with the desired number of steps between them.
     * If a valid path cannot be formed, the process retries until successful.
     *
     * @param desiredMoves the number of transitions (word steps) between the start and end word.
     */
    public void startGame(int desiredMoves) {
        gui.drawInstructionsPopup();
        while (!StdDraw.hasNextKeyTyped()) {}
        StdDraw.nextKeyTyped(); // consume

        List<String> candidates = getWordsOfSameLength(dictionary.getAllWords(),
                4);
        if (candidates.size() < desiredMoves + 1) {
            System.out.println("Not enough words.");
            return;
        }

        Random random = new Random();

        while (true) { // keep trying until path of correct difficulty
            startWord = candidates.get(random.nextInt(candidates.size()));
            Word current = new Word(startWord, dictionary);

            Set<String> used = new HashSet<>();
            used.add(startWord);

            List<String> buildPath = new ArrayList<>();
            buildPath.add(startWord);

            boolean success = true;

            for (int i = 0; i < desiredMoves; i++) {
                Set<Word> neighbors = current.getNeighbors();
                List<Word> options = new ArrayList<>();
                for (Word w : neighbors) {
                    if (!used.contains(w.getWord())) {
                        options.add(w);
                    }
                }
                if (options.isEmpty()) {
                    success = false; // Failed to build enough moves
                    break;
                }
                current = options.get(random.nextInt(options.size()));
                used.add(current.getWord());
                buildPath.add(current.getWord());
            }

            endWord = current.getWord();

            if (success && !startWord.equals(endWord)) {
                // we have a good path
                break;
            }
            // Otherwise retry new startWord
        }

        // Now we have good startWord and endWord
        this.player = new Player(startWord, dictionary);
        this.currentWord = startWord;
        moveHistory = new ArrayList<>();
        moveHistory.add(startWord);

        playGameLoop(); // Play the actual game after setup
    }

    /**
     * Main game loop responsible for rendering the game state,
     * handling player input, and checking for win conditions.
     * Includes support for mouse interactions with control buttons,
     * real-time keyboard input for guesses, and game restart or exit.
     */
    private void playGameLoop() {
        String userInput = "";
        boolean needsRedraw = true;

        while (!isGameOver()) {
            if (needsRedraw) {
                Set<Word> neighborSet = new Word(currentWord, dictionary).
                        getNeighbors();
                Set<String> neighborWords = new HashSet<>();
                for (Word w : neighborSet) {
                    neighborWords.add(w.getWord());
                }
                gui.drawState(startWord, endWord, currentWord,
                        moveHistory, neighborWords, userInput, statusMessage);
                needsRedraw = false;
            }

            if (StdDraw.isMousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();

                if (insideButton(x, y, 0.3, 0.10,
                        0.08, 0.035)) {
                    int difficulty = showDifficultySelection();
                    startGame(difficulty);
                    return;
                }

                if (insideButton(x, y, 0.5, 0.10,
                        0.08, 0.035)) {
                    List<String> solution = findShortestPath(startWord, endWord);
                    if (solution == null) {
                        statusMessage = "No path found.";
                        needsRedraw = true;
                    } else {
                        gui.drawSolutionPopup(solution);
                        while (!StdDraw.hasNextKeyTyped()) {}
                        StdDraw.nextKeyTyped();
                        System.exit(0);
                    }
                }

                if (insideButton(x, y, 0.7, 0.10,
                        0.08, 0.035)) {
                    System.exit(0);
                }
            }

            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();

                if (key == '?') {
                    gui.drawInstructionsPopup();
                    while (!StdDraw.hasNextKeyTyped()) {}
                    StdDraw.nextKeyTyped();
                    needsRedraw = true;
                    continue;
                }

                if (Character.isLetter(key)) {
                    userInput += Character.toLowerCase(key);
                    needsRedraw = true;
                } else if (key == '\b' && userInput.length() > 0) {
                    userInput = userInput.substring(0, userInput.length() - 1);
                    needsRedraw = true;
                } else if (key == '\n') {
                    processGuess(userInput);
                    userInput = "";
                    needsRedraw = true;
                }
            }
        }

        // Victory
        gui.drawVictoryScreen(startWord, endWord, moveHistory);
        playSound("victory.wav", true);

        // Play again or quit
        StdDraw.setFont(new Font("Arial", Font.BOLD, 18));
        StdDraw.setPenColor(Color.LIGHT_GRAY);
        StdDraw.filledRectangle(0.35, 0.1, 0.1, 0.04);
        StdDraw.filledRectangle(0.65, 0.1, 0.1, 0.04);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.rectangle(0.35, 0.1, 0.1, 0.04);
        StdDraw.text(0.35, 0.1, "Play Again");
        StdDraw.rectangle(0.65, 0.1, 0.1, 0.04);
        StdDraw.text(0.65, 0.1, "Quit");

        StdDraw.show();

        while (true) {
            if (StdDraw.isMousePressed()) {
                double mx = StdDraw.mouseX();
                double my = StdDraw.mouseY();

                if (insideButton(mx, my, 0.35, 0.1,
                        0.1, 0.04)) {
                    int difficulty = showDifficultySelection();
                    startGame(difficulty);
                    return;
                } else if (insideButton(mx, my, 0.65,
                        0.1, 0.1, 0.04)) {
                    System.exit(0);
                }
            }
        }
    }

    /**
     * Determines whether a point (x, y) lies within a rectangular button.
     *
     * @param x          the x-coordinate of the point to check
     * @param y          the y-coordinate of the point to check
     * @param centerX    the x-coordinate of the button's center
     * @param centerY    the y-coordinate of the button's center
     * @param halfWidth  half the width of the button
     * @param halfHeight half the height of the button
     * @return true if the point is inside the button's bounds, false otherwise
     */
    private boolean insideButton(double x, double y, double centerX,
                                 double centerY, double halfWidth,
                                 double halfHeight) {
        return (x >= centerX - halfWidth && x <= centerX + halfWidth &&
                y >= centerY - halfHeight && y <= centerY + halfHeight);
    }

    public boolean isGameOver() {
        return currentWord.equals(endWord);
    }

    /**
     * Finds the shortest sequence of valid words from the start word to the end word,
     * where each word in the sequence differs by exactly one letter from the previous word.
     * Uses a breadth-first search (BFS) to ensure the shortest path is found.
     *
     * @param start the starting word
     * @param end   the target word
     * @return a list of words representing the shortest path from start to end,
     *         or {@code null} if no such path exists
     */
    public List<String> findShortestPath(String start, String end) {
        Queue<List<Word>> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Word startW = new Word(start, dictionary);
        Word endW = new Word(end, dictionary);

        List<Word> firstPath = new ArrayList<>();
        firstPath.add(startW);
        queue.add(firstPath);
        visited.add(start);

        while (!queue.isEmpty()) {
            List<Word> path = queue.poll();
            Word last = path.get(path.size() - 1);

            if (last.equals(endW)) {
                List<String> result = new ArrayList<>();
                for (Word w : path) {
                    result.add(w.getWord());
                }
                return result;
            }

            for (Word neighbor : last.getNeighbors()) {
                if (!visited.contains(neighbor.getWord())) {
                    visited.add(neighbor.getWord());
                    List<Word> newPath = new ArrayList<>(path);
                    newPath.add(neighbor);
                    queue.add(newPath);
                }
            }
        }
        return null;
    }

    /**
     * Filters and returns a list of words from the given set that have the specified length.
     *
     * @param allWords the set of all available words
     * @param length   the desired word length
     * @return a list containing only the words from {@code allWords} that are exactly {@code length} characters long
     */
    public List<String> getWordsOfSameLength(Set<String>
                                                     allWords, int length) {
        List<String> result = new ArrayList<>();
        for (String word : allWords) {
            if (word.length() == length) {
                result.add(word);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Game game = new Game("word_ladder_words.txt");

        while (true) { // So they can replay from beginning
            int difficulty = game.showDifficultySelection();
            game.startGame(difficulty);
        }
    }
}