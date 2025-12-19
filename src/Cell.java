public class Cell {
    int row, col;
    boolean isWall = false;

    int g = Integer.MAX_VALUE;
    int h = 0;
    int f = 0;

    Cell parent = null;

    public Cell(int r, int c) {
        row = r;
        col = c;
    }

    void updateF() {
        f = g + h;
    }
}
