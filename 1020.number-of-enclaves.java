/*
 * @lc app=leetcode id=1020 lang=java
 *
 * [1020] Number of Enclaves
 *
 * https://leetcode.com/problems/number-of-enclaves/description/
 *
 * algorithms
 * Medium (71.13%)
 * Likes:    4500
 * Dislikes: 87
 * Total Accepted:    367.2K
 * Total Submissions: 515.8K
 * Testcase Example:  '[[0,0,0,0],[1,0,1,0],[0,1,1,0],[0,0,0,0]]'
 *
 * You are given an m x n binary matrix grid, where 0 represents a sea cell and
 * 1 represents a land cell.
 * 
 * A move consists of walking from one land cell to another adjacent
 * (4-directionally) land cell or walking off the boundary of the grid.
 * 
 * Return the number of land cells in grid for which we cannot walk off the
 * boundary of the grid in any number of moves.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: grid = [[0,0,0,0],[1,0,1,0],[0,1,1,0],[0,0,0,0]]
 * Output: 3
 * Explanation: There are three 1s that are enclosed by 0s, and one 1 that is
 * not enclosed because its on the boundary.
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: grid = [[0,1,1,0],[0,0,1,0],[0,0,1,0],[0,0,0,0]]
 * Output: 0
 * Explanation: All 1s are either on the boundary or can reach the
 * boundary.
 * 
 * 
 * 
 * Constraints:
 * 
 * 
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 500
 * grid[i][j] is either 0 or 1.
 * 
 * 
 */

// @lc code=start
import java.util.*;

class Solution {
    private int rows, cols;
    private boolean[][] visited;
    // Directions: right, down, left, up
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

    public int numEnclaves(int[][] grid) {
        rows = grid.length;
        cols = grid[0].length;
        visited = new boolean[rows][cols];

        Queue<int[]> queue = new LinkedList<>();
        
        // Mark all boundary land cells and add to queue
        // Left and right columns
        for (int row = 0; row < rows; row++) {
            if (grid[row][0] == 1 && !visited[row][0]) {
                queue.offer(new int[] {row, 0});
                visited[row][0] = true;
            }
            if (grid[row][cols - 1] == 1 && !visited[row][cols - 1]) {
                queue.offer(new int[] {row, cols - 1});
                visited[row][cols - 1] = true;
            }
        }

        // Top and bottom rows
        for (int col = 0; col < cols; col++) {
            if (grid[0][col] == 1 && !visited[0][col]) {
                queue.offer(new int[] {0, col});
                visited[0][col] = true;
            }
            if (grid[rows - 1][col] == 1 && !visited[rows - 1][col]) {
                queue.offer(new int[] {rows - 1, col});
                visited[rows - 1][col] = true;
            }
        }

        // BFS to mark all land cells reachable from boundaries
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            
            // Check all 4 adjacent cells
            for (int[] direction : DIRECTIONS) {
                int neighborRow = row + direction[0];
                int neighborCol = col + direction[1];
                    
                if (isValidCell(neighborRow, neighborCol)
                    && !visited[neighborRow][neighborCol]
                    && grid[neighborRow][neighborCol] == 1) {
                    visited[neighborRow][neighborCol] = true;
                    queue.offer(new int[] {neighborRow, neighborCol});
                }
            }
        }

        // Count unvisited land cells (enclaves)
        int enclaveCount = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == 1 && !visited[row][col]) {
                    enclaveCount++;
                }
            }
        }

        return enclaveCount;
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }
}
// @lc code=end

