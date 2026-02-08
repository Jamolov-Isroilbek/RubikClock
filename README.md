# Rubik's Clock

A Java Swing implementation of the classic Rubik's Clock puzzle. Click corner buttons to rotate groups of four adjacent clocks — solve it by setting all nine clocks back to 12.

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)

## How It Works

A 3×3 grid of clocks starts in a scrambled state (randomized via 20 simulated moves). Four buttons sit at the corners of the grid — each button increments the four adjacent clocks by one hour (wrapping from 12 → 1). The goal is to get all nine clocks back to 12.

The game tracks your move count and displays it when you solve the puzzle.

## Running

```bash
javac -d out src/rubikclock/*.java
java -cp out rubikclock.RubikClock
```

Requires Java 8+.

## Author

**Isroilbek Jamolov** — [GitHub](https://github.com/Jamolov-Isroilbek)
