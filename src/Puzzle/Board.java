package Puzzle;

public class Board {
    private final int[][] board;

    private int emptyX;
    private int emptyY;

    private final int borderValue;

    public Board(int[][] board, int emptyX, int emptyY) {
        borderValue = board.length - 1;

        this.emptyX = emptyX;
        this.emptyY = emptyY;

        this.board = arrCopy(board);
    }

    public Board(int[][] board) {
        borderValue = board.length - 1;
        this.board = board;
        getEmptyPositions();

    }

    private void getEmptyPositions() {
        boolean got = false;
        int tempX = 0;
        int tempY = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    if (!got) {
                        tempX = j;
                        tempY = i;
                        got = true;
                    } else {
                        throw new IllegalArgumentException("You can have only one empty tile marked as 0");
                    }
                }
            }
        }
        if (!got) {
            throw new IllegalArgumentException("You should have at least one empty tile marked as 0");
        }
        emptyX = tempX;
        emptyY = tempY;
    }

    // Constructor for deepCopy
    public Board copy() {
        return new Board(board, emptyX, emptyY);
    }

    private int[][] arrCopy(final int[][] matrix) {
        int[][] A = new int[borderValue + 1][];
        for (int i = 0; i < borderValue + 1; i++) {
            A[i] = matrix[i].clone();
        }
        return A;
    }

    private void setEmptyX(final int x) {
        if (x < 0)
            throw new IllegalStateException("x cannot be lesser than border value");
        if (x > borderValue)
            throw new IllegalStateException("x cannot be greater than (border value-1)");
        this.emptyX = x;
//        System.out.printf("Position: (%d, %d)\n", emptyX, emptyY);
    }

    private void setEmptyY(final int y) {
        if (y < 0)
            throw new IllegalStateException("x cannot be lesser than border value");
        if (y > borderValue)
            throw new IllegalStateException("x cannot be greater than (border value-1)");
        this.emptyY = y;
//        System.out.printf("Position: (%d, %d)\n", emptyX, emptyY);
    }

    // Move empty block up
    private void moveUp() {
        final int tempVal = board[emptyY - 1][emptyX];
        board[emptyY][emptyX] = tempVal;
        board[emptyY - 1][emptyX] = 0;
        setEmptyY(emptyY - 1);
    }

    // Move empty block down
    private void moveDown() {
        final int tempVal = board[emptyY + 1][emptyX];
        board[emptyY][emptyX] = tempVal;
        board[emptyY + 1][emptyX] = 0;
        setEmptyY(emptyY + 1);
    }

    // Move empty block left
    private void moveLeft() {
        final int tempVal = board[emptyY][emptyX - 1];
        board[emptyY][emptyX] = tempVal;
        board[emptyY][emptyX - 1] = 0;
        setEmptyX(emptyX - 1);
    }

    // Move empty block right
    private void moveRight() {
        final int tempVal = board[emptyY][emptyX + 1];
        board[emptyY][emptyX] = tempVal;
        board[emptyY][emptyX + 1] = 0;
        setEmptyX(emptyX + 1);
    }

    // Driver to move in direction
    public void moveTile(final Directions direction) {
        switch (direction) {
            case Up -> moveUp();
            case Down -> moveDown();
            case Left -> moveLeft();
            case Right -> moveRight();
        }
    }

    // Gives Current state
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int[] temp : board) {
            for (int t : temp)
                s.append(" ").append(t);
            s.append("\n");
        }
        return s.toString();
    }

    public int numberOfMismatchedTiles(Board board) {
        int mmt = 0;
        for (int i = 0; i <= borderValue; i++) {
            for (int j = 0; j <= borderValue; j++) {
                if (this.board[i][j] != board.board[i][j])
                    ++mmt;
            }
        }
        return mmt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Are they the same object in memory?
        if (o == null || getClass() != o.getClass()) return false; // Is it a Board?
        Board board = (Board) o;
        return java.util.Arrays.deepEquals(this.board, board.board);
    }

    @Override
    public int hashCode() {
        return java.util.Arrays.deepHashCode(board);
    }

    /**
     * Checks if the empty tile can be moved up.
     * @return true if the move is valid, false otherwise.
     */
    public boolean canMoveUp() {
        return emptyY > 0;
    }

    /**
     * Checks if the empty tile can be moved down.
     * @return true if the move is valid, false otherwise.
     */
    public boolean canMoveDown() {
        return emptyY < borderValue;
    }

    /**
     * Checks if the empty tile can be moved left.
     * @return true if the move is valid, false otherwise.
     */
    public boolean canMoveLeft() {
        return emptyX > 0;
    }

    /**
     * Checks if the empty tile can be moved right.
     * @return true if the move is valid, false otherwise.
     */
    public boolean canMoveRight() {
        return emptyX < borderValue;
    }

    /**
     * Creates a new board state by moving the empty tile in the given direction.
     * This method is immutable: it does not change the current board.
     *
     * @param direction The direction to move the empty tile.
     * @return A new Board object representing the successor state, or null if the move is invalid.
     */
    public Board generateSuccessor(final Directions direction) {
        // First, check if the requested move is possible.
        switch (direction) {
            case Up:
                if (!canMoveUp()) return null;
                break;
            case Down:
                if (!canMoveDown()) return null;
                break;
            case Left:
                if (!canMoveLeft()) return null;
                break;
            case Right:
                if (!canMoveRight()) return null;
                break;
        }

        Board newBoard = this.copy();

        newBoard.moveTile(direction);

        return newBoard;
    }
}
