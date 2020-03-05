
package structures.interfaces;

import structures.interfaces.H_Collection;
import java.util.Iterator;


public interface H_Tree<E> extends H_Collection<E> {
    
    public boolean add(E element);
    public boolean remove(Object o);
    public boolean contains(Object o);
    public boolean isEmpty();
    public int size();
    public void clear();
    
    public Iterator<E> inorderIterator();
    public Iterator<E> preorderIterator();
    public Iterator<E> postorderIterator();
    public Iterator<E> descendingIterator();
    
}
