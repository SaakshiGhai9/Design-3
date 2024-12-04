// Time complexity: O(1)
// space Complexity: O(1)

import java.util.HashMap;
import java.util.Map;


public class LRUCache {
    private class Node{
        int key, value;
        Node prev, next;

        Node (int key, int value){
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> map;
    private final Node head, tail;

    public LRUCache(int capacity){
        this.capacity = capacity;
        this.map = new HashMap<>();

        // dummy head and tail nodes for Doubly linked list

        head = new Node(0,0);
        tail = new Node(0,0);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }

        // move the accessed node to the head
        Node node = map.get(key);
        remove(node);
        addToHead(node);

        return node.value;
    }

    public void put (int key, int value) {
        // update teh value and move node to the head
        if(map.containsKey(key)){
        Node node = map.get(key);
        node.value = value;
        remove(node);
        addToHead(node);
    } else{
        // add a new node

        Node node = new Node(key, value);
        map.put(key, node);
        addToHead(node);

        // if capacity is exceeded remove the least recently used node
            if(map.size() > capacity ){
                Node lru = tail.prev;
                remove(lru);
                map.remove(lru.key);
            }
        }
    }

    private void addToHead(Node node){
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void remove(Node node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public static void main(String [] args ){
        LRUCache lruCache = new LRUCache(2);

        lruCache.put(1, 1); // Cache is {1=1}
        lruCache.put(2, 2); // Cache is {1=1, 2=2}
        System.out.println(lruCache.get(1)); // Output: 1 (Cache is {2=2, 1=1})

        lruCache.put(3, 3); // Evicts key 2, Cache is {1=1, 3=3}
        System.out.println(lruCache.get(2)); // Output: -1 (not found)

        lruCache.put(4, 4); // Evicts key 1, Cache is {4=4, 3=3}
        System.out.println(lruCache.get(1)); // Output: -1 (not found)
        System.out.println(lruCache.get(3)); // Output: 3 (Cache is {4=4, 3=3})
        System.out.println(lruCache.get(4)); // Output: 4 (Cache is {3=3, 4=4})
    }

}
