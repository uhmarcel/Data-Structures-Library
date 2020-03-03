
package interfaces;

import java.util.Comparator;


public interface H_PriorityQueue<E> extends H_Queue<E> {
   
    public boolean offer(E elem);
    public E poll();
    public E peek();
    public int size();
    public boolean isEmpty();
    public Comparator<? super E> comparator();

}
