# Hex Game Simulator (Union-Find Based)

> Simulates randomized games of **Hex** using the Union-Find data structure to determine connectivity and winners.

---

## 📌 Overview

This Java project simulates the **Hex** board game across various board sizes and estimates Player 1's win rate based on random tile placement.

---

## 📁 File Structure

    ├── HexBoard.java         // Core game logic with Union-Find
    ├── HexBoardStats.java    // Game simulations and statistics
    ├── Stopwatch.java        // Simple timer class (assumed provided)
    ├── StdRandom.java        // Random number generator (assumed provided)
    └── README.md             // This file (Markdeep format)

---

## 🧠 Key Concepts

- **Union-Find (Disjoint Set Union)**:
    - Efficiently tracks connected tiles.
    - Weighted quick union with path compression is used.
- **Virtual Nodes**:
    - Represent board edges (top, bottom, left, right).
    - Help check for a connected path from one side to the opposite.
- **Random Game Simulation**:
    - Games are simulated with players randomly placing tiles in empty spots.
    - Win probabilities are estimated from repeated simulations.

---

## ▶️ How It Works

### 🟦 HexBoard.java

- Manages the hex grid and game logic.
- Players take turns placing tiles.
- Uses Union-Find to track connectivity.
- Virtual nodes simplify win checking:
    - Player 1 connects **top to bottom**
    - Player 2 connects **left to right**
- Exposes methods:
    - `setTile(row, col, player)`
    - `hasPlayer1Won()`
    - `hasPlayer2Won()`
    - `isOnWinningPath(row, col)`

### 🧮 HexBoardStats.java

- Simulates random games `T` times for each board size from `N0` to `N1`.
- Computes Player 1 win rates.
- Uses `Stopwatch.java` to time the simulations.

---

## 🧪 Running the Simulation

### 🔧 Compilation

    javac HexBoard.java HexBoardStats.java Stopwatch.java StdRandom.java

### ▶️ Execution

    java HexBoardStats N0 N1 T

- `N0` — Starting board size (e.g., 3)
- `N1` — Ending board size (e.g., 6)
- `T`  — Number of games to simulate per size

### 🖥️ Example Output

    Time for board size n: 3, Games: 1000, time is: 0.14
    Time for board size n: 4, Games: 1000, time is: 0.17
    Time for board size n: 5, Games: 1000, time is: 0.20
    N = 3, P1 = 0.391, P2 = 0.609
    N = 4, P1 = 0.473, P2 = 0.527
    N = 5, P1 = 0.513, P2 = 0.487

---

## 🧱 Sample Game Flow

- A board of size `n x n` is created.
- Player 1 and Player 2 alternate placing random tiles.
- Union-Find structure connects newly placed tiles to neighbors.
- Virtual node connections are checked to detect a win.
- Player 1's win count is tracked across simulations.

---

## 📚 Dependencies

- `Stopwatch.java` and `StdRandom.java` are assumed to be available.
    - Provided by [Princeton's StdLib](https://introcs.cs.princeton.edu/java/stdlib/)

---

## ⚠️ Notes

- This is a **randomized simulation** — no strategy or AI involved.
- Execution time increases with board size and simulation count.
- Win rates for Player 1 tend to increase with board size due to **first-move advantage**.

---

## ✅ Future Enhancements

- Add a graphical interface or command-line playable mode.
- Implement AI to replace random move generation.
- Visualize board states and winning paths.
- Support multiple game rules or strategies.
