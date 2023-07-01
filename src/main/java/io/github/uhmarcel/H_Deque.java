package io.github.uhmarcel;

public interface H_Deque<E> {
    
    public boolean offerFirst(E elem);
    public boolean offerLast(E elem);
    public E pollFirst();
    public E pollLast();
    public E peekFirst();
    public E peekLast();
    
    public int size();
    public boolean isEmpty();
}
