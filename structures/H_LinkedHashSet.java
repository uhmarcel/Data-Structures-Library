
package structures;

import java.util.Iterator;


public class H_LinkedHashSet<E> implements H_Set<E> {

    private H_Map<E, ListNode> map; 
    private ListNode head;
    private ListNode tail;
    
    public H_LinkedHashSet() {
        this.map = new H_HashMap<>();
        this.head = new ListNode(null);
        this.tail = new ListNode(null);
        link(head, tail);
    }
    
    public boolean add(E e) {
        if (map.containsKey(e)) return false;
        ListNode node = new ListNode(e);
        link(tail.prev, node);
        link(node, tail);
        map.put(e, node);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode node = map.remove(o);
        if (node == null) return false;
        unlink(node);
        return true;
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey((E) o);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.size() == 0;
    }

    @Override
    public void clear() {
        this.map = new H_HashMap<>();
        this.head = new ListNode(null);
        this.tail = this.head;
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedHashSetIterator();
    }
    
    public String toString() {
        H_List<E> list = new H_ArrayList<>();
        for (E element : this) {
            list.add(element);
        }
        return list.toString();
    }
    
    private void link(ListNode v, ListNode u) {
        v.next = u;
        u.prev = v;
    }
    
    private void unlink(ListNode v) {
        if (v.prev != null) v.prev.next = v.next;
        if (v.next != null) v.next.prev = v.prev;
    }
    
    private class ListNode {
        public E value;
        public ListNode prev;
        public ListNode next;
    
        public ListNode(E value) {
            this.value = value;
        }
        
        public int hashCode() {
            return value.hashCode();
        } 
        
        public boolean equals(Object o) {
            ListNode node = (ListNode) o;
            return value.equals(node.value);
        }
    }
    
    private class LinkedHashSetIterator implements Iterator<E> {
        private ListNode pointer;
        
        public LinkedHashSetIterator() {
            this.pointer = head;
        }
        public boolean hasNext() {
            return pointer.next != tail;
        }

        public E next() {
            pointer = pointer.next;
            return pointer.value;
        }
    }
            
    //////////////////////////////////////////////
    // --------------- TEST ------------------- //
    //////////////////////////////////////////////
    
    public static void main(String[] mains) {
        H_Set<Character> set = new H_LinkedHashSet<>();
        for (char c : "helloworld".toCharArray()) {
            set.add(c);
        }
        System.out.println(set);
        
        for (char elem : set) 
            System.out.print(elem + " ");
        
        System.out.println(set.contains('o'));
        System.out.println(set.contains('f'));
        
    }

}
