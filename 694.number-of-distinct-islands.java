/*
 * @lc app=leetcode id=694 lang=java
 *
 * [694] Number of Distinct Islands
 *
 * Given a boolean 2D matrix grid of size n * m. You have to find the number of 
 * distinct islands where a group of connected 1s (horizontally or vertically) 
 * forms an island. Two islands are considered to be distinct if and only if one 
 * island is not equal to another (not rotated or reflected).
 *
 * Example 1:
 * Input: grid = [[1, 1, 0, 0, 0],
 *                [1, 1, 0, 0, 0],
 *                [0, 0, 0, 1, 1],
 *                [0, 0, 0, 1, 1]]
 * Output: 1
 * Explanation: Both islands have the same 2x2 shape:
 *              Island 1 (top-left): cells at (0,0), (0,1), (1,0), (1,1)
 *              Island 2 (bottom-right): cells at (2,3), (2,4), (3,3), (3,4)
 *              After normalization, both become: (0,0), (0,1), (1,0), (1,1)
 *              So we have only 1 distinct island shape.
 *
 * Example 2:
 * Input: grid = [[1, 1, 0, 1, 1],
 *                [1, 0, 0, 0, 0],
 *                [0, 0, 0, 0, 1],
 *                [1, 1, 0, 1, 1]]
 * Output: 3
 * Explanation: We have 4 islands total:
 *              Island 1 (top-left): L-shape with 3 cells
 *              Island 2 (top-right): 2x2 square
 *              Island 3 (middle-right): single cell
 *              Island 4 (bottom-left): L-shape with 3 cells (same as Island 1)
 *              Islands 1 and 4 have the same shape after normalization.
 *              So we have 3 distinct island shapes.
 *
 * Constraints:
 * - 1 <= n, m <= 500
 * - grid[i][j] == 0 or grid[i][j] == 1
 *
 * Approach: DFS + Coordinate Normalization
 * 1. Use DFS to find all connected components (islands)
 * 2. For each island, collect all cell coordinates
 * 3. Normalize coordinates by subtracting minimum row and column values
 *    - This creates a canonical representation starting from (0,0)
 *    - Islands with the same shape will have identical normalized coordinates
 * 4. Convert normalized coordinates to a string for hashing
 * 5. Use a HashSet to track unique island shapes
 *
 * Time Complexity: O(n * m) where n is rows and m is columns
 *                  - We visit each cell once during DFS
 *                  - Normalization and sorting take O(k log k) per island where k is island size
 *                  - Since total cells across all islands is at most n*m, overall is O(n*m)
 *
 * Space Complexity: O(n * m)
 *                   - Visited array: O(n * m)
 *                   - Recursion stack: O(n * m) in worst case (entire grid is one island)
 *                   - Set of distinct shapes: O(number of islands)
 */

import java.util.*;

class Solution {
    private int rows, cols;
    private boolean[][] visited;
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
    
    public int numDistinctIslands(int[][] grid) {
        rows = grid.length;
        cols = grid[0].length;
        visited = new boolean[rows][cols];
        
        // Set to store unique island shapes (as normalized coordinate strings)
        Set<String> distinctShapes = new HashSet<>();
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    List<int[]> islandCells = new ArrayList<>();
                    dfs(grid, i, j, islandCells);
                    
                    // Normalize the island shape and add to set
                    String normalizedShape = normalizeIsland(islandCells);
                    distinctShapes.add(normalizedShape);
                }
            }
        }
        
        return distinctShapes.size();
    }
    
    private void dfs(int[][] grid, int row, int col, List<int[]> cells) {
        visited[row][col] = true;
        cells.add(new int[]{row, col});
        
        // Explore all 4 directions (right, down, up, left)
        for (int[] dir : DIRECTIONS) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            
            if (isValidCell(newRow, newCol) && grid[newRow][newCol] == 1 && !visited[newRow][newCol]) {
                dfs(grid, newRow, newCol, cells);
            }
        }
    }
    
    /**
     * Normalize island coordinates to create a canonical representation
     * The normalization process:
     * 1. Find the minimum row and column values in the island
     * 2. Subtract these minimums from all coordinates
     * 3. This shifts the island so its top-left cell is at (0,0)
     * 4. Sort the coordinates for consistent ordering
     * 5. Convert to string for hashing
     * 
     * Example: Island at positions [(2,3), (2,4), (3,3), (3,4)]
     *          Min row = 2, Min col = 3
     *          Normalized: [(0,0), (0,1), (1,0), (1,1)]
     */
    private String normalizeIsland(List<int[]> cells) {
        if (cells.isEmpty()) {
            return "";
        }
        
        int minRow = Integer.MAX_VALUE;
        int minCol = Integer.MAX_VALUE;
        
        for (int[] cell : cells) {
            minRow = Math.min(minRow, cell[0]);
            minCol = Math.min(minCol, cell[1]);
        }
        
        List<String> normalizedCoords = new ArrayList<>();
        for (int[] cell : cells) {
            int relativeRow = cell[0] - minRow;
            int relativeCol = cell[1] - minCol;
            normalizedCoords.add(relativeRow + "," + relativeCol);
        }
        
        Collections.sort(normalizedCoords);
        
        return String.join(";", normalizedCoords);
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }
    
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test Case 1: Two identical 2x2 islands
        int[][] grid1 = {
            {1, 1, 0, 0, 0},
            {1, 1, 0, 0, 0},
            {0, 0, 0, 1, 1},
            {0, 0, 0, 1, 1}
        };
        System.out.println("Test Case 1:");
        System.out.println("Input: [[1,1,0,0,0],[1,1,0,0,0],[0,0,0,1,1],[0,0,0,1,1]]");
        System.out.println("Expected Output: 1");
        System.out.println("Actual Output: " + solution.numDistinctIslands(grid1));
        System.out.println();
        
        // Test Case 2: Four islands with three distinct shapes
        int[][] grid2 = {
            {1, 1, 0, 1, 1},
            {1, 0, 0, 0, 0},
            {0, 0, 0, 0, 1},
            {1, 1, 0, 1, 1}
        };
        System.out.println("Test Case 2:");
        System.out.println("Input: [[1,1,0,1,1],[1,0,0,0,0],[0,0,0,0,1],[1,1,0,1,1]]");
        System.out.println("Expected Output: 3");
        System.out.println("Actual Output: " + solution.numDistinctIslands(grid2));
        System.out.println();
        
        // Test Case 3: All different shapes
        int[][] grid3 = {
            {1, 1, 0, 0, 0},
            {1, 0, 0, 0, 0},
            {0, 0, 0, 1, 1},
            {0, 0, 0, 0, 1}
        };
        System.out.println("Test Case 3:");
        System.out.println("Input: [[1,1,0,0,0],[1,0,0,0,0],[0,0,0,1,1],[0,0,0,0,1]]");
        System.out.println("Expected Output: 2");
        System.out.println("Actual Output: " + solution.numDistinctIslands(grid3));
        System.out.println();
        
        // Test Case 4: Single island
        int[][] grid4 = {
            {1, 1},
            {1, 0}
        };
        System.out.println("Test Case 4:");
        System.out.println("Input: [[1,1],[1,0]]");
        System.out.println("Expected Output: 1");
        System.out.println("Actual Output: " + solution.numDistinctIslands(grid4));
        System.out.println();
        
        // Test Case 5: No islands
        int[][] grid5 = {
            {0, 0, 0},
            {0, 0, 0}
        };
        System.out.println("Test Case 5:");
        System.out.println("Input: [[0,0,0],[0,0,0]]");
        System.out.println("Expected Output: 0");
        System.out.println("Actual Output: " + solution.numDistinctIslands(grid5));
    }
}