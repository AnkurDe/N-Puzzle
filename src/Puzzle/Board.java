package Puzzle;

public class Board {
    private int[][] initialState;
    private int[][] currentState;
    private int[][] goalState;

    private int emptyX, emptyY;

    private final int borderValue;

    public Board(int[][] initialState, int[][] goalState) {
        if (initialState.length != initialState[0].length)
            throw new IllegalArgumentException("Enter a square board");

        if (goalState.length != goalState[0].length)
            throw new IllegalArgumentException("Enter square board");

        if (goalState.length != initialState.length)
            throw new IllegalArgumentException("Boards not of equal size");

        getEmptyPositions(goalState); // Works as error detection
        getEmptyPositions(initialState);// Updates the value

        this.initialState = initialState;
        this.currentState = arrCopy(initialState);
        this.goalState = goalState;

        borderValue = goalState.length - 1;

        System.out.println("Board created");
    }

    private void getEmptyPositions(int[][] matrix) {
        boolean got = false;
        int tempX;
        int tempY;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == 0) {
                    if (!got) {
                        tempX = i;
                        tempY = j;
                        got = true;
                    }
                    else {
                        throw new IllegalArgumentException("You can have only one empty tile marked as 0");
                    }
                }
            }
        }
        if (!got) {
            throw new IllegalArgumentException("You should have at least one empty tile marked as 0");
        }
    }
    
    private static int[][] arrCopy(int[][] matrix) {
        int[][] A = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            A[i]=matrix[i].clone();
        }
        return A;
    }


    // BUGGY
    // Gets empty position of x
    private void setEmptyX(int x) {
        if (x <0 || x > borderValue)
            throw new IllegalStateException();

        int temp = currentState[emptyX][emptyY];
        emptyX = x;
        currentState[emptyX][emptyY] = temp;
    }

    // BUGGY
    // Gets empty position of y
    private void setEmptyY(int y) {
        if (y <0 || y > borderValue)
            throw new IllegalStateException();

        int temp = currentState[emptyX][emptyY];
        emptyY = y;
        currentState[emptyX][emptyY] = temp;
    }

    public void moveTile (Directions direction) {
        switch (direction) {
            case Up -> setEmptyY(emptyY - 1);
            case Down -> setEmptyY(emptyY + 1);
            case Left -> setEmptyX(emptyX - 1);
            case Right -> setEmptyY(emptyX + 1);
        }
    }

    // Gives Current state
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int[] temp : currentState){
            for (int t : temp)
                s.append(" ").append(t);
            s.append("\n");
        }
        return s.toString();
    }
}
