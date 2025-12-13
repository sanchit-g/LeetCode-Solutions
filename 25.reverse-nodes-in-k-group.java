/*
 * @lc app=leetcode id=25 lang=java
 *
 * [25] Reverse Nodes in k-Group
 *
 * https://leetcode.com/problems/reverse-nodes-in-k-group/description/
 *
 * algorithms
 * Hard (64.68%)
 * Likes:    15303
 * Dislikes: 789
 * Total Accepted:    1.4M
 * Total Submissions: 2.2M
 * Testcase Example:  '[1,2,3,4,5]\n2'
 *
 * Given the head of a linked list, reverse the nodes of the list k at a time,
 * and return the modified list.
 * 
 * k is a positive integer and is less than or equal to the length of the
 * linked list. If the number of nodes is not a multiple of k then left-out
 * nodes, in the end, should remain as it is.
 * 
 * You may not alter the values in the list's nodes, only nodes themselves may
 * be changed.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: head = [1,2,3,4,5], k = 2
 * Output: [2,1,4,3,5]
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: head = [1,2,3,4,5], k = 3
 * Output: [3,2,1,4,5]
 * 
 * 
 * 
 * Constraints:
 * 
 * 
 * The number of nodes in the list is n.
 * 1 <= k <= n <= 5000
 * 0 <= Node.val <= 1000
 * 
 * 
 * 
 * Follow-up: Can you solve the problem in O(1) extra memory space?
 * 
 */

// @lc code=start
class Solution {
    private class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode temp = head;
        ListNode prevGroupLast = null;

        while (true) {
            ListNode kthNode = findKthNode(temp, k);

            if (kthNode == null) {
                // If there are fewer than k nodes left, leave them as is
                if (prevGroupLast != null) {
                    prevGroupLast.next = temp;
                }
                break;
            } 
            
            ListNode nextGroupFirst = kthNode.next;
            kthNode.next = null;
            reverseLinkedList(temp);
            
            if (temp == head) {
                // If it's the first group, update the head
                head = kthNode;
            } else {
                prevGroupLast.next = kthNode;
            }
            prevGroupLast = temp;
            temp = nextGroupFirst;
        }

        return head;
    }

    private void reverseLinkedList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
    }

    private ListNode findKthNode(ListNode head, int k) {
        ListNode curr = head;
        int count = 1;

        while (curr != null && count < k) {
            curr = curr.next;
            count++;
        }

        return curr;
    }
}
// @lc code=end

