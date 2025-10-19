void main() {
    int[][] start = {
            {1, 2, 3},
            {4, 0, 5},
            {6, 7, 8}
    };

    int[][] finalState = {
            {8, 6, 3},
            {4, 5, 0},
            {2, 7, 1}
    };
    Game game = new Game(start, finalState);
    game.solvePuzzle();
}