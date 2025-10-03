/*
 * @lc app=leetcode id=56 lang=java
 *
 * [56] Merge Intervals
 *
 * https://leetcode.com/problems/merge-intervals/description/
 *
 * algorithms
 * Medium (50.08%)
 * Likes:    23928
 * Dislikes: 877
 * Total Accepted:    3.5M
 * Total Submissions: 7M
 * Testcase Example:  '[[1,3],[2,6],[8,10],[15,18]]'
 *
 * Given an array of intervals where intervals[i] = [starti, endi], merge all
 * overlapping intervals, and return an array of the non-overlapping intervals
 * that cover all the intervals in the input.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
 * Output: [[1,6],[8,10],[15,18]]
 * Explanation: Since intervals [1,3] and [2,6] overlap, merge them into
 * [1,6].
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: intervals = [[1,4],[4,5]]
 * Output: [[1,5]]
 * Explanation: Intervals [1,4] and [4,5] are considered overlapping.
 * 
 * 
 * Example 3:
 * 
 * 
 * Input: intervals = [[4,7],[1,4]]
 * Output: [[1,7]]
 * Explanation: Intervals [1,4] and [4,7] are considered overlapping.
 * 
 * 
 * 
 * Constraints:
 * 
 * 
 * 1 <= intervals.length <= 10^4
 * intervals[i].length == 2
 * 0 <= starti <= endi <= 10^4
 * 
 * 
 */

// @lc code=start
import java.util.*;

class Solution {
    public int[][] merge(int[][] intervals) {

        if (intervals.length <= 1) return intervals;

        // sort by ascending starting point
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        List<int[]> merged = new ArrayList<>();
        
        for (int[] currentInterval : intervals) {
            // if the list of merged intervals is empty or if the current
            // interval does not overlap with the previous, simply append it.
            if (merged.isEmpty() || merged.get(merged.size() - 1)[1] < currentInterval[0]) {
                merged.add(currentInterval);
            } else {
                // otherwise, there is overlap, so we merge the current and previous intervals
                merged.get(merged.size() - 1)[1] = Math.max(merged.get(merged.size() - 1)[1], currentInterval[1]);
            }
        }
        return merged.toArray(int[][]::new);
    }
}
// @lc code=end

