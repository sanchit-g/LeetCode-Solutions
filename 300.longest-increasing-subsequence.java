/*
 * @lc app=leetcode id=300 lang=java
 *
 * [300] Longest Increasing Subsequence
 *
 * https://leetcode.com/problems/longest-increasing-subsequence/description/
 *
 * algorithms
 * Medium (58.43%)
 * Likes:    22258
 * Dislikes: 492
 * Total Accepted:    2.4M
 * Total Submissions: 4M
 * Testcase Example:  '[10,9,2,5,3,7,101,18]'
 *
 * Given an integer array nums, return the length of the longest strictly
 * increasing subsequence.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: nums = [10,9,2,5,3,7,101,18]
 * Output: 4
 * Explanation: The longest increasing subsequence is [2,3,7,101], therefore
 * the length is 4.
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: nums = [0,1,0,3,2,3]
 * Output: 4
 * 
 * 
 * Example 3:
 * 
 * 
 * Input: nums = [7,7,7,7,7,7,7]
 * Output: 1
 * 
 * 
 * 
 * Constraints:
 * 
 * 
 * 1 <= nums.length <= 2500
 * -10^4 <= nums[i] <= 10^4
 * 
 * 
 * 
 * Follow up: Can you come up with an algorithm that runs in O(n log(n)) time
 * complexity?
 * 
 */

// @lc code=start
import java.util.*;

class Solution {
    public int lengthOfLIS(int[] nums) {
        int[][] memo = new int[nums.length][nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            Arrays.fill(memo[i], -1);
        }
        return recursiveLIS(nums, 0, -1, memo);
    }

    private int recursiveLIS(int[] nums, int currentIndex, int previousIndex, int[][] memo) {
        // Base case: If we have processed all elements
        if (currentIndex == nums.length) {
            return 0;
        }

        // Check if the result is already computed
        if (memo[currentIndex][previousIndex + 1] != -1) {
            return memo[currentIndex][previousIndex + 1];
        }

        // Option 1: Exclude the current element and move to the next
        int lengthExcludingCurrent = recursiveLIS(nums, currentIndex + 1, previousIndex, memo);

        // Option 2: Include the current element if it is greater than the previous element
        int lengthIncludingCurrent = 0;
        if (previousIndex == -1 || nums[currentIndex] > nums[previousIndex]) {
            lengthIncludingCurrent = 1 + recursiveLIS(nums, currentIndex + 1, currentIndex, memo);
        }

        // Store the result in the memoization cache
        memo[currentIndex][previousIndex + 1] = Math.max(lengthExcludingCurrent, lengthIncludingCurrent);
        
        return memo[currentIndex][previousIndex + 1];
    }
}
// @lc code=end

