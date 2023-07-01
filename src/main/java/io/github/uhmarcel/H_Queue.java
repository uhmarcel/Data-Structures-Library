package io.github.uhmarcel;


public interface H_Queue<E> {

    public boolean offer(E elem);
    public E poll();
    public E peek();
    
    public int size();
    
    public default boolean isEmpty() {
        return this.size() == 0;
    }
}
