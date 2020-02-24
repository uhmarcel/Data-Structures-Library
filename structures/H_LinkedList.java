
package structures;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class H_LinkedList<E> implements H_List<E>, H_Queue<E>, H_Stack<E> {
    
    private ListNode<E> head;
    private ListNode<E> tail;
    private int size;

    public H_LinkedList() {
        this.head = new ListNode<>();
        this.tail = new ListNode<>();
        this.size = 0;
        linkNodes(head, tail);
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }
    
    public boolean addFirst(E e) {
        insertAfter(head, e);
        size++;
        return true;
    }
    
    public boolean addLast(E e) {
        insertBefore(tail, e);
        size++;
        return true;
    }

    public boolean add(E e) {
        return this.addLast(e);
    }
    
    public boolean removeFirst() {
        if (size == 0) return false;
        head = head.next;
        head.prev = null;
        size--;
        return true;
    }
    
    public boolean removeLast() {
        if (size == 0) return false;
        tail = tail.prev;
        tail.next = null;
        size--;
        return true;
    }
    
    public void clear() {
        this.head = new ListNode<>();
        this.tail = new ListNode<>();
        this.size = 0;
        linkNodes(head, tail);
    }

    public E get(int i) {
        return getNode(i).value;
    }

    public E set(int i, E e) {
        ListNode<E> node = getNode(i);
        E oldVal = node.value;
        node.value = e;
        return oldVal;
    }

    public void add(int i, E e) {
        ListNode curr = getNode(i).prev;
        insertAfter(curr, e);
        size++;
    }

    public E remove(int i) {
        ListNode<E> curr = getNode(i).prev;
        E removed = curr.next.value;
        linkNodes(curr, curr.next.next);
        size--;
        return removed;
    }
    
    public boolean remove(Object o) {
        int i = indexOf(o);
        if (i == -1) return false;
        remove(i);
        return true;
    }

    public int indexOf(Object o) {
        ListNode<E> curr = head.next;
        for (int i = 0; i < size; i++) {
            if (curr.value.equals(o))
                return i;
            curr = curr.next;
        }
        return -1;
    }
    
    public boolean offer(E elem) {
        return add(elem);
    }

    public E poll() {
        E elem = get(0);
        removeFirst();
        return elem;
    }

    public E peek() {
        return get(0);
    }
    
    public boolean push(E elem) {
        return addFirst(elem);
    }

    public E pop() {
        return poll();
    }

    public Object[] toArray() {
        Object[] clone = new Object[size];
        ListNode<E> curr = head.next;
        for (int i = 0; i < size; i++) {
            clone[i] = curr.value;
            curr = curr.next;
        }
        return clone;
    }

    public <T> T[] toArray(T[] ts) {
        ListNode<E> curr = head.next;
        for (int i = 0; i < size; i++) {
            ts[i] = (T) curr.value;
            curr = curr.next;
        }
        return ts;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListNode<E> curr = head.next;
        sb.append('[');
        for (int i = 0; i < size; i++) {
            sb.append(curr.value);
            sb.append(", ");
            curr = curr.next;
        }
        if (size != 0) {
            sb.setLength(sb.length() - 2);
        } 
        sb.append(']');
        return sb.toString();
    }
    
    private void linkNodes(ListNode<E> v, ListNode<E> u) {
        v.next = u;
        u.prev = v;
    }
    
    private void insertAfter(ListNode<E> node, E elem) {
        if (node == null) return;
        ListNode<E> curr = new ListNode(elem);
        ListNode<E> next = node.next;
        linkNodes(node, curr);
        linkNodes(curr, next);        
    }
    
    private void insertBefore(ListNode<E> node, E elem) {
        if (node == null) return;
        ListNode<E> curr = new ListNode(elem);
        ListNode<E> prev = node.prev;
        linkNodes(prev, curr);
        linkNodes(curr, node);
    }
    
    private ListNode<E> getNode(int i) {
        return (i > size / 2) ? getFromTail(i) : getFromHead(i); 
    }
    
    private ListNode<E> getFromHead(int index) {
        ListNode<E> curr = head.next;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr;
    }
    
    private ListNode<E> getFromTail(int index) {
        ListNode<E> curr = tail.prev;
        for (int i = index; i < size - 1; i++) {
            curr = curr.prev;
        }
        return curr;
    }

    class ListNode<E> {
        public ListNode<E> next;
        public ListNode<E> prev;
        public E value;
        
        public ListNode() {
            this(null);
        }
        
        public ListNode(E val) {
            this.value = val;
            this.next = null;
            this.prev = null;
        }
    }
    
    //////////////////////////////////////////////
    // --------------- TEST ------------------- //
    //////////////////////////////////////////////
    
    public static void main(String[] args) {
        H_List<Integer> list = new H_LinkedList<>();
        
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        
        list.remove(7);
        System.out.println(list);
        System.out.println(list.contains(7));
        
        H_Queue<Integer> queue = new H_LinkedList<>();
        for (int i = 10; i >= 0; i--) {
            queue.offer(i);
        }
        System.out.println(queue.peek());
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
        
    }
}
