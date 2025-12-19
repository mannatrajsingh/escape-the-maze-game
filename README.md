# AI-Based Maze Runner Game using A*

## Overview
This project is a grid-based maze game implemented in **Java**, where a player must escape a randomly generated maze while being chased by an AI-controlled enemy. The enemy uses the **A\*** pathfinding algorithm to dynamically pursue the player in real time.

The project demonstrates practical applications of **graph algorithms**, **pathfinding**, and **game logic**, while ensuring that all generated mazes are solvable.

---

## Key Features
- Grid-based maze environment
- Player-controlled movement using keyboard input
- Enemy AI that dynamically chases the player using **A\***
- Random maze generation with guaranteed solvability
- BFS-based connectivity checks to ensure valid paths
- Real-time game updates using a timer loop
- Clear win (escape) and lose (caught) conditions

---

## Algorithms Used
- **A\* Search Algorithm**  
  Used by the enemy to compute the shortest path to the player while avoiding obstacles.

- **Breadth-First Search (BFS)**  
  Used during maze generation to verify that a valid path exists from the player start to the exit.

---

## Game Mechanics
- **Player (Blue)**:  
  Controlled using arrow keys. The objective is to reach the exit tile.

- **Enemy (Red)**:  
  Automatically moves toward the player using A\* pathfinding.

- **Walls (Black)**:  
  Act as obstacles that neither the player nor the enemy can cross.

- **Exit (Green)**:  
  Reaching this tile results in a win.

---

## Controls
- `↑` Move Up  
- `↓` Move Down  
- `←` Move Left  
- `→` Move Right  

---

## Project Structure

## Project Structure

- **Main.java**
  - Entry point of the application
  - Initializes the game window and starts the game loop

- **GamePanel.java**
  - Handles core game logic and rendering
  - Manages player movement, enemy movement, and collision detection
  - Integrates A* pathfinding for enemy AI
  - Runs the real-time update loop using a timer

- **Grid.java**
  - Represents the maze as a 2D grid
  - Stores grid dimensions and validates cell positions
  - Acts as the shared environment for all entities

- **Cell.java**
  - Represents an individual cell in the grid
  - Stores properties such as wall status and coordinates
  - Used by BFS and A* during traversal

- **Player.java**
  - Represents the player entity
  - Stores the player’s current position
  - Updates position based on keyboard input

- **Enemy.java**
  - Represents the AI-controlled enemy
  - Stores the enemy’s current position
  - Movement is driven by A* pathfinding

- **AStar.java**
  - Implements the A* search algorithm
  - Computes the shortest path from the enemy to the player
  - Uses heuristics to efficiently navigate around obstacles

- **README.md**
  - Project documentation
  - Explains game mechanics, algorithms used, setup instructions, and design decisions




## How to Run
1. Ensure Java (JDK 17 or higher) is installed.
2. Compile all files:
   ```bash
   javac *.java
Run the game:
java Main
