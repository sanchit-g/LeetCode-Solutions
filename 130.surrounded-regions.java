/*
 * @lc app=leetcode id=130 lang=java
 *
 * [130] Surrounded Regions
 *
 * https://leetcode.com/problems/surrounded-regions/description/
 *
 * algorithms
 * Medium (43.78%)
 * Likes:    9594
 * Dislikes: 2169
 * Total Accepted:    1.1M
 * Total Submissions: 2.4M
 * Testcase Example:  '[["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]'
 *
 * You are given an m x n matrix board containing letters 'X' and 'O', capture
 * regions that are surrounded:
 * 
 * 
 * Connect: A cell is connected to adjacent cells horizontally or
 * vertically.
 * Region: To form a region connect every 'O' cell.
 * Surround: The region is surrounded with 'X' cells if you can connect the
 * region with 'X' cells and none of the region cells are on the edge of the
 * board.
 * 
 * 
 * To capture a surrounded region, replace all 'O's with 'X's in-place within
 * the original board. You do not need to return anything.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: board =
 * [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
 * 
 * Output:
 * [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
 * 
 * Explanation:
 * 
 * In the above diagram, the bottom region is not captured because it is on the
 * edge of the board and cannot be surrounded.
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: board = [["X"]]
 * 
 * Output: [["X"]]
 * 
 * 
 * 
 * Constraints:
 * 
 * 
 * m == board.length
 * n == board[i].length
 * 1 <= m, n <= 200
 * board[i][j] is 'X' or 'O'.
 * 
 * 
 */

// @lc code=start
class Solution {
    private int rows, cols;
    private boolean[][] visited;
    // Directions: right, down, left, up
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
    
    public void solve(char[][] board) {
        rows = board.length;
        cols = board[0].length;
        visited = new boolean[rows][cols];

        // Mark all 'O' cells connected to boundaries as visited
        // Top and bottom rows
        for (int col = 0; col < cols; col++) {
            if (board[0][col] == 'O' && !visited[0][col]) {
                dfs(board, 0, col);
            }
            if (board[rows - 1][col] == 'O' && !visited[rows - 1][col]) {
                dfs(board, rows - 1, col);
            }
        }

        // Left and right columns
        for (int row = 0; row < rows; row++) {
            if (board[row][0] == 'O' && !visited[row][0]) {
                dfs(board, row, 0);
            }
            if (board[row][cols - 1] == 'O' && !visited[row][cols - 1]) {
                dfs(board, row, cols - 1);
            }
        }

        // Capture all unvisited 'O' cells (surrounded regions)
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col] == 'O' && !visited[row][col]) {
                    board[row][col] = 'X';
                }
            }
        }
    }

    private void dfs(char[][] board, int row, int col) {
        visited[row][col] = true;

        // Explore all 4 adjacent cells
        for (int[] direction : DIRECTIONS) {
            int neighborRow = row + direction[0];
            int neighborCol = col + direction[1];

            if (isValidCell(neighborRow, neighborCol)
                && !visited[neighborRow][neighborCol]
                && board[neighborRow][neighborCol] == 'O') {
                dfs(board, neighborRow, neighborCol);
            }
        }
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }
}
// @lc code=end

