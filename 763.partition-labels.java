/*
 * @lc app=leetcode id=763 lang=java
 *
 * [763] Partition Labels
 *
 * https://leetcode.com/problems/partition-labels/description/
 *
 * algorithms
 * Medium (81.66%)
 * Likes:    11089
 * Dislikes: 436
 * Total Accepted:    749.1K
 * Total Submissions: 917.4K
 * Testcase Example:  '"ababcbacadefegdehijhklij"'
 *
 * You are given a string s. We want to partition the string into as many parts
 * as possible so that each letter appears in at most one part. For example,
 * the string "ababcc" can be partitioned into ["abab", "cc"], but partitions
 * such as ["aba", "bcc"] or ["ab", "ab", "cc"] are invalid.
 * 
 * Note that the partition is done so that after concatenating all the parts in
 * order, the resultant string should be s.
 * 
 * Return a list of integers representing the size of these parts.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: s = "ababcbacadefegdehijhklij"
 * Output: [9,7,8]
 * Explanation:
 * The partition is "ababcbaca", "defegde", "hijhklij".
 * This is a partition so that each letter appears in at most one part.
 * A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it
 * splits s into less parts.
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: s = "eccbbbbdec"
 * Output: [10]
 * 
 * 
 * 
 * Constraints:
 * 
 * 
 * 1 <= s.length <= 500
 * s consists of lowercase English letters.
 * 
 * 
 */

// @lc code=start
import java.util.*;

class Solution {
    public List<Integer> partitionLabels(String s) {
        if (s.length() == 1) {
            return List.of(1);
        }

        int[] lastIndex = new int[26];

        // store the last index of each character
        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - 'a';
            lastIndex[idx] = i;
        }

        List<Integer> result = new ArrayList<>();

        int partitionStart = 0;
        int partitionEnd = 0;

        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - 'a';
            // extend the partition end to the last index of the current character
            partitionEnd = Math.max(partitionEnd, lastIndex[idx]);

            // if we have reached the end of the partition
            if (i == partitionEnd) {
                // add the size of the partition to the result
                result.add(partitionEnd - partitionStart + 1);
                
                // start a new partition
                partitionStart = partitionEnd + 1;
            }
        }

        return result;
    }

    public List<Integer> partitionLabels2(String s) {
        if (s.length() == 1) {
            return List.of(1);
        }

        int[] lastIndex = new int[26];
        
        int[] firstIndex = new int[26];
        Arrays.fill(firstIndex, -1);

        // store the last index of each character
        // and the first index of each character
        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - 'a';
            lastIndex[idx] = i;
            if (firstIndex[idx] == -1) {
                firstIndex[idx] = i;
            }
        }
        
        List<int[]> intervals = new ArrayList<>();

        for (char c : s.toCharArray()) {
            int idx = c - 'a';
            intervals.add(new int[] {firstIndex[idx], lastIndex[idx]});
        }

        int[][] mergedIntervals = mergeIntervals(intervals.toArray(int[][]::new));

        List<Integer> result = new ArrayList<>();
        for (int[] interval : mergedIntervals) {
            result.add(interval[1] - interval[0] + 1);
        }

        return result;
    }

    private int[][] mergeIntervals(int[][] intervals) {
        if (intervals.length <= 1) return intervals;

        // sort by ascending starting point
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        List<int[]> merged = new ArrayList<>();
        
        for (int[] currentInterval : intervals) {
            // if the list of merged intervals is empty or if the current
            // interval does not overlap with the previous, simply append it.
            if (merged.isEmpty() || merged.get(merged.size() - 1)[1] < currentInterval[0]) {
                merged.add(currentInterval);
            }
            // otherwise, there is overlap, so we merge the current and previous intervals
            else {
                merged.get(merged.size() - 1)[1] = Math.max(merged.get(merged.size() - 1)[1], currentInterval[1]);
            }
        }
        
        return merged.toArray(int[][]::new);
    }
}
// @lc code=end

