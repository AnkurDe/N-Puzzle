import Puzzle.Board;
import Puzzle.Directions;

public class Main {
    public static void main(String[] args) {
        int[][] start = {
                {1, 2, 3},
                {4, 0, 5},
                {6, 7, 8}
        };

        int[][] finalState = {
                {1, 2, 3},
                {4, 0, 5},
                {6, 7, 8}
        };

        Board board = new Board(start, finalState);
        System.out.println(board);

        System.out.println("Moving down");
        board.moveTile(Directions.Down);
        System.out.println(board);
    }
}