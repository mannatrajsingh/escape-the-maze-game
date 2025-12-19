import java.util.*;

public class AStar {

    static int[][] DIRS = {{1,0},{-1,0},{0,1},{0,-1}};

    public static List<Cell> findPath(Grid grid, Cell start, Cell end) {

        PriorityQueue<Cell> open =
                new PriorityQueue<>(Comparator.comparingInt(c -> c.f));
        HashSet<Cell> closed = new HashSet<>();

        for (int i = 0; i < grid.rows; i++)
            for (int j = 0; j < grid.cols; j++) {
                Cell c = grid.grid[i][j];
                c.g = Integer.MAX_VALUE;
                c.parent = null;
            }

        start.g = 0;
        start.h = heuristic(start, end);
        start.updateF();
        open.add(start);

        while (!open.isEmpty()) {
            Cell curr = open.poll();

            if (curr == end)
                return buildPath(end);

            closed.add(curr);

            for (int[] d : DIRS) {
                int nr = curr.row + d[0];
                int nc = curr.col + d[1];

                if (!grid.isValid(nr, nc)) continue;

                Cell neigh = grid.grid[nr][nc];
                if (neigh.isWall || closed.contains(neigh)) continue;

                int tempG = curr.g + 1;

                if (tempG < neigh.g) {
                    neigh.parent = curr;
                    neigh.g = tempG;
                    neigh.h = heuristic(neigh, end);
                    neigh.updateF();
                    open.add(neigh);
                }
            }
        }
        return null;
    }

    static int heuristic(Cell a, Cell b) {
        return Math.abs(a.row - b.row) + Math.abs(a.col - b.col);
    }

    static List<Cell> buildPath(Cell end) {
        List<Cell> path = new ArrayList<>();
        while (end != null) {
            path.add(end);
            end = end.parent;
        }
        Collections.reverse(path);
        return path;
    }
}

