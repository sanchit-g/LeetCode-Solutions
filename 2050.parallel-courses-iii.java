/*
 * @lc app=leetcode id=2050 lang=java
 *
 * [2050] Parallel Courses III
 *
 * https://leetcode.com/problems/parallel-courses-iii/description/
 *
 * algorithms
 * Hard (66.77%)
 * Likes:    1708
 * Dislikes: 47
 * Total Accepted:    108K
 * Total Submissions: 161.7K
 * Testcase Example:  '3\n[[1,3],[2,3]]\n[3,2,5]'
 *
 * You are given an integer n, which indicates that there are n courses labeled
 * from 1 to n. You are also given a 2D integer array relations where
 * relations[j] = [prevCoursej, nextCoursej] denotes that course prevCoursej
 * has to be completed before course nextCoursej (prerequisite relationship).
 * Furthermore, you are given a 0-indexed integer array time where time[i]
 * denotes how many months it takes to complete the (i+1)^th course.
 * 
 * You must find the minimum number of months needed to complete all the
 * courses following these rules:
 * 
 * 
 * You may start taking a course at any time if the prerequisites are met.
 * Any number of courses can be taken at the same time.
 * 
 * 
 * Return the minimum number of months needed to complete all the courses.
 * 
 * Note: The test cases are generated such that it is possible to complete
 * every course (i.e., the graph is a directed acyclic graph).
 * 
 * 
 * Example 1:
 * 
 * 
 * 
 * Input: n = 3, relations = [[1,3],[2,3]], time = [3,2,5]
 * Output: 8
 * Explanation: The figure above represents the given graph and the time
 * required to complete each course. 
 * We start course 1 and course 2 simultaneously at month 0.
 * Course 1 takes 3 months and course 2 takes 2 months to complete
 * respectively.
 * Thus, the earliest time we can start course 3 is at month 3, and the total
 * time required is 3 + 5 = 8 months.
 * 
 * 
 * Example 2:
 * 
 * 
 * 
 * Input: n = 5, relations = [[1,5],[2,5],[3,5],[3,4],[4,5]], time =
 * [1,2,3,4,5]
 * Output: 12
 * Explanation: The figure above represents the given graph and the time
 * required to complete each course.
 * You can start courses 1, 2, and 3 at month 0.
 * You can complete them after 1, 2, and 3 months respectively.
 * Course 4 can be taken only after course 3 is completed, i.e., after 3
 * months. It is completed after 3 + 4 = 7 months.
 * Course 5 can be taken only after courses 1, 2, 3, and 4 have been completed,
 * i.e., after max(1,2,3,7) = 7 months.
 * Thus, the minimum time needed to complete all the courses is 7 + 5 = 12
 * months.
 * 
 * 
 * 
 * Constraints:
 * 
 * 
 * 1 <= n <= 5 * 10^4
 * 0 <= relations.length <= min(n * (n - 1) / 2, 5 * 10^4)
 * relations[j].length == 2
 * 1 <= prevCoursej, nextCoursej <= n
 * prevCoursej != nextCoursej
 * All the pairs [prevCoursej, nextCoursej] are unique.
 * time.length == n
 * 1 <= time[i] <= 10^4
 * The given graph is a directed acyclic graph.
 * 
 * 
 */

// @lc code=start
import java.util.*;

class Solution {
    public int minimumTime(int n, int[][] relations, int[] time) {
        // Build the adjacency list
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        // Initialize the in-degree array
        int[] inDegree = new int[n + 1];
        for (int[] relation : relations) {
            int prev = relation[0];
            int next = relation[1];
            graph.get(prev).add(next);
            inDegree[next]++;
        }
        
        // Initialize the completion time array
        int[] completionTime = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            // Minimum completion time for each course is the time required to complete it
            completionTime[i] = time[i - 1];
        }

        // Initialize the queue with courses that have no prerequisites
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0) {
                queue.offerLast(i);
            }
        }

        // Use the Kahn's algorithm
        while (!queue.isEmpty()) {
            int u = queue.pollFirst();

            for (int v : graph.get(u)) {
                // Update the completion time for the dependent course
                completionTime[v] = Math.max(completionTime[v], completionTime[u] + time[v - 1]);
                
                // Reduce the in-degree of the dependent course
                inDegree[v]--;
                if (inDegree[v] == 0) {
                    queue.offerLast(v);
                }
            }
        }

        // Find the maximum completion time
        int maxTime = 0;
        for (int i = 1; i <= n; i++) {
            maxTime = Math.max(maxTime, completionTime[i]);
        }

        return maxTime;
    }
}
// @lc code=end