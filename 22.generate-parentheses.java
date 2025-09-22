/*
 * @lc app=leetcode id=22 lang=java
 *
 * [22] Generate Parentheses
 *
 * https://leetcode.com/problems/generate-parentheses/description/
 *
 * algorithms
 * Medium (77.64%)
 * Likes:    22649
 * Dislikes: 1058
 * Total Accepted:    2.6M
 * Total Submissions: 3.3M
 * Testcase Example:  '3'
 *
 * Given n pairs of parentheses, write a function to generate all combinations
 * of well-formed parentheses.
 * 
 * 
 * Example 1:
 * Input: n = 3
 * Output: ["((()))","(()())","(())()","()(())","()()()"]
 * Example 2:
 * Input: n = 1
 * Output: ["()"]
 * 
 * 
 * Constraints:
 * 
 * 
 * 1 <= n <= 8
 * 
 * 
 */

// @lc code=start
import java.util.*;

class Solution {
    private List<String> result = new ArrayList<>();

    public List<String> generateParenthesis(int n) {
        backtrack(new StringBuilder(), n, n);
        return result;
    }

    private void backtrack(StringBuilder currentString, int openBrackets, int closeBrackets) {
        if (openBrackets == 0 && closeBrackets == 0) {
            result.add(currentString.toString());
            return;
        }

        if (openBrackets > 0) {
            backtrack(new StringBuilder(currentString).append("("), openBrackets - 1, closeBrackets);
        }

        if (closeBrackets > openBrackets) {
            backtrack(new StringBuilder(currentString).append(")"), openBrackets, closeBrackets - 1);
        }
    }
}
// @lc code=end

