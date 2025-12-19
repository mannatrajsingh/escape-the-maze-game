import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener {

    Grid grid;
    Player player;
    Enemy enemy;

    int size = 30;

    public GamePanel() {

        // Initialize grid and entities
        grid = new Grid(20, 20);
        player = new Player(0, 0);
        enemy = new Enemy(19, 19);

        // Create maze walls
        Random rand = new Random();

        do {
            // generate random walls
            for (int i = 0; i < grid.rows; i++) {
                for (int j = 0; j < grid.cols; j++) {
                    grid.grid[i][j].isWall = rand.nextDouble() < 0.30;
                }
            }

            // clear safe zones
            clearArea(player.row, player.col);
            clearArea(enemy.row, enemy.col);
            clearArea(grid.rows - 1, grid.cols - 1);

        } while (
                !pathExists(enemy.row, enemy.col, player.row, player.col) ||
                        !pathExists(player.row, player.col, grid.rows - 1, grid.cols - 1)
        );


// Ensure start, enemy, and exit are free
        grid.grid[player.row][player.col].isWall = false;
        grid.grid[enemy.row][enemy.col].isWall = false;
        grid.grid[grid.rows - 1][grid.cols - 1].isWall = false;






        // Panel settings
        setPreferredSize(new Dimension(600, 620));
        setFocusable(true);
        addKeyListener(this);

        // Enemy movement timer (game loop)
        new javax.swing.Timer(300, e -> {
            moveEnemy();
            checkGame();
            repaint();
        }).start();
    }
    private void clearArea(int r, int c) {
        for (int i = r - 1; i <= r + 1; i++) {
            for (int j = c - 1; j <= c + 1; j++) {
                if (grid.isValid(i, j)) {
                    grid.grid[i][j].isWall = false;
                }
            }
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw grid
        for (int i = 0; i < grid.rows; i++) {
            for (int j = 0; j < grid.cols; j++) {
                g.setColor(grid.grid[i][j].isWall
                        ? new Color(50, 50, 50)
                        : Color.WHITE);
                g.fillRect(j * size, i * size, size, size);
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(j * size, i * size, size, size);
            }
        }

        // Exit tile
        g.setColor(Color.GREEN);
        g.fillRect((grid.cols - 1) * size,
                (grid.rows - 1) * size,
                size, size);

        // Player
        g.setColor(Color.BLUE);
        g.fillOval(player.col * size,
                player.row * size,
                size, size);

        // Enemy
        g.setColor(Color.RED);
        g.fillOval(enemy.col * size,
                enemy.row * size,
                size, size);

        // Status bar
        g.setColor(Color.BLACK);
        g.drawString(
                "Arrow Keys: Move | Green: Exit | Red: Enemy",
                10, grid.rows * size + 15
        );
    }

    // Enemy uses A* to chase player
    private void moveEnemy() {
        Cell start = grid.grid[enemy.row][enemy.col];
        Cell end = grid.grid[player.row][player.col];

        List<Cell> path = AStar.findPath(grid, start, end);

        // Case 1: A* found a path → move normally
        if (path != null && path.size() > 1) {
            Cell next = path.get(1);
            enemy.row = next.row;
            enemy.col = next.col;
            return;
        }

        // Case 2: No path → greedy fallback (VERY IMPORTANT)
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        int bestDist = Integer.MAX_VALUE;
        int bestR = enemy.row;
        int bestC = enemy.col;

        for (int[] d : dirs) {
            int nr = enemy.row + d[0];
            int nc = enemy.col + d[1];

            if (grid.isValid(nr, nc) && !grid.grid[nr][nc].isWall) {
                int dist = Math.abs(nr - player.row)
                        + Math.abs(nc - player.col);

                if (dist < bestDist) {
                    bestDist = dist;
                    bestR = nr;
                    bestC = nc;
                }
            }
        }

        enemy.row = bestR;
        enemy.col = bestC;
    }


    // Win / Lose conditions
    private void checkGame() {
        if (player.row == enemy.row && player.col == enemy.col) {
            JOptionPane.showMessageDialog(this, "Game Over!");
            System.exit(0);
        }

        if (player.row == grid.rows - 1 &&
                player.col == grid.cols - 1) {
            JOptionPane.showMessageDialog(this, "You Escaped!");
            System.exit(0);
        }
    }

    // Player movement
    @Override
    public void keyPressed(KeyEvent e) {
        int r = player.row;
        int c = player.col;

        if (e.getKeyCode() == KeyEvent.VK_UP) r--;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) r++;
        if (e.getKeyCode() == KeyEvent.VK_LEFT) c--;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) c++;

        if (grid.isValid(r, c) && !grid.grid[r][c].isWall) {
            player.row = r;
            player.col = c;
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    private boolean pathExists(int sr, int sc, int tr, int tc) {
        boolean[][] visited = new boolean[grid.rows][grid.cols];
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        java.util.Queue<int[]> q = new java.util.LinkedList<>();
        q.add(new int[]{sr, sc});
        visited[sr][sc] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            if (cur[0] == tr && cur[1] == tc) return true;

            for (int[] d : dirs) {
                int nr = cur[0] + d[0];
                int nc = cur[1] + d[1];
                if (grid.isValid(nr, nc) &&
                        !grid.grid[nr][nc].isWall &&
                        !visited[nr][nc]) {

                    visited[nr][nc] = true;
                    q.add(new int[]{nr, nc});
                }
            }
        }
        return false;
    }

}
