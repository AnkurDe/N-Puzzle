import Puzzle.Board;
import Puzzle.Directions;

import java.util.*;

public class Game {
    private final Board initialState;
    private final Board goalState;

    public Game(int[][] initialState, int[][] goalState) {
        if (initialState.length != initialState[0].length || goalState.length != goalState[0].length) {
            throw new IllegalArgumentException("Enter a square board");
        }

        if (goalState.length != initialState.length) {
            throw new IllegalArgumentException("Boards not of equal size");
        }

        this.initialState = new Board(initialState);
        this.goalState = new Board(goalState);

        System.out.println("Game initialised");
    }

    private static class Node implements Comparable<Node> {
        Board board;
        Node parent;
        int g; // cost from start
        int h; // heuristic estimate to goal

        public Node(Board board, Node parent, int g, int h) {
            this.board = board;
            this.parent = parent;
            this.g = g;
            this.h = h;
        }

        public int f() {
            return g + h;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.f(), other.f());
        }
    }

    public void solvePuzzle() {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        Set<Board> closedList = new HashSet<>();

        Node startNode = new Node(initialState, null, 0, initialState.numberOfMismatchedTiles(goalState));
        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();

            if (currentNode.board.equals(goalState)) {
                System.out.println("Solution Found!");
                printSolutionPath(currentNode);
                return;
            }

            closedList.add(currentNode.board);

            for (Directions direction : Directions.values()) {

                Board successorBoard = currentNode.board.generateSuccessor(direction);

                if (successorBoard == null || closedList.contains(successorBoard)) {
                    continue;
                }

                int g = currentNode.g + 1;
                int h = successorBoard.numberOfMismatchedTiles(goalState);
                Node successorNode = new Node(successorBoard, currentNode, g, h);

                openList.add(successorNode);
            }
        }

        System.out.println("No solution found.");
    }

    private void printSolutionPath(Node goalNode) {
        List<Board> path = new ArrayList<>();
        Node current = goalNode;
        while (current != null) {
            path.add(current.board);
            current = current.parent;
        }
        Collections.reverse(path);
        for (int i = 0; i < path.size(); i++) {
            System.out.println("--- Step " + i + " ---");
            System.out.println(path.get(i));
        }
        System.out.println("Goal reached!");
    }
}
