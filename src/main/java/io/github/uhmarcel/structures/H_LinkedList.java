package io.github.uhmarcel.structures;

import java.util.Iterator;
import io.github.uhmarcel.H_Deque;
import io.github.uhmarcel.H_Queue;
import io.github.uhmarcel.H_Stack;
import io.github.uhmarcel.H_List;

public class H_LinkedList<E> implements H_List<E>, H_Queue<E>, H_Stack<E>, H_Deque<E> {
    
    private ListNode head;
    private ListNode tail;
    private int size;

    public H_LinkedList() {
        this.head = new ListNode(null);
        this.tail = new ListNode(null);
        this.size = 0;
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }
    
    public boolean addFirst(E e) {
        head.insertAfter(e);
        return true;
    }
    
    public boolean addLast(E e) {
        tail.insertBefore(e);
        return true;
    }
    
    public E removeFirst() {
        if (size == 0) return null;
        return head.next.remove();
    }
    
    public E removeLast() {
        if (size == 0) return null;
        return tail.prev.remove();
    }
    
    public E getFirst() {
        if (size == 0) return null;
        return head.next.value;
    }
    
    public E getLast() {
        if (size == 0) return null;
        return tail.prev.value;
    }

    @Override
    public boolean add(E e) {
        return addLast(e);
    }
    
    @Override
    public boolean remove(Object o) {
        int i = indexOf(o);
        if (i == -1) return false;
        remove(i);
        return true;
    }
    
    @Override
    public void add(int i, E e) {
        if (i < 0 || i > size) {
            throw new IndexOutOfBoundsException();
        }

        if (i == size) {
            addLast(e);
        } else {
            getNode(i).insertBefore(e);
        }
    }

    @Override
    public E remove(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
        return getNode(i).remove();
    }
    
    @Override
    public E get(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
        return getNode(i).value;
    }

    @Override
    public E set(int i, E e) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
        return getNode(i).set(e);
    }
    
    @Override
    public void clear() {
        this.head = new ListNode(null);
        this.tail = new ListNode(null);
        this.size = 0;
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public int indexOf(Object o) {
        ListNode curr = head.next;
        for (int i = 0; i < size; i++) {
            if (curr.value.equals(o))
                return i;
            curr = curr.next;
        }
        return -1;
    }
    
    @Override
    public boolean offer(E elem) {
        return addLast(elem);
    }
    
    @Override
    public boolean offerFirst(E elem) {
        return addFirst(elem);
    }
    
    @Override
    public boolean offerLast(E elem) {
        return addLast(elem);
    }

    @Override
    public E poll() {
        return removeFirst();
    }
    
    @Override
    public E pollFirst() {
        return removeFirst();
    }
    
    @Override
    public E pollLast() {
        return removeLast();
    }

    @Override
    public E peek() {
        return getFirst();
    }
    
    @Override
    public E peekFirst() {
        return getFirst();
    }
    
    @Override
    public E peekLast() {
        return getLast();
    }
    
    @Override
    public boolean push(E elem) {
        return addFirst(elem);
    }

    @Override
    public E pop() {
        return removeFirst();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (E element : this) {
            sb.append(element.toString());
            sb.append(", ");
        }
        if (size != 0) {
            sb.setLength(sb.length() - 2);
        } 
        sb.append(']');
        return sb.toString();
    }
    
    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }
    
    public ListNode getNode(int i) {
        return (i > size / 2) ? getFromTail(i) : getFromHead(i); 
    }
    
    private ListNode getFromHead(int index) {
        ListNode curr = head.next;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr;
    }
    
    private ListNode getFromTail(int index) {
        ListNode curr = tail.prev;
        for (int i = index; i < size - 1; i++) {
            curr = curr.prev;
        }
        return curr;
    }

    private class LinkedListIterator implements Iterator<E> {
        private ListNode pointer;
        
        public LinkedListIterator() {
            this.pointer = head;
        }
        
        @Override
        public boolean hasNext() {
            return pointer.next != tail;
        }
        
        @Override
        public E next() {
            pointer = pointer.next;
            return pointer.value;
        }

        @Override
        public void remove() {
            if (pointer == head) return;
            pointer.remove();
        }
    }
    
    public class ListNode {
        private ListNode next;
        private ListNode prev;
        private E value;
                
        private ListNode(E val) {
            this.value = val;
        }
        
        public ListNode next() {
            return (next != tail) ? next : null;
        }
        
        public ListNode prev() {
            return (prev != head) ? prev : null;
        }
        
        public E set(E e) {
            E previous = value;
            this.value = e;
            return previous;
        }
        public E value() {
            return value;
        }
        
        public void insertBefore(E e) {
            ListNode node = new ListNode(e);
            link(prev, node);
            link(node, this);
            size++;
        }
        
        public void insertAfter(E e) {
            ListNode node = new ListNode(e);
            link(node, next);
            link(this, node);
            size++;
        }
        
        public E remove() {
            unlink(this);
            size--;
            return value;
        }
        
        private void link(ListNode v, ListNode u) {
            if (v != null) v.next = u;
            if (u != null) u.prev = v;
        }

        private void unlink(ListNode v) {
            if (v == null) return;
            if (v.prev != null) v.prev.next = v.next;
            if (v.next != null) v.next.prev = v.prev;
        }
    
        public String toString() {
            return (value != null) ? value.toString() : null;
        }
    }
    
    //////////////////////////////////////////////
    // --------------- TEST ------------------- //
    //////////////////////////////////////////////
    
    public static void main(String[] args) {
        H_LinkedList<Integer> list = new H_LinkedList<>();
        
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        
        System.out.println(list);
        
        list.remove(7);
        list.removeFirst();
        System.out.println(list);
        System.out.println(list.contains(7));
        
        H_Queue<Integer> queue = new H_LinkedList<>();
        for (int i = 10; i >= 0; i--) {
            queue.offer(i);
        }
        
        System.out.println(queue);
        System.out.println(queue.peek());
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
        
        for (int elem : list) 
            System.out.print(elem + " ");
    }
}
