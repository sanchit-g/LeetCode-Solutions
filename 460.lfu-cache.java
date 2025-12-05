/*
 * @lc app=leetcode id=460 lang=java
 *
 * [460] LFU Cache
 *
 * https://leetcode.com/problems/lfu-cache/description/
 *
 * algorithms
 * Hard (47.53%)
 * Likes:    6218
 * Dislikes: 349
 * Total Accepted:    346.9K
 * Total Submissions: 723.5K
 * Testcase Example:  '["LFUCache","put","put","get","put","get","get","put","get","get","get"]\n' +
  '[[2],[1,1],[2,2],[1],[3,3],[2],[3],[4,4],[1],[3],[4]]'
 *
 * Design and implement a data structure for a Least Frequently Used (LFU)
 * cache.
 * 
 * Implement the LFUCache class:
 * 
 * 
 * LFUCache(int capacity) Initializes the object with the capacity of the data
 * structure.
 * int get(int key) Gets the value of the key if the key exists in the cache.
 * Otherwise, returns -1.
 * void put(int key, int value) Update the value of the key if present, or
 * inserts the key if not already present. When the cache reaches its capacity,
 * it should invalidate and remove the least frequently used key before
 * inserting a new item. For this problem, when there is a tie (i.e., two or
 * more keys with the same frequency), the least recently used key would be
 * invalidated.
 * 
 * 
 * To determine the least frequently used key, a use counter is maintained for
 * each key in the cache. The key with the smallest use counter is the least
 * frequently used key.
 * 
 * When a key is first inserted into the cache, its use counter is set to 1
 * (due to the put operation). The use counter for a key in the cache is
 * incremented either a get or put operation is called on it.
 * 
 * The functions get and put must each run in O(1) average time complexity.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input
 * ["LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get",
 * "get"]
 * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [3], [4, 4], [1], [3], [4]]
 * Output
 * [null, null, null, 1, null, -1, 3, null, -1, 3, 4]
 * 
 * Explanation
 * // cnt(x) = the use counter for key x
 * // cache=[] will show the last used order for tiebreakers (leftmost element
 * is  most recent)
 * LFUCache lfu = new LFUCache(2);
 * lfu.put(1, 1);   // cache=[1,_], cnt(1)=1
 * lfu.put(2, 2);   // cache=[2,1], cnt(2)=1, cnt(1)=1
 * lfu.get(1);      // return 1
 * ⁠                // cache=[1,2], cnt(2)=1, cnt(1)=2
 * lfu.put(3, 3);   // 2 is the LFU key because cnt(2)=1 is the smallest,
 * invalidate 2.
 * // cache=[3,1], cnt(3)=1, cnt(1)=2
 * lfu.get(2);      // return -1 (not found)
 * lfu.get(3);      // return 3
 * ⁠                // cache=[3,1], cnt(3)=2, cnt(1)=2
 * lfu.put(4, 4);   // Both 1 and 3 have the same cnt, but 1 is LRU, invalidate
 * 1.
 * ⁠                // cache=[4,3], cnt(4)=1, cnt(3)=2
 * lfu.get(1);      // return -1 (not found)
 * lfu.get(3);      // return 3
 * ⁠                // cache=[3,4], cnt(4)=1, cnt(3)=3
 * lfu.get(4);      // return 4
 * ⁠                // cache=[4,3], cnt(4)=2, cnt(3)=3
 * 
 * 
 * 
 * Constraints:
 * 
 * 
 * 1 <= capacity <= 10^4
 * 0 <= key <= 10^5
 * 0 <= value <= 10^9
 * At most 2 * 10^5 calls will be made to get and put.
 * 
 * 
 * 
 * 
 */

// @lc code=start
import java.util.Map;
import java.util.HashMap;

class LFUCache {
    
    private class Node {
        int key;
        int value;
        Node prev;
        Node next;
        int frequency;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.frequency = 1;
        }
    }

    private class DoublyLinkedList {
        private final Node head;
        private final Node tail;
        private int size;

        public DoublyLinkedList() {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
            this.size = 0;
        }

        public void addToFront(Node node) {
            head.next.prev = node;
            node.next = head.next;
            head.next = node;
            node.prev = head;
            size++;
        }

        public void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> cache;
    private final Map<Integer, DoublyLinkedList> frequencyMap;
    private int minFrequency;

    public LFUCache(int capacity_) {
        this.capacity = capacity_;
        this.cache = new HashMap<>();
        this.frequencyMap = new HashMap<>();
        this.minFrequency = 0;
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }

        Node node = cache.get(key);
        int value = node.value;

        updateFrequency(node);

        return value;
    }

    public void put(int key, int value) {
        if (capacity <= 0) {
            return;
        }

        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.value = value;
            updateFrequency(node);
        } else {
            if (cache.size() >= capacity) {
                DoublyLinkedList minFrequencyList = frequencyMap.get(minFrequency);

                Node lruNode = minFrequencyList.tail.prev;
                minFrequencyList.removeNode(lruNode);
                cache.remove(lruNode.key);
            }

            minFrequency = 1;
            Node node = new Node(key, value);
            cache.put(key, node);

            DoublyLinkedList newList = frequencyMap.getOrDefault(minFrequency, new DoublyLinkedList());
            newList.addToFront(node);
            frequencyMap.put(minFrequency, newList);
        }
    }

    private void updateFrequency(Node node) {
        int oldFrequency = node.frequency;
        DoublyLinkedList oldList = frequencyMap.get(oldFrequency);
        oldList.removeNode(node);

        if (oldFrequency == minFrequency && oldList.size == 0) {
            minFrequency++;
        }

        node.frequency++;
        DoublyLinkedList newList = frequencyMap.getOrDefault(node.frequency, new DoublyLinkedList());
        newList.addToFront(node);
        frequencyMap.put(node.frequency, newList);
    }
}
// @lc code=end

