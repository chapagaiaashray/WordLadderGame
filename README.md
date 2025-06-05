# Word Ladder Game 

Welcome to the Word Ladder Game!  
Transform a start word into an end word by changing **one letter at a time**, forming valid intermediate words each step of the way. Built in Java with a custom GUI and engaging sound effects, this game is designed to be fun, responsive, and visually appealing.

---

## Features

- Difficulty selection screen (Easy, Medium, Hard) with background music
- Word transformations with real-time animated letter changes
- Sound effects for valid moves, invalid inputs, game start, and victory
- Clean and readable GUI using StdDraw
- Replayability through restart and solution preview
- Intelligent word ladder generation based on desired move count
- Thoroughly unit tested with `JUnit`

---

## How to Play

1. Launch the program by running the `main()` method in `Game.java`.
2. Choose a difficulty mode from the menu:
    - **Easy**: 1–2 moves
    - **Medium**: 3–5 moves
    - **Hard**: 6–8 moves
3. Read the instructions shown.
4. Type guesses using your keyboard.
    - Press **Enter** to submit.
    - Press **Backspace** to delete.
5. Use the buttons:
    - **Restart** – Start a new game
    - **Give Up** – Show the shortest path
    - **Quit** – Exit the game

---

## Unit Tests

Unit testing is done using **JUnit** and covers:
- `findShortestPath()` for valid and invalid word pairs
- `getWordsOfSameLength()` for dictionary filtering  
  See `GameTest.java`, `DictionaryTest.java`, `PlayerTest.java`, `WordTest.java` for complete test cases.

---

## Files Included

- `Game.java` – Main game logic and GUI control
- `Player.java` – Player state tracking
- `Word.java` – Word representation with neighbor generation
- `Dictionary.java` – Word list loading and lookup
- `WordLadderGUI.java` – GUI rendering with StdDraw
- `GameTest.java` – JUnit tests for main functionality
- `word_ladder_words.txt` – Valid 4-letter word dictionary
- `lobby.wav`, `valid.wav`, `invalid.wav`, `victory.wav`, `starting.wav` – Game audio files
- `background.png` – Title screen background image
- `README.md` – Project overview and instructions

---

## Acknowledgements

- Background music and sound effects from [freesound.org](https://freesound.org)
- Dictionary sourced from [Enable word list](http://www.wordgamedictionary.com/enable/)
- StdDraw library from Princeton’s Intro to Programming in Java

---

## Style & Structure Notes

- All classes are **single-responsibility**.
- Method names and variables follow clear, descriptive naming conventions.
- Majority of GUI is separated from logic (`Game` vs `WordLadderGUI`)
- Fully commented codebase, with docstrings on all methods.

---

## __How to Run the Game__

To run this on your machine, please follow the detailed instructions in the [`howtorun.md`](howtorun.md) file. It includes:

- Java installation guidance
- Directory and asset setup
- `javac` and `java` command usage
- Classpath tips for __Windows__, __macOS__, and __Linux__
- How to run the `.jar` file if you prefer that
