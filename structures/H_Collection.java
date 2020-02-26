package structures;

public interface H_Collection<E> extends Iterable<E> {

    public boolean add(E e);
    public boolean remove(Object o);
    public boolean contains(Object o);
    public int size();
    public boolean isEmpty();
    public void clear();
    
}
