package Puzzle;

/**
 * Represents an N x N sliding puzzle board for the 8-puzzle (or similar) problem.
 * The board stores an integer matrix where 0 represents the empty tile. The class
 * tracks the current empty tile position and provides operations to inspect and
 * generate successor states by moving the empty tile.
 *
 * Invariants:
 * - board is a square matrix (N x N).
 * - borderValue == board.length - 1.
 * - exactly one tile in the matrix equals 0 (the empty tile).
 *
 * Instances are mutable. Use copy() to obtain a deep copy before modifying
 * an existing Board if you need immutability semantics.
 */
public class Board {
    private final int[][] board;

    private int emptyX;
    private int emptyY;

    private final int borderValue;

    /**
     * Create a new Board with a deep copy of the provided board matrix and
     * the given empty tile coordinates.
     *
     * @param board  the N x N matrix representing tiles; 0 denotes the empty tile
     * @param emptyX the x (column) index of the empty tile (0-based)
     * @param emptyY the y (row) index of the empty tile (0-based)
     * @throws IllegalStateException if provided emptyX/emptyY are out of bounds
     */
    public Board(int[][] board, int emptyX, int emptyY) {
        borderValue = board.length - 1;

        this.emptyX = emptyX;
        this.emptyY = emptyY;

        this.board = arrCopy(board);
    }

    /**
     * Create a new Board that uses the provided matrix as-is and locates the
     * empty tile automatically. The matrix must contain exactly one zero.
     *
     * @param board the N x N matrix representing tiles; 0 denotes the empty tile
     * @throws IllegalArgumentException if matrix does not contain exactly one zero
     */
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

    /**
     * Constructor-like deep copy: returns a new Board instance with a deep copy
     * of internal state (matrix and empty coordinates).
     *
     * @return a new Board identical in state but independent of this instance.
     */
    public Board copy() {
        return new Board(board, emptyX, emptyY);
    }

    /**
     * Create and return a deep copy of the given 2D array.
     *
     * @param matrix the matrix to copy (must be square with size borderValue+1)
     * @return a new 2D array copy
     */
    private int[][] arrCopy(final int[][] matrix) {
        int[][] A = new int[borderValue + 1][];
        for (int i = 0; i < borderValue + 1; i++) {
            A[i] = matrix[i].clone();
        }
        return A;
    }

    /**
     * Set the empty tile's x (column) coordinate. Preconditions: 0 <= x <= borderValue.
     *
     * @param x new x coordinate
     * @throws IllegalStateException if x is out of range
     */
    private void setEmptyX(final int x) {
        if (x < 0)
            throw new IllegalStateException("x cannot be lesser than border value");
        if (x > borderValue)
            throw new IllegalStateException("x cannot be greater than (border value-1)");
        this.emptyX = x;
//        System.out.printf("Position: (%d, %d)\n", emptyX, emptyY);
    }

    /**
     * Set the empty tile's y (row) coordinate. Preconditions: 0 <= y <= borderValue.
     *
     * @param y new y coordinate
     * @throws IllegalStateException if y is out of range
     */
    private void setEmptyY(final int y) {
        if (y < 0)
            throw new IllegalStateException("x cannot be lesser than border value");
        if (y > borderValue)
            throw new IllegalStateException("x cannot be greater than (border value-1)");
        this.emptyY = y;
//        System.out.printf("Position: (%d, %d)\n", emptyX, emptyY);
    }

    /**
     * Swap the empty tile with the tile above it. Preconditions: emptyY > 0.
     * Updates internal matrix and empty tile coordinates.
     */
    private void moveUp() {
        final int tempVal = board[emptyY - 1][emptyX];
        board[emptyY][emptyX] = tempVal;
        board[emptyY - 1][emptyX] = 0;
        setEmptyY(emptyY - 1);
    }

    /**
     * Swap the empty tile with the tile below it. Preconditions: emptyY < borderValue.
     * Updates internal matrix and empty tile coordinates.
     */
    private void moveDown() {
        final int tempVal = board[emptyY + 1][emptyX];
        board[emptyY][emptyX] = tempVal;
        board[emptyY + 1][emptyX] = 0;
        setEmptyY(emptyY + 1);
    }

    /**
     * Swap the empty tile with the tile to the left. Preconditions: emptyX > 0.
     * Updates internal matrix and empty tile coordinates.
     */
    private void moveLeft() {
        final int tempVal = board[emptyY][emptyX - 1];
        board[emptyY][emptyX] = tempVal;
        board[emptyY][emptyX - 1] = 0;
        setEmptyX(emptyX - 1);
    }

    /**
     * Swap the empty tile with the tile to the right. Preconditions: emptyX < borderValue.
     * Updates internal matrix and empty tile coordinates.
     */
    private void moveRight() {
        final int tempVal = board[emptyY][emptyX + 1];
        board[emptyY][emptyX] = tempVal;
        board[emptyY][emptyX + 1] = 0;
        setEmptyX(emptyX + 1);
    }

    /**
     * Move the empty tile in the specified direction. Caller should ensure the
     * move is valid (use canMove* helpers), otherwise an exception from the
     * underlying moveX method may be thrown.
     *
     * @param direction the direction to move the empty tile
     */
    public void moveTile(final Directions direction) {
        switch (direction) {
            case Up -> moveUp();
            case Down -> moveDown();
            case Left -> moveLeft();
            case Right -> moveRight();
        }
    }

    /**
     * Return a multiline string representation of the board matrix.
     *
     * @return human-readable matrix display
     */
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

    /**
     * Count the number of tiles that differ between this board and another board.
     * All positions are compared (including the empty tile).
     *
     * @param board the board to compare against
     * @return number of differing tiles
     */
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

    /**
     * Equality is defined by deep equality of the underlying tile matrix.
     *
     * @param o object to compare
     * @return true if the other object is a Board with the same tiles
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Are they the same object in memory?
        if (o == null || getClass() != o.getClass()) return false; // Is it a Board?
        Board board = (Board) o;
        return java.util.Arrays.deepEquals(this.board, board.board);
    }

    /**
     * Hash code consistent with equals(), based on deep hash of the matrix.
     *
     * @return hash code for the board
     */
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
