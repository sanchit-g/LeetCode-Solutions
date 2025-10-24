/*
 * @lc app=leetcode id=1765 lang=java
 *
 * [1765] Map of Highest Peak
 *
 * https://leetcode.com/problems/map-of-highest-peak/description/
 *
 * algorithms
 * Medium (75.33%)
 * Likes:    1513
 * Dislikes: 110
 * Total Accepted:    151.5K
 * Total Submissions: 201K
 * Testcase Example:  '[[0,1],[0,0]]'
 *
 * You are given an integer matrix isWater of size m x n that represents a map
 * of land and water cells.
 * 
 * 
 * If isWater[i][j] == 0, cell (i, j) is a land cell.
 * If isWater[i][j] == 1, cell (i, j) is a water cell.
 * 
 * 
 * You must assign each cell a height in a way that follows these rules:
 * 
 * 
 * The height of each cell must be non-negative.
 * If the cell is a water cell, its height must be 0.
 * Any two adjacent cells must have an absolute height difference of at most 1.
 * A cell is adjacent to another cell if the former is directly north, east,
 * south, or west of the latter (i.e., their sides are touching).
 * 
 * 
 * Find an assignment of heights such that the maximum height in the matrix is
 * maximized.
 * 
 * Return an integer matrix height of size m x n where height[i][j] is cell (i,
 * j)'s height. If there are multiple solutions, return any of them.
 * 
 * 
 * Example 1:
 * 
 * 
 * 
 * 
 * Input: isWater = [[0,1],[0,0]]
 * Output: [[1,0],[2,1]]
 * Explanation: The image shows the assigned heights of each cell.
 * The blue cell is the water cell, and the green cells are the land cells.
 * 
 * 
 * Example 2:
 * 
 * 
 * 
 * 
 * Input: isWater = [[0,0,1],[1,0,0],[0,0,0]]
 * Output: [[1,1,0],[0,1,1],[1,2,2]]
 * Explanation: A height of 2 is the maximum possible height of any assignment.
 * Any height assignment that has a maximum height of 2 while still meeting the
 * rules will also be accepted.
 * 
 * 
 * 
 * Constraints:
 * 
 * 
 * m == isWater.length
 * n == isWater[i].length
 * 1 <= m, n <= 1000
 * isWater[i][j] is 0 or 1.
 * There is at least one water cell.
 * 
 * 
 * 
 * Note: This question is the same as 542:
 * https://leetcode.com/problems/01-matrix/
 * 
 */

// @lc code=start
import java.util.*;

class Solution {
    private int rows, cols;
    // Directions: right, down, left, up
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

    public int[][] highestPeak(int[][] isWater) {
        rows = isWater.length;
        cols = isWater[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int[][] heights = new int[rows][cols];
        
        Queue<int[]> queue = new LinkedList<>();

        // Initialize queue with all water cells (height 0)
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (isWater[row][col] == 1) {
                    queue.offer(new int[] {row, col, 0});
                    visited[row][col] = true;
                }
            }
        }

        // Multi-source BFS to assign heights
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            int height = current[2];

            heights[row][col] = height;

            // Check all 4 adjacent cells
            for (int[] direction : DIRECTIONS) {
                int neighborRow = row + direction[0];
                int neighborCol = col + direction[1];

                // Assign height to unvisited neighbors
                if (isValidCell(neighborRow, neighborCol) && !visited[neighborRow][neighborCol]) {
                    queue.offer(new int[] {neighborRow, neighborCol, height + 1});
                    visited[neighborRow][neighborCol] = true;
                }
            }
        }

        return heights;
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }
}
// @lc code=end

