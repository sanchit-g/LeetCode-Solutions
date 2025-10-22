/*
 * @lc app=leetcode id=200 lang=java
 *
 * [200] Number of Islands
 *
 * https://leetcode.com/problems/number-of-islands/description/
 *
 * algorithms
 * Medium (63.00%)
 * Likes:    24373
 * Dislikes: 592
 * Total Accepted:    3.7M
 * Total Submissions: 5.9M
 * Testcase Example:  '[["1","1","1","1","0"],["1","1","0","1","0"],["1","1","0","0","0"],["0","0","0","0","0"]]'
 *
 * Given an m x n 2D binary grid grid which represents a map of '1's (land) and
 * '0's (water), return the number of islands.
 * 
 * An island is surrounded by water and is formed by connecting adjacent lands
 * horizontally or vertically. You may assume all four edges of the grid are
 * all surrounded by water.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: grid = [
 * ⁠ ["1","1","1","1","0"],
 * ⁠ ["1","1","0","1","0"],
 * ⁠ ["1","1","0","0","0"],
 * ⁠ ["0","0","0","0","0"]
 * ]
 * Output: 1
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: grid = [
 * ⁠ ["1","1","0","0","0"],
 * ⁠ ["1","1","0","0","0"],
 * ⁠ ["0","0","1","0","0"],
 * ⁠ ["0","0","0","1","1"]
 * ]
 * Output: 3
 * 
 * 
 * 
 * Constraints:
 * 
 * 
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 300
 * grid[i][j] is '0' or '1'.
 * 
 * 
 */

// @lc code=start
import java.util.*;

class Cell {
    int row;
    int col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }
}

class Solution {
    private boolean[][] visited;
    private int rows, cols;
    // Directions: right, down, left, up
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        rows = grid.length;
        cols = grid[0].length;
        visited = new boolean[rows][cols];

        int islandCount = 0;

        // Iterate through each cell in the grid
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // Start BFS when we find an unvisited land cell
                if (grid[row][col] == '1' && !visited[row][col]) {
                    islandCount++;
                    bfs(grid, row, col);
                }
            }
        }

        return islandCount;
    }

    // BFS to mark all connected land cells as visited
    private void bfs(char[][] grid, int startRow, int startCol) {
        Queue<Cell> queue = new LinkedList<>();
        queue.offer(new Cell(startRow, startCol));
        
        visited[startRow][startCol] = true; // Mark as visited when adding to queue

        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            int row = current.row;
            int col = current.col;

            // Explore all 4 adjacent cells
            for (int[] direction : DIRECTIONS) {
                int newRow = row + direction[0];
                int newCol = col + direction[1];
            
                // Add unvisited land cells to queue
                if (isValidCell(newRow, newCol) && grid[newRow][newCol] == '1' && !visited[newRow][newCol]) {
                    queue.offer(new Cell(newRow, newCol));
                    visited[newRow][newCol] = true; // Mark as visited when adding to queue
                }
            }
        }
    }

    // Check if cell coordinates are within grid bounds
    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }
}
// @lc code=end

