/*
 * @lc app=leetcode id=992 lang=java
 *
 * [992] Subarrays with K Different Integers
 *
 * https://leetcode.com/problems/subarrays-with-k-different-integers/description/
 *
 * algorithms
 * Hard (66.82%)
 * Likes:    6689
 * Dislikes: 110
 * Total Accepted:    337.4K
 * Total Submissions: 505K
 * Testcase Example:  '[1,2,1,2,3]\n2'
 *
 * Given an integer array nums and an integer k, return the number of good
 * subarrays of nums.
 * 
 * A good array is an array where the number of different integers in that
 * array is exactly k.
 * 
 * 
 * For example, [1,2,3,1,2] has 3 different integers: 1, 2, and 3.
 * 
 * 
 * A subarray is a contiguous part of an array.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: nums = [1,2,1,2,3], k = 2
 * Output: 7
 * Explanation: Subarrays formed with exactly 2 different integers: [1,2],
 * [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2]
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: nums = [1,2,1,3,4], k = 3
 * Output: 3
 * Explanation: Subarrays formed with exactly 3 different integers: [1,2,1,3],
 * [2,1,3], [1,3,4].
 * 
 * 
 * 
 * Constraints:
 * 
 * 
 * 1 <= nums.length <= 2 * 10^4
 * 1 <= nums[i], k <= nums.length
 * 
 * 
 */

// @lc code=start
import java.util.*;

class Solution {
    public int subarraysWithKDistinct(int[] nums, int k) {
        return atMostKDistinct(nums, k) - atMostKDistinct(nums, k - 1);
    }

    private int atMostKDistinct(int[] nums, int k) {
        int left = 0, right = 0, result = 0;
        Map<Integer, Integer> count = new HashMap<>();

        while (right < nums.length) {
            count.put(nums[right], count.getOrDefault(nums[right], 0) + 1);

            while (count.size() > k) {
                // Shrink window while we have more than k distinct integers
                int leftNum = nums[left];
                count.put(leftNum, count.get(leftNum) - 1);
                if (count.get(leftNum) == 0) {
                    count.remove(leftNum);
                }
                left++;
            }

            result += right - left + 1;
            right++;
        }

        return result;
    }
}
// @lc code=end

