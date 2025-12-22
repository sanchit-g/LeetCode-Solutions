/*
 * @lc app=leetcode id=2110 lang=java
 *
 * [2110] Number of Smooth Descent Periods of a Stock
 *
 * https://leetcode.com/problems/number-of-smooth-descent-periods-of-a-stock/description/
 *
 * algorithms
 * Medium (59.95%)
 * Likes:    1066
 * Dislikes: 51
 * Total Accepted:    137K
 * Total Submissions: 202.5K
 * Testcase Example:  '[3,2,1,4]'
 *
 * You are given an integer array prices representing the daily price history
 * of a stock, where prices[i] is the stock price on the i^th day.
 * 
 * A smooth descent period of a stock consists of one or more contiguous days
 * such that the price on each day is lower than the price on the preceding day
 * by exactly 1. The first day of the period is exempted from this rule.
 * 
 * Return the number of smooth descent periods.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: prices = [3,2,1,4]
 * Output: 7
 * Explanation: There are 7 smooth descent periods:
 * [3], [2], [1], [4], [3,2], [2,1], and [3,2,1]
 * Note that a period with one day is a smooth descent period by the
 * definition.
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: prices = [8,6,7,7]
 * Output: 4
 * Explanation: There are 4 smooth descent periods: [8], [6], [7], and [7]
 * Note that [8,6] is not a smooth descent period as 8 - 6 ≠ 1.
 * 
 * 
 * Example 3:
 * 
 * 
 * Input: prices = [1]
 * Output: 1
 * Explanation: There is 1 smooth descent period: [1]
 * 
 * 
 * 
 * Constraints:
 * 
 * 
 * 1 <= prices.length <= 10^5
 * 1 <= prices[i] <= 10^5
 * 
 * 
 */

// @lc code=start
import java.util.Arrays;

class Solution {
    public long getDescentPeriods(int[] prices) {
        int n = prices.length;
        long[] dp = new long[n];
        Arrays.fill(dp, 1);

        for (int i = 1; i < n; i++) {
            if (prices[i] == prices[i - 1] - 1) {
                dp[i] = dp[i - 1] + 1;
            }
        }

        long result = 0;
        for (long count : dp) {
            result += count;
        }

        return result;
    }
}
// @lc code=end

