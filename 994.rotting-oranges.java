/*
 * @lc app=leetcode id=994 lang=java
 *
 * [994] Rotting Oranges
 *
 * https://leetcode.com/problems/rotting-oranges/description/
 *
 * algorithms
 * Medium (57.40%)
 * Likes:    14562
 * Dislikes: 461
 * Total Accepted:    1.4M
 * Total Submissions: 2.5M
 * Testcase Example:  '[[2,1,1],[1,1,0],[0,1,1]]'
 *
 * You are given an m x n grid where each cell can have one of three
 * values:
 * 
 * 
 * 0 representing an empty cell,
 * 1 representing a fresh orange, or
 * 2 representing a rotten orange.
 * 
 * 
 * Every minute, any fresh orange that is 4-directionally adjacent to a rotten
 * orange becomes rotten.
 * 
 * Return the minimum number of minutes that must elapse until no cell has a
 * fresh orange. If this is impossible, return -1.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: grid = [[2,1,1],[1,1,0],[0,1,1]]
 * Output: 4
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: grid = [[2,1,1],[0,1,1],[1,0,1]]
 * Output: -1
 * Explanation: The orange in the bottom left corner (row 2, column 0) is never
 * rotten, because rotting only happens 4-directionally.
 * 
 * 
 * Example 3:
 * 
 * 
 * Input: grid = [[0,2]]
 * Output: 0
 * Explanation: Since there are already no fresh oranges at minute 0, the
 * answer is just 0.
 * 
 * 
 * 
 * Constraints:
 * 
 * 
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 10
 * grid[i][j] is 0, 1, or 2.
 * 
 * 
 */

// @lc code=start
import java.util.*;

class Cell {
    int row;
    int col;
    int minutes;

    public Cell(int row, int col, int minutes) {
        this.row = row;
        this.col = col;
        this.minutes = minutes;
    }
}

class Solution {
    private int rows, cols;
    // Directions: right, down, left, up
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
    private boolean[][] visited;

    public int orangesRotting(int[][] grid) {
        rows = grid.length;
        cols = grid[0].length;
        visited = new boolean[rows][cols];

        Queue<Cell> queue = new LinkedList<>();
        int freshCount = 0;

        // Initialize queue with all initially rotten oranges and count fresh ones
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(new Cell(i, j, 0));
                    visited[i][j] = true;
                } else if (grid[i][j] == 1) {
                    freshCount++;
                }
            }
        }
        
        int maxMinutes = 0;
        
        // BFS to spread rot to adjacent fresh oranges
        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            maxMinutes = Math.max(maxMinutes, current.minutes);

            // Check all 4 adjacent cells
            for (int[] direction : DIRECTIONS) {
                int neighborRow = current.row + direction[0];
                int neighborCol = current.col + direction[1];

                // Rot adjacent fresh orange if valid and unvisited
                if (isValidCell(neighborRow, neighborCol)
                    && !visited[neighborRow][neighborCol]
                    && grid[neighborRow][neighborCol] == 1) {
                    queue.offer(new Cell(neighborRow, neighborCol, current.minutes + 1));
                    visited[neighborRow][neighborCol] = true;
                    freshCount--;
                }
            }
        }

        // Return -1 if any fresh oranges remain unreachable
        return freshCount == 0 ? maxMinutes : -1;
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }
}
// @lc code=end

