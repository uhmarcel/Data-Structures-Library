
package interfaces;

import java.util.Comparator;


public interface H_SortedSet_TODO<E> extends H_Set<E> {
    public Comparator<? super E> comparator();
    public E first();
    public E last();
    public H_SortedSet_TODO<E> headSet(E targetElement);
    public H_SortedSet_TODO<E> tailSet(E targetElement);
}
