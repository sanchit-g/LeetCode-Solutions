/*
 * @lc app=leetcode id=733 lang=java
 *
 * [733] Flood Fill
 *
 * https://leetcode.com/problems/flood-fill/description/
 *
 * algorithms
 * Easy (67.17%)
 * Likes:    9215
 * Dislikes: 943
 * Total Accepted:    1.3M
 * Total Submissions: 1.9M
 * Testcase Example:  '[[1,1,1],[1,1,0],[1,0,1]]\n1\n1\n2'
 *
 * You are given an image represented by an m x n grid of integers image, where
 * image[i][j] represents the pixel value of the image. You are also given
 * three integers sr, sc, and color. Your task is to perform a flood fill on
 * the image starting from the pixel image[sr][sc].
 * 
 * To perform a flood fill:
 * 
 * 
 * Begin with the starting pixel and change its color to color.
 * Perform the same process for each pixel that is directly adjacent (pixels
 * that share a side with the original pixel, either horizontally or
 * vertically) and shares the same color as the starting pixel.
 * Keep repeating this process by checking neighboring pixels of the updated
 * pixels and modifying their color if it matches the original color of the
 * starting pixel.
 * The process stops when there are no more adjacent pixels of the original
 * color to update.
 * 
 * 
 * Return the modified image after performing the flood fill.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: image = [[1,1,1],[1,1,0],[1,0,1]], sr = 1, sc = 1, color = 2
 * 
 * Output: [[2,2,2],[2,2,0],[2,0,1]]
 * 
 * Explanation:
 * 
 * 
 * 
 * From the center of the image with position (sr, sc) = (1, 1) (i.e., the red
 * pixel), all pixels connected by a path of the same color as the starting
 * pixel (i.e., the blue pixels) are colored with the new color.
 * 
 * Note the bottom corner is not colored 2, because it is not horizontally or
 * vertically connected to the starting pixel.
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: image = [[0,0,0],[0,0,0]], sr = 0, sc = 0, color = 0
 * 
 * Output: [[0,0,0],[0,0,0]]
 * 
 * Explanation:
 * 
 * The starting pixel is already colored with 0, which is the same as the
 * target color. Therefore, no changes are made to the image.
 * 
 * 
 * 
 * Constraints:
 * 
 * 
 * m == image.length
 * n == image[i].length
 * 1 <= m, n <= 50
 * 0 <= image[i][j], color < 2^16
 * 0 <= sr < m
 * 0 <= sc < n
 * 
 * 
 */

// @lc code=start
class Solution {
    private int rows, cols;
    // Directions: right, down, left, up
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        rows = image.length;
        cols = image[0].length;
        int originalColor = image[sr][sc];
        
        // Avoid infinite recursion if colors are the same
        if (originalColor != newColor) {
            dfs(image, sr, sc, newColor, originalColor);
        }
        return image;
    }

    private void dfs(int[][] image, int row, int col, int newColor, int originalColor) {
        // Change current cell's color
        image[row][col] = newColor;

        // Explore all 4 adjacent cells (right, down, left, up)
        for (int[] direction : DIRECTIONS) {
            int neighborRow = row + direction[0];
            int neighborCol = col + direction[1];
        
            // Only fill if neighbor is valid and has the original color
            if (isValidCell(neighborRow, neighborCol)
                && image[neighborRow][neighborCol] == originalColor) {
                dfs(image, neighborRow, neighborCol, newColor, originalColor);
            }
        }
    }

    // Check if cell coordinates are within grid bounds
    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }
}
// @lc code=end

