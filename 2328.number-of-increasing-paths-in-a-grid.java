/*
 * @lc app=leetcode id=2328 lang=java
 *
 * [2328] Number of Increasing Paths in a Grid
 *
 * https://leetcode.com/problems/number-of-increasing-paths-in-a-grid/description/
 *
 * algorithms
 * Hard (57.52%)
 * Likes:    2084
 * Dislikes: 45
 * Total Accepted:    78.7K
 * Total Submissions: 136.8K
 * Testcase Example:  '[[1,1],[3,4]]'
 *
 * You are given an m x n integer matrix grid, where you can move from a cell
 * to any adjacent cell in all 4 directions.
 * 
 * Return the number of strictly increasing paths in the grid such that you can
 * start from any cell and end at any cell. Since the answer may be very large,
 * return it modulo 10^9 + 7.
 * 
 * Two paths are considered different if they do not have exactly the same
 * sequence of visited cells.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: grid = [[1,1],[3,4]]
 * Output: 8
 * Explanation: The strictly increasing paths are:
 * - Paths with length 1: [1], [1], [3], [4].
 * - Paths with length 2: [1 -> 3], [1 -> 4], [3 -> 4].
 * - Paths with length 3: [1 -> 3 -> 4].
 * The total number of paths is 4 + 3 + 1 = 8.
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: grid = [[1],[2]]
 * Output: 3
 * Explanation: The strictly increasing paths are:
 * - Paths with length 1: [1], [2].
 * - Paths with length 2: [1 -> 2].
 * The total number of paths is 2 + 1 = 3.
 * 
 * 
 * 
 * Constraints:
 * 
 * 
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 1000
 * 1 <= m * n <= 10^5
 * 1 <= grid[i][j] <= 10^5
 * 
 * 
 */

// @lc code=start
class Solution {
    private static final int MOD = 1_000_000_007;
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private int rows, cols;
    private long[][] memo;

    public int countPaths(int[][] grid) {
        rows = grid.length;
        cols = grid[0].length;
        memo = new long[rows][cols];

        long totalPaths = 0;

        // Start DFS from each cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                totalPaths = (totalPaths + dfs(grid, i, j)) % MOD;
            }
        }

        return (int) totalPaths;
    }

    private long dfs(int[][] grid, int row, int col) {
        // Return cached result if already computed
        if (memo[row][col] != 0) return memo[row][col];

        // Each cell itself is a valid path
        long pathCount = 1;

        for (int[] direction : DIRECTIONS) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (isValidCell(newRow, newCol) && grid[newRow][newCol] > grid[row][col]) {
                pathCount = (pathCount + dfs(grid, newRow, newCol)) % MOD;
            }
        }

        memo[row][col] = pathCount;
        return pathCount;
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }
}
// @lc code=end

