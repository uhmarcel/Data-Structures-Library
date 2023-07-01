
package io.github.uhmarcel;


public interface H_Heap<E> extends H_PriorityQueue<E> {

    public ElementKey<E> insert(E element);
    public E deleteMin();
    public E findMin();
    public void merge(H_Heap<E> heap);
    public ElementKey<E> decreaseKey(ElementKey<E> key, E value);       
    
    public default boolean offer(E elem) {
        return insert(elem) != null;
    }
    
    public default E poll() {
        return deleteMin();
    }
    
    public default E peek() {
        return findMin();
    }
    
    public static interface ElementKey<E> {
    
        public E getValue();
    
    }
    
    
}
