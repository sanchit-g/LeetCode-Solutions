/*
 * @lc app=leetcode id=1248 lang=java
 *
 * [1248] Count Number of Nice Subarrays
 *
 * https://leetcode.com/problems/count-number-of-nice-subarrays/description/
 *
 * algorithms
 * Medium (74.15%)
 * Likes:    5183
 * Dislikes: 134
 * Total Accepted:    413.7K
 * Total Submissions: 557.9K
 * Testcase Example:  '[1,1,2,1,1]\n3'
 *
 * Given an array of integers nums and an integer k. A continuous subarray is
 * called nice if there are k odd numbers on it.
 * 
 * Return the number of nice sub-arrays.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: nums = [1,1,2,1,1], k = 3
 * Output: 2
 * Explanation: The only sub-arrays with 3 odd numbers are [1,1,2,1] and
 * [1,2,1,1].
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: nums = [2,4,6], k = 1
 * Output: 0
 * Explanation: There are no odd numbers in the array.
 * 
 * 
 * Example 3:
 * 
 * 
 * Input: nums = [2,2,2,1,2,2,1,2,2,2], k = 2
 * Output: 16
 * 
 * 
 * 
 * Constraints:
 * 
 * 
 * 1 <= nums.length <= 50000
 * 1 <= nums[i] <= 10^5
 * 1 <= k <= nums.length
 * 
 * 
 */

// @lc code=start
class Solution {
    public int numberOfSubarrays(int[] nums, int k) {
        // atMost(k) - atMost(k-1)
        return atMostKOdd(nums, k) - atMostKOdd(nums, k - 1);   
    }

    private int atMostKOdd(int[] nums, int k) {
        int left = 0, right = 0, count = 0, oddCount = 0;
        while (right < nums.length) {
            if (nums[right] % 2 == 1) oddCount++;
            while (oddCount > k) {
                if (nums[left] % 2 == 1) oddCount--;
                left++;
            }
            count += right - left + 1;
            right++;
        }
        return count;
    }
}
// @lc code=end

