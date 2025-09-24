/*
 * @lc app=leetcode id=49 lang=java
 *
 * [49] Group Anagrams
 *
 * https://leetcode.com/problems/group-anagrams/description/
 *
 * algorithms
 * Medium (71.44%)
 * Likes:    21142
 * Dislikes: 723
 * Total Accepted:    4.1M
 * Total Submissions: 5.8M
 * Testcase Example:  '["eat","tea","tan","ate","nat","bat"]'
 *
 * Given an array of strings strs, group the anagrams together. You can return
 * the answer in any order.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: strs = ["eat","tea","tan","ate","nat","bat"]
 * 
 * Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
 * 
 * Explanation:
 * 
 * 
 * There is no string in strs that can be rearranged to form "bat".
 * The strings "nat" and "tan" are anagrams as they can be rearranged to form
 * each other.
 * The strings "ate", "eat", and "tea" are anagrams as they can be rearranged
 * to form each other.
 * 
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: strs = [""]
 * 
 * Output: [[""]]
 * 
 * 
 * Example 3:
 * 
 * 
 * Input: strs = ["a"]
 * 
 * Output: [["a"]]
 * 
 * 
 * 
 * Constraints:
 * 
 * 
 * 1 <= strs.length <= 10^4
 * 0 <= strs[i].length <= 100
 * strs[i] consists of lowercase English letters.
 * 
 * 
 */

// @lc code=start
import java.util.*;

class Solution {
    // Original solution using sorting - O(n * k log k)
    public List<List<String>> groupAnagramsOriginal(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            char[] charArray = str.toCharArray();
            Arrays.sort(charArray);
            
            String sortedStr = new String(charArray);

            map.putIfAbsent(sortedStr, new ArrayList<>());
            map.get(sortedStr).add(str);
        }
        
        return new ArrayList<>(map.values());
    }
    
    // Optimized solution using character frequency counting - O(n * k)
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        
        for (String str : strs) {
            // Create frequency signature using character counting
            String key = getFrequencyKey(str);
            
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(str);
        }
        
        return new ArrayList<>(map.values());
    }
    
    private String getFrequencyKey(String str) {
        // Count frequency of each character (a-z)
        int[] count = new int[26];
        
        for (char c : str.toCharArray()) {
            count[c - 'a']++;
        }
        
        // Create unique key from frequency counts
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                sb.append((char)('a' + i)).append(count[i]);
            }
        }
        
        return sb.toString();
    }
}
// @lc code=end

