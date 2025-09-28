/*
 * @lc app=leetcode id=329 lang=java
 *
 * [329] Longest Increasing Path in a Matrix
 *
 * https://leetcode.com/problems/longest-increasing-path-in-a-matrix/description/
 *
 * algorithms
 * Hard (55.78%)
 * Likes:    9345
 * Dislikes: 145
 * Total Accepted:    663.1K
 * Total Submissions: 1.2M
 * Testcase Example:  '[[9,9,4],[6,6,8],[2,1,1]]'
 *
 * Given an m x n integers matrix, return the length of the longest increasing
 * path in matrix.
 * 
 * From each cell, you can either move in four directions: left, right, up, or
 * down. You may not move diagonally or move outside the boundary (i.e.,
 * wrap-around is not allowed).
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: matrix = [[9,9,4],[6,6,8],[2,1,1]]
 * Output: 4
 * Explanation: The longest increasing path is [1, 2, 6, 9].
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: matrix = [[3,4,5],[3,2,6],[2,2,1]]
 * Output: 4
 * Explanation: The longest increasing path is [3, 4, 5, 6]. Moving diagonally
 * is not allowed.
 * 
 * 
 * Example 3:
 * 
 * 
 * Input: matrix = [[1]]
 * Output: 1
 * 
 * 
 * 
 * Constraints:
 * 
 * 
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 200
 * 0 <= matrix[i][j] <= 2^31 - 1
 * 
 * 
 */

// @lc code=start
class Solution {
    // Direction vectors for moving in 4 directions: right, down, left, up
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    
    private int rows;
    private int cols;
    private int[][] memo;

    public int longestIncreasingPath(int[][] matrix) {
        rows = matrix.length;
        cols = matrix[0].length;
        memo = new int[rows][cols];
        
        int maxPathLength = 0;
        
        // Try starting from each cell and find the maximum path length
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                maxPathLength = Math.max(maxPathLength, dfsWithMemo(matrix, row, col));
            }
        }
        
        return maxPathLength;
    }
    
    private int dfsWithMemo(int[][] matrix, int row, int col) {
        if (memo[row][col] != 0) {
            return memo[row][col];
        }
        
        int maxPathFromCurrentCell = 1;
        
        // Explore all 4 directions
        for (int[] direction : DIRECTIONS) {
            int nextRow = row + direction[0];
            int nextCol = col + direction[1];
            
            // Check if the next cell is valid and forms an increasing path
            if (isValidCell(nextRow, nextCol) && matrix[nextRow][nextCol] > matrix[row][col]) {
                int pathFromNextCell = 1 + dfsWithMemo(matrix, nextRow, nextCol);
                maxPathFromCurrentCell = Math.max(maxPathFromCurrentCell, pathFromNextCell);
            }
        }
        
        memo[row][col] = maxPathFromCurrentCell;
        return maxPathFromCurrentCell;
    }
    
    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }
}
// @lc code=end

