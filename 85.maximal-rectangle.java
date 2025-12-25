/*
 * @lc app=leetcode id=85 lang=java
 *
 * [85] Maximal Rectangle
 *
 * https://leetcode.com/problems/maximal-rectangle/description/
 *
 * algorithms
 * Hard (55.41%)
 * Likes:    11551
 * Dislikes: 214
 * Total Accepted:    674.2K
 * Total Submissions: 1.2M
 * Testcase Example:  '[["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]'
 *
 * Given a rows x cols binary matrix filled with 0's and 1's, find the largest
 * rectangle containing only 1's and return its area.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: matrix =
 * [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
 * Output: 6
 * Explanation: The maximal rectangle is shown in the above picture.
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: matrix = [["0"]]
 * Output: 0
 * 
 * 
 * Example 3:
 * 
 * 
 * Input: matrix = [["1"]]
 * Output: 1
 * 
 * 
 * 
 * Constraints:
 * 
 * 
 * rows == matrix.length
 * cols == matrix[i].length
 * 1 <= rows, cols <= 200
 * matrix[i][j] is '0' or '1'.
 * 
 * 
 */

// @lc code=start
import java.util.Stack;
import java.util.Arrays;

class Solution {
    private int[] findPreviousSmallerIndices(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int[] result = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) stack.pop();
            result[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        return result;
    }

    private int[] findNextSmallerIndices(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int[] result = new int[nums.length];
        for (int i = nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) stack.pop();
            result[i] = stack.isEmpty() ? nums.length : stack.peek();
            stack.push(i);
        }
        return result;
    }
    
    private int findLargestArea(int[] heights) {
        int[] previousSmallerIndices = findPreviousSmallerIndices(heights);
        int[] nextSmallerIndices = findNextSmallerIndices(heights);
        int maxArea = 0;
        for (int i = 0; i < heights.length; i++) {
            int width = nextSmallerIndices[i] - previousSmallerIndices[i] - 1;
            maxArea = Math.max(maxArea, heights[i] * width);
        }
        return maxArea;
    }

    private int[][] convertToHeights(char[][] matrix) {
        int[][] heights = new int[matrix.length][matrix[0].length];
        for (int j = 0; j < matrix[0].length; j++) heights[0][j] = matrix[0][j] == '1' ? 1 : 0;

        for (int i = 1; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == '1') heights[i][j] = heights[i - 1][j] + 1;
                else heights[i][j] = 0;
            }
        }
        return heights;
    }

    public int maximalRectangle(char[][] matrix) {
        int[][] heights = convertToHeights(matrix);
        int maxArea = 0;
        for (int[] height : heights) System.out.println(Arrays.toString(height));
        for (int[] height : heights) maxArea = Math.max(maxArea, findLargestArea(height));
        return maxArea;
    }
}
// @lc code=end

