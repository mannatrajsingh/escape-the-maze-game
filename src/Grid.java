public class Grid {
    int rows, cols;
    Cell[][] grid;

    public Grid(int r, int c) {
        rows = r;
        cols = c;
        grid = new Cell[r][c];

        for (int i = 0; i < r; i++)
            for (int j = 0; j < c; j++)
                grid[i][j] = new Cell(i, j);
    }

    boolean isValid(int r, int c) {
        return r >= 0 && c >= 0 && r < rows && c < cols;
    }
}
