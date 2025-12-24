/*
 * @lc app=leetcode id=907 lang=java
 *
 * [907] Sum of Subarray Minimums
 *
 * https://leetcode.com/problems/sum-of-subarray-minimums/description/
 *
 * algorithms
 * Medium (38.08%)
 * Likes:    9030
 * Dislikes: 718
 * Total Accepted:    408.8K
 * Total Submissions: 1.1M
 * Testcase Example:  '[3,1,2,4]'
 *
 * Given an array of integers arr, find the sum of min(b), where b ranges over
 * every (contiguous) subarray of arr. Since the answer may be large, return
 * the answer modulo 10^9 + 7.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: arr = [3,1,2,4]
 * Output: 17
 * Explanation: 
 * Subarrays are [3], [1], [2], [4], [3,1], [1,2], [2,4], [3,1,2], [1,2,4],
 * [3,1,2,4]. 
 * Minimums are 3, 1, 2, 4, 1, 1, 2, 1, 1, 1.
 * Sum is 17.
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: arr = [11,81,94,43,3]
 * Output: 444
 * 
 * 
 * 
 * Constraints:
 * 
 * 
 * 1 <= arr.length <= 3 * 10^4
 * 1 <= arr[i] <= 3 * 10^4
 * 
 * 
 */

// @lc code=start
import java.util.Stack;

class Solution {
    private static final int MOD = 1_000_000_007;

    private int[] findPreviousSmallerOrEqualIndices(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        int[] result = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) stack.pop();
            result[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        return result;
    }

    private int[] findNextSmallerIndices(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        int[] result = new int[arr.length];

        for (int i = arr.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) stack.pop();
            result[i] = stack.isEmpty() ? arr.length : stack.peek();
            stack.push(i);
        }
        return result;
    }

    public int sumSubarrayMins(int[] arr) {
        int[] previousSmallerOrEqualIndices = findPreviousSmallerOrEqualIndices(arr);
        int[] nextSmallerIndices = findNextSmallerIndices(arr);

        long sum = 0;

        for (int i = 0; i < arr.length; i++) {
            int left = i - previousSmallerOrEqualIndices[i];
            int right = nextSmallerIndices[i] - i;
            
            sum += (long) arr[i] * left * right;
            sum %= MOD;
        }
        
        return (int) sum;
    }
}
// @lc code=end

