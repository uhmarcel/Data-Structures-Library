package interfaces;


public interface H_Queue<E> {

    public boolean offer(E elem);
    public E poll();
    public E peek();
    
    public int size();
    public boolean isEmpty();
    
}
