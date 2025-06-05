# How to Run the Word Ladder Game

This guide will walk you through compiling and running the **Word Ladder Game** locally on your machine.

---

## Prerequisites

- Java JDK 17 or later installed  
- All required game files (`.java`, `.png`, `.wav`, `.txt`) in place  

---

## Directory Structure (Expected)

```
Final Project/
├── src/                  # All your .java source files
│   └── ...
├── assets/               # All game resources
│   ├── *.png             # Background images
│   ├── *.wav             # Sound effects
│   └── word_ladder_words.txt
├── out/                  
├── WordLadder.jar        
├── README.md
├── howtorun.md
└── .gitignore
└── stddraw.jar
```

---

## Compile the Game (If using source files)

Run this command **from the root project folder**:

```bash
javac -d out -cp "src" src/*.java
```

This compiles all `.java` files inside `src/` and places `.class` files into the `out/` directory.

---

## ▶️ Run the Game

### Option 1: Run Using `.class` Files (After Compiling)

```bash
java -cp out Main
```

### Option 2: Run the Pre-built `.jar` File

```bash
java -jar WordLadder.jar
```

---

## 🧩 Notes

- Make sure the `assets/` folder is in the same directory as your `.jar` or project root when running. The code loads files like `background.png`, `victory.wav`, and `word_ladder_words.txt` at runtime.
- If using IntelliJ or another IDE, make sure the working directory is set to the project root.
- If you encounter a sound or file-not-found error, double-check file paths and ensure case-sensitivity matches (especially on macOS/Linux).

---

Enjoy the game! 
