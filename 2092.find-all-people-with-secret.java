/*
 * @lc app=leetcode id=2092 lang=java
 *
 * [2092] Find All People With Secret
 *
 * https://leetcode.com/problems/find-all-people-with-secret/description/
 *
 * algorithms
 * Hard (45.32%)
 * Likes:    1902
 * Dislikes: 88
 * Total Accepted:    160.3K
 * Total Submissions: 330.5K
 * Testcase Example:  '6\n[[1,2,5],[2,3,8],[1,5,10]]\n1'
 *
 * You are given an integer n indicating there are n people numbered from 0 to
 * n - 1. You are also given a 0-indexed 2D integer array meetings where
 * meetings[i] = [xi, yi, timei] indicates that person xi and person yi have a
 * meeting at timei. A person may attend multiple meetings at the same time.
 * Finally, you are given an integer firstPerson.
 * 
 * Person 0 has a secret and initially shares the secret with a person
 * firstPerson at time 0. This secret is then shared every time a meeting takes
 * place with a person that has the secret. More formally, for every meeting,
 * if a person xi has the secret at timei, then they will share the secret with
 * person yi, and vice versa.
 * 
 * The secrets are shared instantaneously. That is, a person may receive the
 * secret and share it with people in other meetings within the same time
 * frame.
 * 
 * Return a list of all the people that have the secret after all the meetings
 * have taken place. You may return the answer in any order.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: n = 6, meetings = [[1,2,5],[2,3,8],[1,5,10]], firstPerson = 1
 * Output: [0,1,2,3,5]
 * Explanation:
 * At time 0, person 0 shares the secret with person 1.
 * At time 5, person 1 shares the secret with person 2.
 * At time 8, person 2 shares the secret with person 3.
 * At time 10, person 1 shares the secret with person 5.​​​​
 * Thus, people 0, 1, 2, 3, and 5 know the secret after all the meetings.
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: n = 4, meetings = [[3,1,3],[1,2,2],[0,3,3]], firstPerson = 3
 * Output: [0,1,3]
 * Explanation:
 * At time 0, person 0 shares the secret with person 3.
 * At time 2, neither person 1 nor person 2 know the secret.
 * At time 3, person 3 shares the secret with person 0 and person 1.
 * Thus, people 0, 1, and 3 know the secret after all the meetings.
 * 
 * 
 * Example 3:
 * 
 * 
 * Input: n = 5, meetings = [[3,4,2],[1,2,1],[2,3,1]], firstPerson = 1
 * Output: [0,1,2,3,4]
 * Explanation:
 * At time 0, person 0 shares the secret with person 1.
 * At time 1, person 1 shares the secret with person 2, and person 2 shares the
 * secret with person 3.
 * Note that person 2 can share the secret at the same time as receiving it.
 * At time 2, person 3 shares the secret with person 4.
 * Thus, people 0, 1, 2, 3, and 4 know the secret after all the meetings.
 * 
 * 
 * 
 * Constraints:
 * 
 * 
 * 2 <= n <= 10^5
 * 1 <= meetings.length <= 10^5
 * meetings[i].length == 3
 * 0 <= xi, yi <= n - 1
 * xi != yi
 * 1 <= timei <= 10^5
 * 1 <= firstPerson <= n - 1
 * 
 * 
 */

// @lc code=start
import java.util.*;

class Solution {
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        // Group the meetings by time
        Map<Integer, List<int[]>> timeToMeetingsMap = new TreeMap<>();
        for (int[] meeting : meetings) {
            int x = meeting[0];
            int y = meeting[1];
            int time = meeting[2];
            timeToMeetingsMap.computeIfAbsent(time, k -> new ArrayList<>()).add(new int[]{x, y});
        }

        // Array to track who knows the secret
        boolean[] knowsSecret = new boolean[n];
        knowsSecret[0] = true;
        knowsSecret[firstPerson] = true;

        for (int time : timeToMeetingsMap.keySet()) {
            List<int[]> meetingsAtTime = timeToMeetingsMap.get(time);

            // Create adjacency list for the current time
            Map<Integer, List<Integer>> graph = new HashMap<>();
            for (int[] meeting : meetingsAtTime) {
                int x = meeting[0];
                int y = meeting[1];
                graph.computeIfAbsent(x, k -> new ArrayList<>()).add(y);
                graph.computeIfAbsent(y, k -> new ArrayList<>()).add(x);
            }

            Queue<Integer> queue = new LinkedList<>();

            // Add all people who know the secret at this time to the queue
            Set<Integer> addedToQueue = new HashSet<>();
            for (int[] meeting : meetingsAtTime) {
                int x = meeting[0];
                int y = meeting[1];

                if (knowsSecret[x] && addedToQueue.add(x)) {
                    queue.add(x);
                }
                if (knowsSecret[y] && addedToQueue.add(y)) {
                    queue.add(y);
                }
            }

            // Perform BFS
            while (!queue.isEmpty()) {
                int person = queue.poll();
                for (int neighbor : graph.getOrDefault(person, new ArrayList<>())) {
                    if (!knowsSecret[neighbor]) {
                        knowsSecret[neighbor] = true;
                        queue.add(neighbor);
                    }
                }
            }
        }

        // Collect the result
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (knowsSecret[i]) {
                result.add(i);
            }
        }

        return result;
    }
}
// @lc code=end

