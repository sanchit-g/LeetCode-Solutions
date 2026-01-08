/*
 * @lc app=leetcode id=127 lang=java
 *
 * [127] Word Ladder
 *
 * https://leetcode.com/problems/word-ladder/description/
 *
 * algorithms
 * Hard (44.32%)
 * Likes:    13320
 * Dislikes: 1957
 * Total Accepted:    1.5M
 * Total Submissions: 3.4M
 * Testcase Example:  '"hit"\n"cog"\n["hot","dot","dog","lot","log","cog"]'
 *
 * A transformation sequence from word beginWord to word endWord using a
 * dictionary wordList is a sequence of words beginWord -> s1 -> s2 -> ... ->
 * sk such that:
 * 
 * 
 * Every adjacent pair of words differs by a single letter.
 * Every si for 1 <= i <= k is in wordList. Note that beginWord does not need
 * to be in wordList.
 * sk == endWord
 * 
 * 
 * Given two words, beginWord and endWord, and a dictionary wordList, return
 * the number of words in the shortest transformation sequence from beginWord
 * to endWord, or 0 if no such sequence exists.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: beginWord = "hit", endWord = "cog", wordList =
 * ["hot","dot","dog","lot","log","cog"]
 * Output: 5
 * Explanation: One shortest transformation sequence is "hit" -> "hot" -> "dot"
 * -> "dog" -> cog", which is 5 words long.
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: beginWord = "hit", endWord = "cog", wordList =
 * ["hot","dot","dog","lot","log"]
 * Output: 0
 * Explanation: The endWord "cog" is not in wordList, therefore there is no
 * valid transformation sequence.
 * 
 * 
 * 
 * Constraints:
 * 
 * 
 * 1 <= beginWord.length <= 10
 * endWord.length == beginWord.length
 * 1 <= wordList.length <= 5000
 * wordList[i].length == beginWord.length
 * beginWord, endWord, and wordList[i] consist of lowercase English
 * letters.
 * beginWord != endWord
 * All the words in wordList are unique.
 * 
 * 
 */

// @lc code=start
import java.util.*;

class Solution {
    private class Pair<U, V> {
        public U first;
        public V second;

        public Pair(U first, V second) {
            this.first = first;
            this.second = second;
        }
    }

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // Convert wordList to a set for O(1) lookups
        Set<String> wordSet = new HashSet<>(wordList);
        
        Deque<Pair<String, Integer>> queue = new ArrayDeque<>();
        queue.offerLast(new Pair<>(beginWord, 1));

        wordSet.remove(beginWord);      // Mark the beginWord as visited

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                Pair<String, Integer> current = queue.pollFirst();
                String currentWord = current.first;
                int currentSteps = current.second;

                if (currentWord.equals(endWord)) {
                    return currentSteps;
                }
                
                char[] chars = currentWord.toCharArray();

                for (int j = 0; j < chars.length; j++) {
                    char originalChar = chars[j];

                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == originalChar) continue;

                        chars[j] = c;
                        String newWord = new String(chars);

                        if (wordSet.contains(newWord)) {
                            queue.offerLast(new Pair<>(newWord, currentSteps + 1));
                            wordSet.remove(newWord);  // Mark as visited
                        }
                    }

                    chars[j] = originalChar;  // Restore the original character
                }
            }
        }

        return 0;
    }
}
// @lc code=end