## 🧩 N-Puzzle Problem Solver (Java Implementation)

### 📖 Introduction

The **N-Puzzle Problem** is a generalization of the classic **8-Puzzle Problem**, a well-known example of a state-space search problem in **Artificial Intelligence** and **Graph Theory**.  
It consists of an \( N x N \) grid containing numbered tiles and one empty space.  
The goal is to move the tiles by sliding them into the empty space until the configuration matches a predefined goal state.

From a **graph-theoretic** perspective:
- Each unique board configuration represents a **node (state)**.  
- Each legal tile movement represents an **edge (transition)** between states.  
- Solving the puzzle becomes equivalent to **finding the shortest path** from an initial node to the goal node.

This project implements a modular **Java-based N-Puzzle solver**, emphasizing low-level control, immutability, and algorithmic clarity rather than high abstraction.

---

### 🗁 File Structure

```
Puzzle/
│
├── Puzzle
|   ├──Board.java        # Core logic for board representation, moves, and successors
|   ├──Directions.java   # Enum representing valid movement directions (UP, DOWN, LEFT, RIGHT)
├── Game.java         # A* algorithm implementation and puzzle-solving logic
└── Main.java         # Entry point to run and test the 8-puzzle
```

---

### ⚙️ Class and Function Overview

#### **1️⃣ Board.java**
Represents the puzzle board as a 2D matrix and provides methods for manipulation and state evaluation. The class is designed to support any \( N x N \) board size, not just 3x3.

**Key Functions:**
- `Board(int[][] board, int emptyX, int emptyY)`  
  Initializes the board with a given configuration and the empty tile’s position.

- `Board(int[][] board)`  
  Automatically detects the empty tile and validates the input matrix.

- `copy()`  
  Creates a deep copy of the current board — used for generating successor states.

- `moveTile(Directions direction)`  
  Moves the empty tile in the specified direction if valid.

- `generateSuccessor(Directions direction)`  
  Returns a **new board state** after performing the move, preserving immutability.

- `canMoveUp()`, `canMoveDown()`, `canMoveLeft()`, `canMoveRight()`  
  Boolean checks to ensure move validity.

- `numberOfMismatchedTiles(Board goal)`  
  Heuristic that counts the number of misplaced tiles compared to the goal state.

- `equals()` and `hashCode()`  
  Overridden for use in hash-based data structures during graph traversal.

This class ensures scalability from **8-puzzle (3x3)** to **15-puzzle (4x4)** or even higher-dimensional variants.

---

#### **2️⃣ Directions.java**
A simple enumeration defining all possible movements of the empty tile:
```java
public enum Directions {
    Up, Down, Left, Right
}
```

Used for clarity, compile-time safety, and clean switch-based movement handling in `Board.java`.

---

#### **3️⃣ Game.java**
Implements the **A*** search algorithm to find the shortest sequence of moves from the initial state to the goal state.

**Key Highlights:**
- Uses **priority queues** to always expand the most promising node based on the cost function `f(n) = g(n) + h(n)`.
- `g(n)` = cost to reach the current node.  
  `h(n)` = heuristic estimate (number of misplaced tiles or Manhattan distance).
- Tracks visited states to prevent redundant exploration.
- Returns the optimal path of moves once the goal configuration is reached.

This class serves as the AI core of the project, demonstrating how **graph search algorithms** can efficiently solve variable-sized N-puzzle problems.

---

#### **4️⃣ Main.java**
Acts as the **program’s entry point**.  
It initializes the board, invokes the A* search from `Game.java`, and prints the resulting move sequence and statistics to the console.

You can modify this file to:
- Test different board sizes (e.g., 3x3, 4x4, 5x5)
- Set custom initial and goal configurations
- Compare heuristics and performance metrics

---

### 💡 Usage

1. **Compile the program:**
   ```bash
   javac Puzzle/*.java
   ```

2. **Run the program:**
   ```bash
   java Puzzle.Main
   ```

3. **Edit the initial and goal configurations** in `Main.java` to test different puzzle sizes and arrangements.

---

### 🧠 Philosophy and Design Choice

This project was implemented in **Java** — deliberately — to challenge the common belief that complex algorithmic problems like the N-puzzle are more feasible or convenient only in **highly abstracted languages** such as Python.

- Java offers **explicit control over memory, type safety, and object mutability**, crucial for understanding how search algorithms operate internally.
- This reinforces **fundamental CS concepts** such as:
  - State-space search  
  - Object-oriented modeling of problem states  
  - Graph traversal algorithms  
  - Heuristic evaluation and optimal pathfinding

By maintaining both **clarity** and **rigorous structure**, this project demonstrates that **Java remains a powerful tool for AI and algorithmic problem-solving** when used thoughtfully.

---

### 🧩 Graph Theory Representation

The N-puzzle problem can be viewed as a **graph traversal problem**:

```
(Node) 123456780   → represents a board configuration
(Edge)  Move Up / Move Left / Move Down / Move Right
```

Each move generates a new node (successor state), and the solution path is the shortest path from the **initial node** to the **goal node**.

```text
Initial State  →  Intermediate States  →  Goal State
     |                 |                     |
     ▼                 ▼                     ▼
   Node A ───► Node B ───► Node C ───► Node D
```

The A* algorithm ensures that the path chosen is **optimal**, by balancing both cost and heuristic information. This holds true for any \( N \times N \) puzzle configuration.

---

### 🚀 Future Enhancements
- Visualization of the A* search path and state transitions.  
- Option to choose between **Manhattan**, **Hamming**, and **Linear Conflict** heuristics.  
- **Graph visualization** of states using JavaFX or external libraries.  
- Integration of **performance metrics** (nodes expanded, time taken, branching factor).  
- Support for **random puzzle generation** and solvability checks.

---

### ✍️ Author
**Ankur De**  
B.Tech CSE (Artificial Intelligence)  
Amrita Vishwa Vidyapeetham