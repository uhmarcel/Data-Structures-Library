
package interfaces;



public interface H_Heap<E> extends H_PriorityQueue<E> {
    
    public void decreaseKey(E key);
    public void merge(H_Heap<E> heap);
    public boolean offer(E elem);
    public E poll();
    public E peek();
    
}
