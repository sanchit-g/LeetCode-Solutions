/*
 * @lc app=leetcode id=42 lang=java
 *
 * [42] Trapping Rain Water
 *
 * https://leetcode.com/problems/trapping-rain-water/description/
 *
 * algorithms
 * Hard (65.91%)
 * Likes:    35118
 * Dislikes: 641
 * Total Accepted:    3.1M
 * Total Submissions: 4.7M
 * Testcase Example:  '[0,1,0,2,1,0,1,3,2,1,2,1]'
 *
 * Given n non-negative integers representing an elevation map where the width
 * of each bar is 1, compute how much water it can trap after raining.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * Output: 6
 * Explanation: The above elevation map (black section) is represented by array
 * [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue
 * section) are being trapped.
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: height = [4,2,0,3,2,5]
 * Output: 9
 * 
 * 
 * 
 * Constraints:
 * 
 * 
 * n == height.length
 * 1 <= n <= 2 * 10^4
 * 0 <= height[i] <= 10^5
 * 
 * 
 */

// @lc code=start
class Solution {
    public int trap(int[] height) {
        int n = height.length;
        
        if (n == 0) return 0;

        int[] leftMax = new int[n];
        int[] rightMax = new int[n];

        int mx = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            mx = Math.max(mx, height[i]);
            leftMax[i] = mx;
        }

        mx = Integer.MIN_VALUE;

        for (int i = n - 1; i >= 0; i--) {
            mx = Math.max(mx, height[i]);
            rightMax[i] = mx;
        }

        int waterTrapped = 0;

        for (int i = 0; i < n; i++) {
            waterTrapped += Math.min(leftMax[i], rightMax[i]) - height[i];
        }

        return waterTrapped;
    }
}
// @lc code=end

