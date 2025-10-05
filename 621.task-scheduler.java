/*
 * @lc app=leetcode id=621 lang=java
 *
 * [621] Task Scheduler
 *
 * https://leetcode.com/problems/task-scheduler/description/
 *
 * algorithms
 * Medium (62.08%)
 * Likes:    11447
 * Dislikes: 2180
 * Total Accepted:    866.4K
 * Total Submissions: 1.4M
 * Testcase Example:  '["A","A","A","B","B","B"]\n2'
 *
 * You are given an array of CPU tasks, each labeled with a letter from A to Z,
 * and a number n. Each CPU interval can be idle or allow the completion of one
 * task. Tasks can be completed in any order, but there's a constraint: there
 * has to be a gap of at least n intervals between two tasks with the same
 * label.
 * 
 * Return the minimum number of CPU intervals required to complete all
 * tasks.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: tasks = ["A","A","A","B","B","B"], n = 2
 * 
 * Output: 8
 * 
 * Explanation: A possible sequence is: A -> B -> idle -> A -> B -> idle -> A
 * -> B.
 * 
 * After completing task A, you must wait two intervals before doing A again.
 * The same applies to task B. In the 3^rd interval, neither A nor B can be
 * done, so you idle. By the 4^th interval, you can do A again as 2 intervals
 * have passed.
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: tasks = ["A","C","A","B","D","B"], n = 1
 * 
 * Output: 6
 * 
 * Explanation: A possible sequence is: A -> B -> C -> D -> A -> B.
 * 
 * With a cooling interval of 1, you can repeat a task after just one other
 * task.
 * 
 * 
 * Example 3:
 * 
 * 
 * Input: tasks = ["A","A","A", "B","B","B"], n = 3
 * 
 * Output: 10
 * 
 * Explanation: A possible sequence is: A -> B -> idle -> idle -> A -> B ->
 * idle -> idle -> A -> B.
 * 
 * There are only two types of tasks, A and B, which need to be separated by 3
 * intervals. This leads to idling twice between repetitions of these
 * tasks.
 * 
 * 
 * 
 * Constraints:
 * 
 * 
 * 1 <= tasks.length <= 10^4
 * tasks[i] is an uppercase English letter.
 * 0 <= n <= 100
 * 
 * 
 */

// @lc code=start
import java.util.*;

class Solution {
    public int leastInterval(char[] tasks, int n) {
        if (n == 0) return tasks.length;
        
        // Max heap to store the frequencies of tasks
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        
        int[] freq = new int[26];
        for (char task : tasks) {
            freq[task - 'A']++;
        }

        for (int f : freq) {
            if (f > 0) {
                maxHeap.offer(f);
            }
        }

        Deque<int[]> waitQueue = new ArrayDeque<>();
        int time = 0;

        while (!maxHeap.isEmpty() || !waitQueue.isEmpty()) {
            time++;

            if (!maxHeap.isEmpty()) {
                int currentFreq = maxHeap.poll() - 1; // Execute the task
                if (currentFreq > 0) {
                    waitQueue.offerLast(new int[]{currentFreq, time + n}); // Add to wait queue if it still has remaining count
                }
            }

            if (!waitQueue.isEmpty() && waitQueue.peekFirst()[1] == time) {
                maxHeap.offer(waitQueue.pollFirst()[0]); // Move task back to maxHeap after cooling period
            }
        }

        return time;
    }
}
// @lc code=end

