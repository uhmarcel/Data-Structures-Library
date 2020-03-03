
package interfaces;

import java.util.Comparator;


public interface H_SortedSet<E> extends H_Set<E> {
    public Comparator<? super E> comparator();
    public E first();
    public E last();
    public H_SortedSet<E> headSet(E targetElement);
    public H_SortedSet<E> tailSet(E targetElement);
}
