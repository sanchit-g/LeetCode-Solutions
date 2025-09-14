/*
 * @lc app=leetcode id=980 lang=java
 *
 * [980] Unique Paths III
 */

// @lc code=start
class Solution {
    public int uniquePathsIII(int[][] grid) {
        int startX = 0, startY = 0, emptyCount = 0;
        int rows = grid.length, cols = grid[0].length;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 1) {
                    startX = r;
                    startY = c;
                } else if (grid[r][c] == 0) {
                    emptyCount++;
                }
            }
        }

        return dfs(grid, startX, startY, emptyCount);
    }

    private int dfs(int[][] grid, int x, int y, int emptyCount) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] == -1) {
            return 0;
        }
        
        if (grid[x][y] == 2) {
            return emptyCount == -1 ? 1 : 0;
        }
        
        int temp = grid[x][y];
        grid[x][y] = -1; // mark as visited
        
        int paths = 0;
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        
        for (int[] dir : directions) {
            int newX = x + dir[0], newY = y + dir[1];
            paths += dfs(grid, newX, newY, emptyCount - 1);
        }
        
        grid[x][y] = temp; // backtrack
        
        return paths;
    }
}
// @lc code=end

