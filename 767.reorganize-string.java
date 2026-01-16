/*
 * @lc app=leetcode id=767 lang=java
 *
 * [767] Reorganize String
 *
 * https://leetcode.com/problems/reorganize-string/description/
 *
 * algorithms
 * Medium (56.64%)
 * Likes:    9143
 * Dislikes: 285
 * Total Accepted:    574K
 * Total Submissions: 1M
 * Testcase Example:  '"aab"'
 *
 * Given a string s, rearrange the characters of s so that any two adjacent
 * characters are not the same.
 * 
 * Return any possible rearrangement of s or return "" if not possible.
 * 
 * 
 * Example 1:
 * Input: s = "aab"
 * Output: "aba"
 * Example 2:
 * Input: s = "aaab"
 * Output: ""
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
    public String reorganizeString(String s) {
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        // max heap to determine the most frequent character
        // in O(1) time
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> count[b] - count[a]);
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                pq.offer(i);
            }
        }

        StringBuilder sb = new StringBuilder();
        int blocked = -1;       // stores the character that cannot be used in the next step

        while (!pq.isEmpty()) {
            int current = pq.poll();
            sb.append((char) (current + 'a'));
            count[current]--;

            // if the previous character is still available, add it back to the heap
            if (blocked != -1 && count[blocked] > 0) {
                pq.offer(blocked);
            }

            // block current character if it is still available
            if (count[current] > 0) {
                blocked = current;
            } else {
                blocked = -1;
            }
        }

        // if the length of the result is not equal to the length of the input string
        // it means that it is not possible to rearrange the string
        if (sb.length() != s.length()) {
            return "";
        }

        return sb.toString();
    }
}
// @lc code=end